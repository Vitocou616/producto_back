@echo off
REM Script para compilar y ejecutar la aplicación en Windows
REM Uso: run.bat

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║        Iniciando Producto API - Team 19                      ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.

REM Verificar Java
echo Verificando Java...
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ Error: Java no está instalado
    echo Descarga desde: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

for /f "tokens=*" %%i in ('java -version 2^>^&1 ^| findstr /R "version"') do set JAVA_VERSION=%%i
echo ✓ Java encontrado: %JAVA_VERSION%
echo.

REM Verificar Maven
echo Verificando Maven...
mvn -v >nul 2>&1
if errorlevel 1 (
    echo ⚠️ Maven no encontrado, intentando con mvnw...
    set MVN_CMD=mvnw.cmd
) else (
    echo ✓ Maven encontrado
    set MVN_CMD=mvn
)
echo.

REM Compilar
echo Compilando proyecto...
call %MVN_CMD% clean compile
if errorlevel 1 (
    echo ❌ Error en compilación
    pause
    exit /b 1
)
echo ✓ Compilación exitosa
echo.

REM Ejecutar
echo Iniciando aplicación...
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
call %MVN_CMD% spring-boot:run

echo.
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo ✓ Aplicación finalizada
pause
