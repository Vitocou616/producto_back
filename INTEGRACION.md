# Guía de Integración - Backend SpringBoot + Frontend React

## 📋 Descripción

Este es un backend Java/Spring Boot que implementa una API REST siguiendo la estructura de una aplicación Spring Boot profesional. Se integra perfectamente con el frontend React creado anteriormente.

## 🏗️ Estructura del Proyecto

```
producto_back/
├── src/main/java/com/team19/producto/
│   ├── ProductoApplication.java          ← Punto de entrada
│   ├── controller/                       ← REST API
│   ├── service/                          ← Lógica de negocio
│   ├── repository/                       ← Acceso a datos
│   ├── model/                            ← Entidades
│   ├── dto/                              ← Data Transfer Objects
│   ├── exception/                        ← Excepciones personalizadas
│   └── config/                           ← Configuración
├── src/main/resources/
│   └── application.properties            ← Configuración Spring
├── pom.xml                               ← Dependencias Maven
├── README.md                             ← Documentación
├── INICIO_RAPIDO.md                      ← Guía de inicio
├── run.sh / run.bat                      ← Scripts de ejecución
└── INTEGRACION.md                        ← Este archivo
```

## 🔌 Capas de la Aplicación

### 1. Controller (API REST)
- **Archivo**: `ProductoController.java`
- **Responsabilidad**: Maneja peticiones HTTP
- **Endpoints**:
  ```
  GET    /api/productos
  GET    /api/productos/{id}
  GET    /api/productos/buscar?nombre=
  POST   /api/productos
  PUT    /api/productos/{id}
  DELETE /api/productos/{id}
  GET    /api/health
  ```

### 2. Service (Lógica de Negocio)
- **Interfaz**: `IProductoService.java`
- **Implementación**: `ProductoServiceImpl.java`
- **Responsabilidad**: Validaciones y lógica de negocio
- **Métodos**:
  - `obtenerTodos()`
  - `obtenerPorId(id)`
  - `buscarPorNombre(nombre)`
  - `crearProducto(dto)`
  - `actualizarProducto(id, dto)`
  - `eliminarProducto(id)`

### 3. Repository (Persistencia)
- **Interfaz**: `IProductoRepository.java`
- **Implementación**: `ProductoRepositoryImpl.java` (JSON)
- **Responsabilidad**: CRUD en base de datos
- **Datos**: Persistidos en `productos.json`

### 4. Model (Entidades)
- **Clase**: `Producto.java`
- **Atributos**: id, nombre, precio, imagen, enlace, descripción, stock, timestamps

### 5. DTO (Transfer Objects)
- **ProductoDTO.java**: Para crear/actualizar productos
- **ApiResponse.java**: Respuesta estandarizada de la API

## 🚀 Compilación y Ejecución

### Requisitos
- Java 17 o superior
- Maven 3.8.1+

### Opción 1: Ejecutar directamente (Windows)
```bash
# Doble click en run.bat
run.bat
```

### Opción 2: Ejecutar directamente (Linux/Mac)
```bash
# Hacer ejecutable
chmod +x run.sh

# Ejecutar
./run.sh
```

### Opción 3: Desde Maven
```bash
cd producto_back
mvn spring-boot:run
```

### Opción 4: Compilar y ejecutar JAR
```bash
mvn clean package
java -jar target/producto-api-1.0.0.jar
```

## 🔗 Integración con React

El frontend React puede consumir esta API:

```javascript
// src/services/productsService.js
const API_BASE_URL = 'http://localhost:8080/api';

export const productsService = {
  async getAll() {
    const response = await fetch(`${API_BASE_URL}/productos`);
    return response.json();
  },
  
  async getById(id) {
    const response = await fetch(`${API_BASE_URL}/productos/${id}`);
    return response.json();
  },
  
  async create(product) {
    const response = await fetch(`${API_BASE_URL}/productos`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(product)
    });
    return response.json();
  }
  
  // ... más métodos
};
```

## 📊 Formato de Respuestas

### Éxito
```json
{
  "success": true,
  "message": "Operación exitosa",
  "data": {
    "id": 1,
    "nombre": "Producto",
    "precio": 9990,
    ...
  },
  "error": null
}
```

### Error
```json
{
  "success": false,
  "message": "Error en la operación",
  "data": null,
  "error": "El ID debe ser un número válido"
}
```

## 🔍 Pruebas de Endpoints

### Health Check
```bash
curl http://localhost:8080/api/health
```

### Obtener Todos
```bash
curl http://localhost:8080/api/productos
```

### Obtener por ID
```bash
curl http://localhost:8080/api/productos/1
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

### Actualizar
```bash
curl -X PUT http://localhost:8080/api/productos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Nombre Actualizado",
    "precio": 8990
  }'
