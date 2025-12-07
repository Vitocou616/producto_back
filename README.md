# Estructura del Proyecto Backend - Producto API Team 19

## 📁 Estructura de Carpetas (Estilo SpringBoot)

```
producto_back/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/team19/producto/
│   │   │       ├── ProductoApplication.java          (Clase principal)
│   │   │       ├── controller/
│   │   │       │   └── ProductoController.java       (REST Controller)
│   │   │       ├── service/
│   │   │       │   ├── IProductoService.java         (Interface)
│   │   │       │   └── ProductoServiceImpl.java       (Implementación)
│   │   │       ├── repository/
│   │   │       │   ├── IProductoRepository.java      (Interface)
│   │   │       │   └── ProductoRepositoryImpl.java    (Implementación JSON)
│   │   │       ├── model/
│   │   │       │   └── Producto.java                 (Entidad)
│   │   │       ├── dto/
│   │   │       │   ├── ProductoDTO.java              (Data Transfer Object)
│   │   │       │   └── ApiResponse.java              (Respuesta genérica)
│   │   │       ├── exception/
│   │   │       │   ├── ResourceNotFoundException.java
│   │   │       │   └── BadRequestException.java
│   │   │       └── config/
│   │   │           └── AppConfig.java                (Configuración)
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/...
├── pom.xml                                           (Maven config)
├── README.md
└── ARQUITECTURA.md
```

## 🏗️ Arquitectura en Capas

```
┌─────────────────────────────────┐
│   ProductoController            │  ← REST API Layer
│   (Maneja peticiones HTTP)      │
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│   ProductoServiceImpl            │  ← Business Logic Layer
│   (Lógica de negocio)           │
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│   ProductoRepositoryImpl         │  ← Data Access Layer
│   (Acceso a datos JSON)         │
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│   productos.json                │  ← Database Layer
│   (Persistencia de datos)       │
└─────────────────────────────────┘
```

## 📋 Componentes Principales

### 1. **Model (Entidad)**
- `Producto.java` - Representa la entidad Producto con atributos

### 2. **DTO (Transfer Objects)**
- `ProductoDTO.java` - Para recibir/enviar datos desde la API
- `ApiResponse.java` - Respuesta estandarizada de la API

### 3. **Repository (Acceso a Datos)**
- `IProductoRepository.java` - Interface del repositorio
- `ProductoRepositoryImpl.java` - Implementación con persistencia JSON

### 4. **Service (Lógica de Negocio)**
- `IProductoService.java` - Interface del servicio
- `ProductoServiceImpl.java` - Implementación de la lógica

### 5. **Controller (API REST)**
- `ProductoController.java` - Maneja las peticiones HTTP

### 6. **Exception (Manejo de Errores)**
- `ResourceNotFoundException.java` - Excepción para recurso no encontrado
- `BadRequestException.java` - Excepción para validaciones

### 7. **Config (Configuración)**
- `AppConfig.java` - Configuración global de la aplicación

## 🔌 Endpoints REST

```
GET    /api/productos              → Obtener todos
GET    /api/productos/{id}         → Obtener por ID
GET    /api/productos/buscar?q=    → Buscar por nombre
POST   /api/productos              → Crear
PUT    /api/productos/{id}         → Actualizar
DELETE /api/productos/{id}         → Eliminar
GET    /api/health                 → Health check
```

## 🔄 Flujo de una Petición

1. **Cliente** envía: `POST /api/productos`
2. **Spring Boot** recibe y rutea a `ProductoController`
3. **Controller** valida y llama a `ProductoService`
4. **Service** aplica lógica de negocio
5. **Repository** persiste en `productos.json`
6. **API** devuelve respuesta en formato `ApiResponse`
7. **Cliente** recibe JSON con resultado

## 📦 Dependencias Principal

- Spring Boot 3.2.0
- Java 17
- Lombok (anotaciones)
- Jackson (JSON)
- Maven (build tool)

## 🚀 Compilación y Ejecución

### Con Maven:
```bash
# Compilar
mvn clean compile

# Ejecutar tests
mvn test

# Empaquetar
mvn clean package

# Ejecutar JAR
java -jar target/producto-api-1.0.0.jar

# Desarrollo (con hot reload)
mvn spring-boot:run
```

## 🏛️ Principios de Diseño

- **Separation of Concerns**: Cada capa tiene su responsabilidad
- **Dependency Injection**: Inyección de dependencias (Spring)
- **Interface Segregation**: Interfaces para cada componente
- **Open/Closed**: Abierto para extensión, cerrado para modificación
- **SOLID**: Principios SOLID aplicados

## 🔒 Seguridad

- Validaciones en DTO y Service
- Manejo de excepciones personalizado
- API Response estandarizada
- Logging de operaciones

## 📊 Persistencia

- Datos almacenados en `productos.json`
- Auto-cargable al iniciar aplicación
- Auto-guardado después de cada cambio
- Formato JSON legible

## 🧪 Testing

Estructura lista para:
- Unit Tests (Service)
- Integration Tests (Controller)
- Repository Tests

## 📝 Convenciones

- Package naming: `com.team19.producto.*`
- Clases: PascalCase
- Métodos: camelCase
- Constantes: UPPER_CASE
- Interfaces: IPrefijo

## 🛠️ Extensión Futura

Para convertir a base de datos real:
1. Reemplazar `ProductoRepositoryImpl` con JPA
2. Agregar `@Entity` a `Producto`
3. Crear `ProductoJPARepository extends JpaRepository`
4. Configurar `application.properties` con BD

## 📞 Integración con React

El frontend puede consumir esta API usando:
```javascript
fetch('http://localhost:8080/api/productos')
  .then(r => r.json())
  .then(data => console.log(data))
```

---

**Fecha**: Diciembre 2024
**Versión**: 1.0.0
**Equipo**: Team 19
