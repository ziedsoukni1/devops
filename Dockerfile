# Étape 1 : Utiliser une image de base avec JDK pour construire l'application
FROM maven:3.8.5-openjdk-17 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration Maven et les fichiers sources
COPY pom.xml .
COPY src ./src

# Compiler l'application
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser une image JRE pour exécuter l'application
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans l'image finale
WORKDIR /app

# Copier le fichier .jar généré dans l'image finale
COPY --from=build /app/target/*.jar app.jar

# Exposer le port que votre application utilise (exemple : 8080)
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
