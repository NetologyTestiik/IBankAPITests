@echo off
set JAVA_HOME=%JAVA_HOME%
set GRADLE_HOME=%GRADLE_HOME%
if "%GRADLE_HOME%"=="" (
    echo Using system Gradle...
    gradle %*
) else (
    "%GRADLE_HOME%\bin\gradle" %*
)
