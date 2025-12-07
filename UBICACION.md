# 📍 UBICACIÓN DEL BACKEND

**Carpeta:** `C:\Users\cachu\Desktop\Duoc\fullstack-2\Nueva carpeta\producto_back`

## ✅ Contenido

- ✅ **21 archivos Java** (controllers, services, repositories, models, DTOs, utils)
- ✅ **pom.xml** (dependencias Maven)
- ✅ **application.properties** (configuración Spring Boot)
- ✅ **target/producto-api-1.0.0.jar** (JAR compilado - 26 MB)
- ✅ **users.json** (usuarios de prueba)
- ✅ **Scripts BAT** (iniciar-servidor.bat, recompilar-backend.bat)

## 🚀 Inicio Rápido

### Opción 1: Doble clic
```
iniciar-servidor.bat
```

### Opción 2: Línea de comandos
```bash
cd "C:\Users\cachu\Desktop\Duoc\fullstack-2\Nueva carpeta\producto_back"
java -jar target/producto-api-1.0.0.jar
```

## 📦 Productos por Defecto

El backend incluye 3 productos de Marvel:

1. **New Mutants Combate el Futuro 3 de 3** - $5,990
2. **Patrulla X Especie en Peligro 13** - $8,990
3. **Superior Ironman** - $15,990

## 🔐 Usuarios de Prueba

- **Admin:** admin@test.com / admin123
- **Usuario:** user@test.com / user123

## 📡 API Endpoints

**Base URL:** http://localhost:8080

- `GET /api/productos` - Listar productos
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registro
- `POST /api/productos` - Crear (requiere ADMIN)
- `PUT /api/productos/{id}` - Actualizar (requiere ADMIN)
- `DELETE /api/productos/{id}` - Eliminar (requiere ADMIN)

## ⚠️ Importante

- **Puerto:** 8080 (debe estar libre)
- **Firewall:** Desactivar o agregar excepción para Java
- **Java:** Requiere JDK 17+

## 🔄 Recompilar

Si modificas el código:
```bash
recompilar-backend.bat
```

Requiere Maven instalado.
