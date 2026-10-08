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
├── out/
├── scripts/
│   ├── setup-unix
│   └── setup-windows
├── src/
│   └── main/
│       └── java/
│           ├── Map.java
│           ├── Main.java
│           ├── Body.java
│           └── Player.java
├── pox.xml
├── target/
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

### Windows
```console
scripts/setup-windows
```
