# Gestor de tareas

Aplicación web para crear, consultar, actualizar, filtrar y eliminar tareas. Proyecto académico desarrollado con Spring Boot, React y PostgreSQL.

## Tecnologías
- Backend: Java 17, Spring Boot 3.5.7, Spring Data JPA y Bean Validation.
- Frontend: React 18 y Vite.
- Base de datos: PostgreSQL 16.
- API: REST/JSON.

## Arquitectura hexagonal
El backend separa el dominio de los detalles técnicos:
- `domain`: modelo y reglas de negocio (`Tarea`, `EstadoTarea`, `Prioridad`).
- `application/port/in`: caso de uso que expone las operaciones disponibles.
- `application/port/out`: puerto de persistencia requerido por la aplicación.
- `application/service`: implementación de los casos de uso.
- `infrastructure/persistence`: adaptador PostgreSQL/JPA.
- `infrastructure/web`: adaptador REST, DTO y manejo de errores.

La capa de aplicación depende de interfaces (puertos), no de Spring Data ni de la base de datos. Los adaptadores implementan o utilizan esos puertos.

## Requisitos
- Java 17 o superior.
- Maven 3.9 o superior.
- Node.js 20 o superior y npm.
- PostgreSQL 16 o Docker Desktop.

## Ejecutar la base de datos
Desde la carpeta raíz:

```bash
docker compose up -d
```

Esto crea PostgreSQL con:
- Base de datos: `gestor_tareas`
- Usuario: `postgres`
- Contraseña local de desarrollo: `postgres`
- Puerto: `5432`

Si ya tienes PostgreSQL instalado, crea una base de datos llamada `gestor_tareas` y ajusta usuario/contraseña en `backend/src/main/resources/application.properties` o mediante las variables `DB_URL`, `DB_USER` y `DB_PASSWORD`.

## Ejecutar backend
En una terminal:

```bash
cd backend
mvn spring-boot:run
```

Backend: `http://localhost:8080`

## Ejecutar frontend
En otra terminal:

```bash
cd frontend
npm install
npm run dev
```

Frontend: `http://localhost:5173`

## API REST
| Método | Ruta | Función |
|---|---|---|
| POST | `/api/tareas` | Crear tarea |
| GET | `/api/tareas` | Listar tareas |
| GET | `/api/tareas/{id}` | Consultar una tarea |
| PUT | `/api/tareas/{id}` | Actualizar tarea |
| DELETE | `/api/tareas/{id}` | Eliminar tarea |
| GET | `/api/tareas?estado=PENDIENTE` | Filtrar por estado |
| GET | `/api/tareas?prioridad=ALTA` | Filtrar por prioridad |

Ejemplo de creación:

```json
{
  "titulo": "Preparar presentación",
  "descripcion": "Finalizar la actividad de arquitectura",
  "prioridad": "ALTA",
  "fechaLimite": "2026-11-15"
}
```

Estados: `PENDIENTE`, `EN_PROGRESO`, `COMPLETADA`.
Prioridades: `BAJA`, `MEDIA`, `ALTA`.

## Calidad del código
- **SRP:** el controlador gestiona HTTP, el servicio coordina casos de uso y el adaptador persiste datos.
- **OCP/DIP:** la aplicación trabaja contra `TareaRepositoryPort`; se puede sustituir el adaptador de persistencia sin modificar los casos de uso.
- **LSP/ISP:** interfaces pequeñas y contratos específicos evitan dependencias innecesarias.
- **Clean Code:** nombres expresivos, métodos enfocados, DTO para entrada HTTP y manejo centralizado de errores.
- **Refactoring:** separación de responsabilidades, extracción de puertos/adaptadores y centralización de validación y errores.

## Pruebas
En `backend`:

```bash
mvn test
```

## Publicar en GitHub
1. Crea un repositorio vacío en GitHub.
2. Desde esta carpeta ejecuta:

```bash
git init
git add .
git commit -m "Implementa gestor de tareas con arquitectura hexagonal"
git branch -M main
git remote add origin URL_DE_TU_REPOSITORIO
git push -u origin main
```

> Proyecto académico en entorno local. Antes de desplegarlo públicamente, cambia las credenciales de desarrollo y configura CORS para el dominio real del frontend.
