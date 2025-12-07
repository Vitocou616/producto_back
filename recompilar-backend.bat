@echo off
echo ================================================
echo   RECOMPILANDO BACKEND CON MAVEN
echo ================================================
echo.

REM Verificar si Maven está instalado
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven no está instalado.
    echo.
    echo Por favor instala Maven desde: https://maven.apache.org/download.cgi
    echo O usa Maven Wrapper si está disponible.
    echo.
    pause
    exit /b 1
)

echo [1/3] Limpiando archivos anteriores...
call mvn clean

echo.
echo [2/3] Compilando código fuente...
call mvn compile

echo.
echo [3/3] Empaquetando JAR...
call mvn package -DskipTests

echo.
echo ================================================
echo   COMPILACION COMPLETADA
echo ================================================
echo El JAR está en: target\producto-api-1.0.0.jar
echo.
echo Para iniciar el servidor ejecuta:
echo   java -jar target\producto-api-1.0.0.jar
echo.
pause
