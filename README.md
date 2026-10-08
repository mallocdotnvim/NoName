# NoName (無名)

NoName (無名) is a first-person-shooter game using Jaylib. (Yes I'm not kidding — Raylib in Java.)

> [!WARNING]
> Please read the `README.md` before building the project.

> [!IMPORTANT]
> This is just a showcase project for an OOP lecture, feel free to modify anything!

## Structure

```tree
NoName/
├── assets/
├── lib/
├── scripts/
│   └── setup-unix
├── src/
│   └── main/
│       └── java/
│           ├── Map.java
│           ├── Main.java
│           ├── Body.java
│           └── Player.java
├── pox.xml
└── README.md
```


## Build 

Dependencies: 
- Java 21
- [Jaylib Library](https://github.com/electronstudio/jaylib/)

### macOS and Linux
```console
chmod +x scripts/setup-unix
./scripts/setup-unix
```

## Todo

- [x] Camera control and basic movements
- [x] Simple map generator
- [] Collision Boxes 
- [] Assets (such as picture, music, font, etc.)
- [] Basic UI
- [] Rewrite Windows `bat` file (this is somehow difficult)
- [] Releases
 