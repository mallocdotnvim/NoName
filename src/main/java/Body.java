import com.raylib.Raylib.*;


public class Body
{
  public Vector3 position;
  public Vector3 velocity;
  public Vector3 dir;
  public boolean isGrounded;

  public Body()
  {
    this.position = new Vector3();
    this.velocity = new Vector3();
    this.dir = new Vector3();
    this.isGrounded = false;
  }
}