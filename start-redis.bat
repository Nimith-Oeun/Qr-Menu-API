@echo off
echo Starting Redis with Docker Compose...
echo.

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Docker is not running. Please start Docker Desktop first.
    pause
    exit /b 1
)

REM Start Redis services
echo Starting Redis and Redis Commander...
docker-compose -f docker-compose.redis.yml up -d

if %errorlevel% equ 0 (
    echo.
    echo =====================================
    echo Redis Services Started Successfully!
    echo =====================================
    echo.
    echo Redis Server: localhost:6379
    echo Redis Commander UI: http://localhost:8081
    echo.
    echo Use the following commands to manage:
    echo - Stop: docker-compose -f docker-compose.redis.yml down
    echo - View logs: docker-compose -f docker-compose.redis.yml logs
    echo - Restart: docker-compose -f docker-compose.redis.yml restart
    echo.
) else (
    echo.
    echo Error: Failed to start Redis services.
    echo Please check Docker and try again.
)

pause
