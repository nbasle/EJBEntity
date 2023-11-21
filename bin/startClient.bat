@echo off

set JAVA=%JAVA_HOME%\bin\java
set DEPLOY_DIR=..\build
set LIB_DIR=..\lib

set CLASSPATH=%DEPLOY_DIR%\client.jar;%DEPLOY_DIR%\common.jar;%DEPLOY_DIR%\services-ejb-client.jar;%LIB_DIR%\jbossall-client.jar

%JAVA% -cp %CLASSPATH% com.yaps.petstore.client.ui.Menu