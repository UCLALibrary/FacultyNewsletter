package newsletter;

public class VgerBean
{
  private String uid;
  private String lastName;
  private String firstName;
  private String email;
  
  public VgerBean()
  {
  }

  public void setEmail( String email )
  {
    this.email = email;
  }

  public String getEmail()
  {
    return email;
  }

  public void setUid( String uid )
  {
    this.uid = uid;
  }

  public String getUid()
  {
    return uid;
  }

  public void setLastName( String lastName )
  {
    this.lastName = lastName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setFirstName( String firstName )
  {
    this.firstName = firstName;
  }

  public String getFirstName()
  {
    return firstName;
  }
}
