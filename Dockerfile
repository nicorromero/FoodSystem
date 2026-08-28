# --- Etapa 1: Build y resolución de dependencias ---
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# 1. Copiar wrapper y descriptores de Maven
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# 2. Descargar dependencias para aprovechar la caché de Docker
RUN ./mvnw dependency:go-offline -B

# 3. Copiar el código fuente (lo que más cambia) y compilar
COPY src/ src/
RUN ./mvnw package -DskipTests -B

# --- Etapa 2: Runtime ligero ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Seguridad: ejecutar con un usuario sin privilegios
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copiar solo el binario compilado
COPY --from=build --chown=appuser:appgroup /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
