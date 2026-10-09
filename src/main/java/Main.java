import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Main
{
  public static void main(String[] args)
  {
    SetConfigFlags(FLAG_WINDOW_RESIZABLE | FLAG_WINDOW_HIGHDPI);
    InitWindow(1600, 900, "Anonym");
    DisableCursor();
    SetTargetFPS(120);
    
    Map map = new Map(10);
    Player NoName = new Player();
    while (!WindowShouldClose()) {
      NoName.update();
      
    BeginDrawing();
      ClearBackground(RAYWHITE);
      BeginMode3D(NoName.getCamera());
        map.drawMap();
      EndMode3D();
           
      DrawText(String.format("FPS: %d", GetFPS()),0, 0, 30, WHITE);
      EndDrawing();
    }
  }
}