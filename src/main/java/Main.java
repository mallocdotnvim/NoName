import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Main
{
  public static void main(String[] args)
  {
    InitWindow(800, 450, "Anonym");
    DisableCursor();
    SetTargetFPS(120);
    

    Map map = new Map(50);
    Player NoName = new Player();
    
    
    while (!WindowShouldClose()) {
      NoName.update();
      BeginDrawing();
      ClearBackground(new Color().r((byte)30).g((byte)30).b((byte)30).a((byte)120));
      BeginMode3D(NoName.getCamera());
        map.drawMap();
      EndMode3D();
      DrawText(String.format("FPS: %d", GetFPS()),0, 0, 30, WHITE);
      EndDrawing();
    }
  }
}