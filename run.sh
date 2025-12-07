#!/bin/bash

# Script para compilar y ejecutar la aplicación
# Uso: ./run.sh

echo "╔══════════════════════════════════════════════════════════════╗"
echo "║        Iniciando Producto API - Team 19                      ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo ""

# Verificar Java
echo "Verificando Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java no está instalado"
    echo "Descarga desde: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -1)
echo "✓ Java encontrado: $JAVA_VERSION"
echo ""

# Verificar Maven
echo "Verificando Maven..."
if ! command -v mvn &> /dev/null; then
    echo "⚠️ Maven no encontrado, usando mvnw"
    MVN_CMD="./mvnw"
else
    MVN_CMD="mvn"
    echo "✓ Maven encontrado"
fi
echo ""

# Compilar
echo "Compilando proyecto..."
$MVN_CMD clean compile
if [ $? -ne 0 ]; then
    echo "❌ Error en compilación"
    exit 1
fi
echo "✓ Compilación exitosa"
echo ""

# Ejecutar
echo "Iniciando aplicación..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
$MVN_CMD spring-boot:run

echo ""
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "✓ Aplicación finalizada"
