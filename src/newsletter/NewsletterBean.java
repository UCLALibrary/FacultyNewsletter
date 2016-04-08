package newsletter;

public class NewsletterBean
{
  private VgerBean vger;
  private QdbBean qdb;
  
  public NewsletterBean()
  {
  }

  public void setVger( VgerBean vger )
  {
    this.vger = vger;
  }

  public VgerBean getVger()
  {
    return vger;
  }

  public void setQdb( QdbBean qdb )
  {
    this.qdb = qdb;
  }

  public QdbBean getQdb()
  {
    return qdb;
  }
}
