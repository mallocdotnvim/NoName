import static com.raylib.Raylib.*;


public class Player
{
  private static final float GRAVITY = 32.0f;
  private static final float MAX_SPEED = 20.0f;
  private static final float CROUCH_SPEED = 5.0f;
  private static final float JUMP_FORCE = 12.0f;
  private static final float MAX_ACCEL = 150.0f;

  private static final float FRICTION = 0.86f;

  private static final float AIR_DRAG = 0.98f;

  private static final float CONTROL = 15.0f;
 
  private static final float CROUCH_HEIGHT = 0.0f;
  private static final float STAND_HEIGHT = 1.0f;
  
  private static final float BOTTOM_HEIGHT = 0.5f;

  private static final float WIDTH = 0.6f;
  private static final float HEIGHT = 1.8f;
  private static final float DEPTH = 0.6f;

  private static final boolean NORMALIZE_INPUT = false;

  private Vector2 lookRotation;
  private Vector2 lean;
  private Vector2 sensitivity;
  private float headTimer = 0.0f;

  public Body body;
  private Camera3D core;

  private float headLerp = STAND_HEIGHT;
  private float walkLerp = 0;

  
  public Player()
  {
    this.body = new Body();
    body.position
      .x(0.0f)
      .y(5.0f + (BOTTOM_HEIGHT + headLerp))
      .z(5.0f);

    this.core = new Camera3D();
    core._position(body.position);

    this.lookRotation = new Vector2()
      .x(0.0f)
      .y(0.0f); 
    this.lean = new Vector2()
      .x(0.0f)
      .y(0.0f);
    this.sensitivity = new Vector2()
      .x(0.001f)
      .y(0.001f); 
    this.headTimer = 0.0f;
  }
  
  private void updateBody(float rot, int side, int forward, boolean jumpPressed, boolean crouchHold)
  {
    Vector2 input = new Vector2()
      .x((float)side)
      .y((float)-forward);

    if (NORMALIZE_INPUT) {
      if ((side != 0) && (forward != 0)) {
        input = Vector2Normalize(input);
      }
    }

    float delta = GetFrameTime();
    if (!body.isGrounded) {
      body.velocity.y(body.velocity.y() - GRAVITY * delta);
    }

    if (body.isGrounded && jumpPressed) {
      body.velocity.y(JUMP_FORCE);
      body.isGrounded = false;
    }

    Vector3 front = new Vector3()
      .x((float)Math.sin(rot))
      .y(0.0f)
      .z((float)Math.cos(rot));

    Vector3 right = new Vector3()
      .x((float)Math.cos(-rot))
      .y(0.0f)
      .z((float)Math.sin(-rot));

    Vector3 desiredDir = new Vector3()
      .x(input.x() * right.x() + input.y() * front.x())
      .y(0.0f)
      .z(input.x() * right.z() + input.y() * front.z());

    body.dir = Vector3Lerp(body.dir, desiredDir, CONTROL * delta);

    float decel = (body.isGrounded ? FRICTION : AIR_DRAG);

    Vector3 hvel = new Vector3()
      .x(body.velocity.x() * decel)
      .y(0.0f)
      .z(body.velocity.z() * decel);

    float hvelLength = Vector3Length(hvel);
    if (hvelLength < (MAX_SPEED * 0.01f)) {
      hvel
        .x(0.0f)
        .y(0.0f)
        .z(0.0f);
    }

    float speed = Vector3DotProduct(hvel, body.dir);
    float maxSpeed = (crouchHold ? CROUCH_SPEED : MAX_SPEED);
    float accel = Clamp(maxSpeed - speed, 0.0f, MAX_ACCEL * delta);

    hvel
      .x(hvel.x() + body.dir.x() * accel)
      .z(hvel.z() + body.dir.z() * accel);

    body.velocity
      .x(hvel.x())
      .z(hvel.z());

    body.position
      .x(body.position.x() + body.velocity.x() * delta)
      .y(body.position.y() + body.velocity.y() * delta)
      .z(body.position.z() + body.velocity.z() * delta);

    if (body.position.y() <= 0.0f) {
      body.position.y(0.0f);
      body.velocity.y(0.0f);
      body.isGrounded = true;
    }
  }

