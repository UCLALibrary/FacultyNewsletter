package newsletter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class QdbMapper
  implements RowMapper
{
  public QdbMapper()
  {
  }

  public Object mapRow( ResultSet rs, int i )
    throws SQLException
  {
    QdbBean bean;
    
    bean = new QdbBean();
    bean.setDeptTitle(rs.getString("dept_title"));
    bean.setOrgTitle(rs.getString("org_title"));

    return bean;
  }
}
