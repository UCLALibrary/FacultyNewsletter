package newsletter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class NewsletterGenerator
{
  private static DriverManagerDataSource vgerDS;
  private static DriverManagerDataSource qdbDS;
  private static List<VgerBean> vgerItems;
  private static final String VGER_QUERY =
    "SELECT p.institution_id, INITCAP(p.last_name) AS last_name, INITCAP("
    + "p.first_name) AS first_name, pa.address_line1 AS email FROM "
    + "ucladb.patron p inner join ucladb.patron_address pa ON p.patron_id = "
    + "pa.patron_id inner join ucladb.patron_barcode pb ON p.patron_id = "
    + "pb.patron_id inner join vger_report.cmp_qdb qdb ON p.institution_id = "
    + "qdb.employee_id WHERE p.expire_date >= SYSDATE AND (pa.address_type = 3 "
    + "AND lower(pa.address_line1) NOT LIKE '%merced%') AND (pb.barcode_status"
    + " = 1 AND pb.patron_group_id IN (1,13,38,39)) ORDER BY INITCAP("
    + "p.last_name), INITCAP(p.first_name)";
  private static final String QDB_QUERY =
    "SELECT top 1 d.dept_title, d.org_title FROM qdb.dbo.tbl_emp e inner join "
    + "qdb.dbo.department d ON e.home_dept_code = d.dept_code WHERE e.employee_id = ?";

  public NewsletterGenerator()
  {
  }

  public static void main( String[] args )
    throws IOException
  {
    openConnections();
    getVger();
    populateNewsletter();
  }

  private static void openConnections()
  {
    vgerDS = new DriverManagerDataSource();
    vgerDS.setDriverClassName( "oracle.jdbc.OracleDriver" );
    //vgerDS.setUrl( "jdbc:oracle:thin:@//ils-db-prod.library.ucla.edu:1521/VGER.VGER" );
    vgerDS.setUrl( "jdbc:oracle:thin:@ils-db-prod.library.ucla.edu:1521:VGER" );
    vgerDS.setUsername( "VGER_SUPPORT" );
    vgerDS.setPassword( "VGER_SUPPORT_PWD" );

    qdbDS = new DriverManagerDataSource();
    qdbDS.setDriverClassName( "com.microsoft.sqlserver.jdbc.SQLServerDriver" );
    qdbDS.setUrl( "jdbc:sqlserver://obiwan.qdb.ucla.edu:1433" );
    qdbDS.setUsername( "mgrlib" );
    qdbDS.setPassword( "srKard21" );
  }

  private static void getVger()
  {
    JdbcTemplate sql;

    sql = new JdbcTemplate( vgerDS );
    vgerItems = sql.query( VGER_QUERY, new VgerMapper() );
  }

  private static void populateNewsletter()
    throws IOException
  {
    BufferedWriter writer;

    writer = new BufferedWriter(
      new FileWriter( "C:\\Temp\\newsletter\\faculty.csv" ) );

    writer.write(
      "\"last_name\",\"first_name\",\"email\",\"dept title\",\"org title\"");
    writer.newLine();

    for ( VgerBean bean : vgerItems )
    {
      NewsletterBean entry;

      entry = new NewsletterBean();
      entry.setVger(bean);
      entry.setQdb( getQDB( entry.getVger().getUid() ) );

      writer.write("\"" + entry.getVger().getLastName() + "\",");
      writer.write("\"" + entry.getVger().getFirstName() + "\",");
      writer.write("\"" + entry.getVger().getEmail() + "\",");
      writer.write("\"" + entry.getQdb().getDeptTitle() + "\",");
      writer.write("\"" + entry.getQdb().getOrgTitle() + "\"");
      writer.newLine();
    }

    writer.flush();
    writer.close();
  }

  private static QdbBean getQDB(String uid)
  {
    QdbBean bean;
    JdbcTemplate sql;

    sql = new JdbcTemplate( qdbDS );
    //System.out.println(QDB_QUERY.replaceAll("\\?", uid));
    bean = ( QdbBean ) sql.queryForObject( QDB_QUERY, new Object[] {uid}, new QdbMapper() );

    return bean;
  }
}
