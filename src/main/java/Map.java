import java.util.ArrayList;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Map 
{
  private ArrayList<Color> colors;
  private ArrayList<Float> heights;
  private ArrayList<Vector3> positions;

  public int MAP_SIZE;
  private float cubegen;

  public Map(int MAP_SIZE)
  {
    this.MAP_SIZE = MAP_SIZE;

    colors = new ArrayList<Color>(MAP_SIZE);
    heights = new ArrayList<Float>(MAP_SIZE);
    positions = new ArrayList<Vector3>(MAP_SIZE);
    terraGen();
  }
  
  private void terraGen()
  {
    this.cubegen = (float)GetRandomValue(5, 10);
    for (int i = 0; i < MAP_SIZE; i++) {
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
    for (int i = 0; i < MAP_SIZE; i++) {
      DrawCube(positions.get(i), cubegen, heights.get(i), cubegen, colors.get(i));
    }
  }

}