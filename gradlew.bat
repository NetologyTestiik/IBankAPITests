@echo off
rem Minimal Gradle wrapper for Windows
rem If wrapper doesn't work, use system Gradle

set GRADLE_VERSION=8.5
set GRADLE_CMD=gradle

rem Check if wrapper jar exists
if exist "gradle\wrapper\gradle-wrapper.jar" (
    rem Use wrapper
    java -jar gradle\wrapper\gradle-wrapper.jar %*
) else (
    rem Use system gradle
    echo Using system Gradle...
    %GRADLE_CMD% %*
)
