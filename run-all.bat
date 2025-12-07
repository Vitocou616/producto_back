@echo off
REM Script para integración de backends en Windows

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║     Iniciando Ambos Servidores - Backend y Backend Java      ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.

echo Abriendo terminales para ambas aplicaciones...
echo.

REM Abrir nuevas ventanas de terminal
start cmd /k "cd /d %~dp0..\team_19 && npm run dev:server"
timeout /t 2 /nobreak

start cmd /k "cd /d %~dp0..\team_19 && npm run dev"
timeout /t 2 /nobreak

start cmd /k "cd /d %~dp0 && mvn spring-boot:run"

echo.
echo ✓ Tres ventanas de terminal se han abierto:
echo   1. Servidor Express/Node.js (puerto 5000)
echo   2. React Frontend (puerto 5173)
echo   3. Spring Boot Backend (puerto 8080)
echo.

echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo Accesos:
echo   • Frontend React:    http://localhost:5173
echo   • API Node.js:       http://localhost:5000/api/productos
echo   • API Spring Boot:   http://localhost:8080/api/productos
echo.
echo Health checks:
echo   • Node.js:           http://localhost:5000/api/health
echo   • Spring Boot:       http://localhost:8080/api/health
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.

pause
