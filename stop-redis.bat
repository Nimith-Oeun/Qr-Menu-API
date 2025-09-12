@echo off
echo Stopping Redis services...
echo.

REM Check if Docker is running
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Docker is not running. Please start Docker Desktop first.
    pause
    exit /b 1
)

REM Stop Redis services
echo Stopping Redis and Redis Commander...
docker-compose -f docker-compose.redis.yml down

if %errorlevel% equ 0 (
    echo.
    echo ====================================
    echo Redis Services Stopped Successfully!
    echo ====================================
    echo.
) else (
    echo.
    echo Error: Failed to stop Redis services.
    echo Please check Docker and try again.
)

pause
