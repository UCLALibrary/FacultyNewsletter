package newsletter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VgerMapper
  implements RowMapper
{
  public VgerMapper()
  {
  }

  public Object mapRow( ResultSet rs, int i )
    throws SQLException
  {
    VgerBean bean;
    
    bean = new VgerBean();
    bean.setEmail(rs.getString("email"));
    bean.setLastName(rs.getString("last_name"));
    bean.setFirstName(rs.getString("first_name"));
    bean.setUid(rs.getString("institution_id"));

    return bean;
  }
}
