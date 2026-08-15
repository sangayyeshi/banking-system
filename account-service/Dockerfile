# 1. Base Image: Bring the kitchen and tools
FROM eclipse-temurin:21-jdk

# 2. Working Directory: Set the kitchen counter space
WORKDIR /app

# 3. Copy files: Bring the pre-measured ingredients into the kitchen
COPY target/*.jar app.jar


# 4. Expose Port: Open a window so customers can order food
EXPOSE 8082

# 5. Entrypoint: Turn on the stove and start cooking
ENTRYPOINT ["java", "-jar", "app.jar"]
