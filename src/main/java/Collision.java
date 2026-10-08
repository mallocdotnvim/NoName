import static com.raylib.Raylib.*;

public class Collision
{
  public static boolean checkCollision(BoundingBox self, BoundingBox other)
  {
    return CheckCollisionBoxes(self, other);
  }
}