```

### Eliminar
```bash
curl -X DELETE http://localhost:8080/api/productos/1
```

## 📁 Archivo de Datos

- **Ubicación**: `productos.json` (raíz del proyecto)
- **Formato**: JSON array
- **Contenido inicial**: 3 productos por defecto
- **Auto-guardado**: Después de cada operación

```json
[
  {
    "id": 1,
    "nombre": "New Mutants...",
    "precio": 5990,
    "imagen": "/src/images/covers/...",
    "enlace": "/producto/1",
    "descripcion": "Comic New Mutants",
    "stock": 100,
    "creadoEn": 1701964800000,
    "actualizadoEn": 1701964800000
  },
  ...
]
```

## 🔄 Flujo de Datos

```
┌─────────────┐
│   Frontend  │ (React en localhost:5173)
│   (React)   │
└──────┬──────┘
       │ HTTP Request
       ▼
┌─────────────┐
│ Spring Boot │ (Java en localhost:8080)
│ Controller  │ ← ProductoController.java
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Service   │ ← ProductoServiceImpl.java
│  (Lógica)   │ (Validaciones y reglas de negocio)
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Repository  │ ← ProductoRepositoryImpl.java
│  (Datos)    │ (Acceso a JSON)
└──────┬──────┘
       │
       ▼
┌─────────────┐
│productos.   │ (JSON file)
│   json      │
└─────────────┘
```

## 🛠️ Arquitectura Técnica

### Patrones Implementados
- **MVC**: Model-View-Controller (adaptado a REST)
- **Service Layer**: Separación de lógica
- **Repository Pattern**: Abstracción de datos
- **DTO Pattern**: Transferencia de datos
- **Exception Handling**: Manejo de errores personalizado

### Principios SOLID
- **S**ingle Responsibility: Cada clase tiene una responsabilidad
- **O**pen/Closed: Abierto para extensión, cerrado para modificación
- **L**iskov Substitution: Interfaces bien definidas
- **I**nterface Segregation: Interfaces específicas
- **D**ependency Inversion: Inyección de dependencias

## 📈 Escalabilidad

Para evolucionar a producción:

1. **Base de Datos Real**
   - Cambiar `ProductoRepositoryImpl` por JPA
   - Usar Spring Data JPA
   - MySQL, PostgreSQL, MongoDB, etc.

2. **Autenticación**
   - Spring Security
   - JWT tokens
   - OAuth2

3. **Caché**
   - Redis
   - Spring Cache

4. **Documentación API**
   - Swagger/Springdoc
   - OpenAPI 3.0

5. **Testing**
   - JUnit 5
   - Mockito
   - Integration Tests

6. **Seguridad**
   - HTTPS/TLS
   - CORS configurado
   - Validación de entrada
   - SQL Injection protection

## 🆚 Comparativa: Node.js vs Spring Boot

| Aspecto | Node.js | Spring Boot |
|---------|---------|------------|
| Framework | Express | Spring Framework |
| Lenguaje | JavaScript | Java |
| Puerto | 5000 | 8080 |
| BD actual | JSON | JSON |
| Performance | Bueno | Muy Bueno |
| Escalabilidad | Media | Alta |
| Equipo | 1 desarrollador | 1+ desarrolladores |
| Curve Learning | Baja | Media |
| Producción | Posible | Excelente |

## 🚀 Ejecución Simultánea

Para ejecutar todos los servicios:

```bash
# Windows
run-all.bat

# Linux/Mac
./run-all.sh
```

Esto abrirá 3 terminales:
1. API Node.js (5000)
2. Frontend React (5173)
3. API Spring Boot (8080)

## 📞 Puertos

| Servicio | Puerto | URL |
|----------|--------|-----|
| Node.js API | 5000 | http://localhost:5000 |
| React Frontend | 5173 | http://localhost:5173 |
| Spring Boot | 8080 | http://localhost:8080 |

## 🔒 Configuración CORS

Habilitado en `application.properties`:
```properties
cors.allowed-origins=*
cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
cors.max-age=3600
```

## 📝 Logs

Configurar en `application.properties`:
```properties
logging.level.root=INFO
logging.level.com.team19=DEBUG
```

## ✅ Checklist de Verificación

- [ ] Java 17+ instalado (`java -version`)
- [ ] Maven instalado (`mvn -version`)
- [ ] Compilar: `mvn clean compile`
- [ ] Ejecutar: `mvn spring-boot:run`
- [ ] Health: `curl http://localhost:8080/api/health`
- [ ] GET: `curl http://localhost:8080/api/productos`
- [ ] React conecta a http://localhost:8080/api

## 🎯 Próximos Pasos

1. ✅ Verificar que Spring Boot compila
2. ✅ Ejecutar y probar endpoints
3. ✅ Conectar React al backend Java
4. ✅ Elegir backend preferido para producción
5. ✅ Migrar a BD real si es necesario

---

**Creado**: Diciembre 2024
**Equipo**: Team 19
**Versión**: 1.0.0
