import java.util.ArrayList;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Map 
{
  private ArrayList<Color> colors;
  private ArrayList<Float> heights;
  private ArrayList<Vector3> positions;

  private int MAX_VALUE;
  private float cubegen;

  public Map(int MAX_VALUE)
  {
    this.MAX_VALUE = MAX_VALUE;

    colors = new ArrayList<Color>(MAX_VALUE);
    heights = new ArrayList<Float>(MAX_VALUE);
    positions = new ArrayList<Vector3>(MAX_VALUE);
    terraGen();
  }
  
  private void terraGen()
  {
    this.cubegen = (float)GetRandomValue(5, 10);
    for (int i = 0; i < MAX_VALUE; i++) {
      heights.add((float)GetRandomValue(1, 50));
      positions.add(new Vector3()
        .x((float)GetRandomValue(-100, 100))
        .y(heights.get(i) / 2.0f)
        .z((float)GetRandomValue(-100, 100)));

      byte colorgen = (byte)GetRandomValue(0, 255);
      colors.add(new Color()
        .r(colorgen)
        .g(colorgen)
        .b(colorgen)
        .a((byte)255));
    }
  }
  
  public void drawMap()
  {
    Color color = GRAY;
    DrawPlane(new Vector3().x(0.0f).y(0.0f).z(0.0f),
              new Vector2().x(200.0f).y(200.0f),
              color);
    DrawSphere(new Vector3().x(150.0f).y(150.0f).z(0.0f), 50.0f, RED);
    DrawGrid(20, 10.0f);
    for (int i = 0; i < MAX_VALUE; i++) {
      DrawCube(positions.get(i), cubegen, heights.get(i), cubegen, colors.get(i));
    }
  }

  public BoundingBox getBoundingBox(int index)
  {
    Vector3 boxPosition = positions.get(index);
    float detect = cubegen / 2.0f;

    return new BoundingBox()
      .min(new Vector3()
        .x(boxPosition.x() - detect)
        .y(boxPosition.y() - detect)
        .z(boxPosition.z() - detect))
      .max(new Vector3()
        .x(boxPosition.x() + detect)
        .y(boxPosition.y() + detect)
        .z(boxPosition.z() + detect));
  }
}