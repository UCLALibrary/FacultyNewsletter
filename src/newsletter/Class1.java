package newsletter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Class1
{
  public Class1()
  {
  }

  public static void main( String[] args )
  {
    DriverManagerDataSource vgerDS;
    String result;

    vgerDS = new DriverManagerDataSource();
    vgerDS.setDriverClassName( "oracle.jdbc.OracleDriver" );
    vgerDS.setUrl( "url" );
    vgerDS.setUsername( "user" );
    vgerDS.setPassword( "PWD" );

    result = 
        new JdbcTemplate( vgerDS ).queryForObject( "select vger_support.test_590( 1855202 ) from dual", 
                                                   String.class ).toString();
    System.out.println(result);
  }
}
