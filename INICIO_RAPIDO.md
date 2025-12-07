# Guía de Inicio Rápido - Backend Producto API

## 📋 Requisitos

- Java 17 o superior
- Maven 3.8.1 o superior
- Git (opcional)

## 🚀 Pasos para Ejecutar

### 1. Verificar Requisitos

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version
```

### 2. Compilar el Proyecto

```bash
cd producto_back
mvn clean compile
```

### 3. Ejecutar la Aplicación

**Opción A: Desde Maven (Recomendado)**
```bash
mvn spring-boot:run
```

**Opción B: Ejecutar JAR**
```bash
mvn clean package
java -jar target/producto-api-1.0.0.jar
```

**Opción C: Desde IDE (Eclipse/IntelliJ)**
- Click derecho en `ProductoApplication.java`
- Seleccionar "Run As" → "Java Application"

## ✅ Verificar que Funciona

### Health Check
```bash
curl http://localhost:8080/api/health
```

Deberías ver:
```json
{
  "success": true,
  "message": "API funcionando correctamente",
  "data": "OK"
}
```

### Obtener Todos los Productos
```bash
curl http://localhost:8080/api/productos
```

### Crear Producto
```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Nuevo Cómic",
    "precio": 9990,
    "imagen": "/path/imagen.jpg",
    "enlace": "/producto/4",
    "descripcion": "Descripción",
    "stock": 100
  }'
```

## 📁 Estructura de Carpetas

```
producto_back/
├── src/main/java/com/team19/producto/
│   ├── controller/          ← REST API
│   ├── service/             ← Lógica
│   ├── repository/          ← Datos
│   ├── model/               ← Entidades
│   ├── dto/                 ← Transfer Objects
│   ├── exception/           ← Excepciones
│   └── config/              ← Configuración
├── src/main/resources/
│   └── application.properties
├── pom.xml                  ← Dependencias Maven
└── README.md
```

## 🔍 Logs

Para ver logs de debug:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.com.team19=DEBUG"
```

## 🐛 Troubleshooting

### Puerto 8080 ya en uso
```bash
# Cambiar puerto en application.properties
server.port=9090
```

### Error de compilación Java
```bash
# Verificar versión de Java
java -version

# Debe ser 17 o superior
```

### Maven no encontrado
```bash
# Agregar Maven al PATH o usar
./mvnw spring-boot:run
```

## 📊 Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Obtener todos |
| GET | `/api/productos/{id}` | Obtener uno |
| GET | `/api/productos/buscar?nombre=` | Buscar |
| POST | `/api/productos` | Crear |
| PUT | `/api/productos/{id}` | Actualizar |
| DELETE | `/api/productos/{id}` | Eliminar |

## 🔗 Integración con Frontend

El frontend React puede conectar a esta API usando:

```javascript
const API_URL = 'http://localhost:8080/api';

// Obtener productos
fetch(`${API_URL}/productos`)
  .then(r => r.json())
  .then(data => console.log(data.data))
```

## 📝 Archivo de Datos

- Ubicación: `productos.json` (raíz del proyecto)
- Se crea automáticamente al ejecutar
- Contiene los 3 productos por defecto
- Se actualiza automáticamente con cambios

## 🎯 Próximos Pasos

1. ✅ Ejecutar la aplicación
2. ✅ Probar endpoints con Postman o curl
3. ✅ Integrar con frontend React
4. ✅ Agregar autenticación si es necesario
5. ✅ Migrar a base de datos real (MySQL, PostgreSQL)

## 📚 Recursos

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Boot REST](https://spring.io/guides/gs/rest-service/)
- [Maven Guide](https://maven.apache.org/guides/getting-started/)

---

**¡Listo para desarrollar!** 🚀