  private void updataCameraFPS(Vector2 lookRotation, Vector2 lean, float headTimer)
  { 
    


    
    final Vector3 up = new Vector3()
      .x(0.0f)
      .y(1.0f)
      .z(0.0f);

    final Vector3 targetOffset = new Vector3()
      .x(0.0f)
      .y(0.0f)
      .z(-1.0f);

    Vector3 yaw = Vector3RotateByAxisAngle(targetOffset, up, lookRotation.x());

    float maxAngleUp = Vector3Angle(up, yaw);
    maxAngleUp -= 0.001f;

    if (-(lookRotation.y()) > maxAngleUp) {
      lookRotation.y(-maxAngleUp);
    }

    Vector3 right = Vector3Normalize(Vector3CrossProduct(yaw, up));

    float pitchAngle = -lookRotation.y() - lean.y();
    pitchAngle = Clamp(pitchAngle, -(float)(Math.PI / 2 + 0.0001f), (float)(Math.PI / 2 + 0.0001f));
    Vector3 pitch = Vector3RotateByAxisAngle(yaw, right, pitchAngle);

    float headSin = (float)Math.sin(headTimer * Math.PI);
    float headCos = (float)Math.cos(headTimer * Math.PI);
    final float stepRotation = 0.01f;

    core.up(Vector3RotateByAxisAngle(up, pitch, headSin * stepRotation + lean.x()));

    final float bobSide = 0.1f, bobUp = 0.15f;
    Vector3 bobbing = Vector3Scale(right, headSin * bobSide);
    bobbing.y(Math.abs(headCos * bobUp));

    core._position(Vector3Add(core._position(), Vector3Scale(bobbing, walkLerp)));
    core.target(Vector3Add(core._position(), pitch));
  }
  
  public void update()
  {
    Vector2 mouseDelta = GetMouseDelta();
    lookRotation
      .x(lookRotation.x() - mouseDelta.x() * sensitivity.x())
      .y(lookRotation.y() - mouseDelta.y() * sensitivity.y());

    int sideway = ((IsKeyDown(KEY_D) ? 1 : 0) - (IsKeyDown(KEY_A) ? 1 : 0));
    int forward = ((IsKeyDown(KEY_W) ? 1 : 0) - (IsKeyDown(KEY_S) ? 1 : 0));
    boolean crouching = IsKeyDown(KEY_LEFT_CONTROL);
    updateBody(lookRotation.x(), sideway, forward, IsKeyPressed(KEY_SPACE), crouching);

    float delta = GetFrameTime();
    headLerp = Lerp(headLerp, (crouching ? CROUCH_HEIGHT : STAND_HEIGHT), 20.0f * delta);
    core._position(new Vector3()
      .x(body.position.x())
      .y(body.position.y() + (BOTTOM_HEIGHT + headLerp))
      .z(body.position.z())
    );
    if (body.isGrounded && ((forward != 0) || (sideway != 0))) {
        headTimer += delta*3.0f;
        walkLerp = Lerp(walkLerp, 1.0f, 10.0f * delta);
        core.fovy(Lerp(core.fovy(), 55.0f, 5.0f * delta));
    } else {
      walkLerp = Lerp(walkLerp, 0.0f, 10.0f*delta);
      core.fovy(Lerp(core.fovy(), 60.0f, 5.0f * delta));
    }
    lean.x(Lerp(lean.x(), sideway*0.02f, 10.0f*delta));
    lean.y(Lerp(lean.y(), forward*0.015f, 10.0f*delta));
    updataCameraFPS(lookRotation, lean, headTimer);
  }

  public Camera3D getCamera()
  {
    return core;
  }
}
