#!/usr/bin/env bash

# Script para integración de backends
# Este script permite ejecutar ambos servidores simultáneamente

echo "╔══════════════════════════════════════════════════════════════╗"
echo "║     Iniciando Ambos Servidores - Backend y Backend Java      ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo ""

# Verificar si estamos en Windows o Linux
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
    echo "Sistema detectado: Windows"
    echo ""
    echo "Abriendo terminales para ambas aplicaciones..."
    echo ""
    
    # En Windows, abrir nuevas ventanas de terminal
    start cmd /k "cd /d %cd%\..\team_19 && npm run dev:server"
    start cmd /k "cd /d %cd%\..\team_19 && npm run dev"
    start cmd /k "cd /d %cd% && mvn spring-boot:run"
    
    echo "✓ Tres ventanas de terminal se han abierto:"
    echo "  1. Servidor Express/Node.js (puerto 5000)"
    echo "  2. React Frontend (puerto 5173)"
    echo "  3. Spring Boot Backend (puerto 8080)"
    echo ""
else
    echo "Sistema detectado: Linux/Mac"
    echo ""
    echo "Iniciando servicios en background..."
    echo ""
    
    # En Linux/Mac, ejecutar en background
    cd ../team_19
    npm run dev:server > /tmp/node-server.log 2>&1 &
    npm run dev > /tmp/react-dev.log 2>&1 &
    cd ../producto_back
    mvn spring-boot:run > /tmp/java-server.log 2>&1 &
    
    echo "✓ Servicios iniciados:"
    echo "  • Node.js Server en background (logs: /tmp/node-server.log)"
    echo "  • React Dev en background (logs: /tmp/react-dev.log)"
    echo "  • Java Server en background (logs: /tmp/java-server.log)"
    echo ""
    echo "Para detener, ejecute: pkill -f 'node|npm|mvn'"
fi

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "Accesos:"
echo "  • Frontend React:    http://localhost:5173"
echo "  • API Node.js:       http://localhost:5000/api/productos"
echo "  • API Spring Boot:   http://localhost:8080/api/productos"
echo ""
echo "Health checks:"
echo "  • Node.js:           http://localhost:5000/api/health"
echo "  • Spring Boot:       http://localhost:8080/api/health"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
