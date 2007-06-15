@echo off
rem @version $Revision$ ($Author$)  $Date$
SETLOCAL
call %~dp0scripts\version.cmd

rem set JVM_ARGS=-Dbluecove.stack=widcomm

set TITLE=bluecove-tester
set ERRORS=0
set SUCCESS=0
echo Starting > run-continuous.cmd.log
:startagain
title %TITLE%  SUCCESS=%SUCCESS% ERRORS=%ERRORS%
java %JVM_ARGS% -jar target\bluecove-tester-%VERSION%-app.jar --runonce  >>  run-continuous.cmd.log
if errorlevel 2 (
    echo Error calling java
    set /A ERRORS+=1
    rem pause
    rem exit /b 1
    goto startagain
)
if errorlevel 1 (
    set /A SUCCESS+=1
    goto startagain
)
echo Done SUCCESS=%SUCCESS% ERRORS=%ERRORS%
pause
ENDLOCAL