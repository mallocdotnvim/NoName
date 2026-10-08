@echo off
setlocal

java -version

if exist out (
    rmdir /s /q out
)

mkdir out

javac -cp "lib\jaylib-6.0.1-0.jar" -d out src\main\java\*.java

java -cp "lib\jaylib-6.0.1-0.jar;out" Main

pause