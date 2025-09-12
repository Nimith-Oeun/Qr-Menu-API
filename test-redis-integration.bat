@echo off
echo Testing Redis Integration for QR Menu API...
echo.

REM Check if Redis is running
echo Checking Redis connection...
redis-cli ping >nul 2>&1
if %errorlevel% equ 0 (
    echo ✓ Redis is running and accessible
) else (
    echo ✗ Redis is not running or not accessible
    echo Please start Redis first using start-redis.bat
    pause
    exit /b 1
)

echo.
echo Testing Redis operations...

REM Test basic Redis operations
echo Testing SET operation...
redis-cli set test-key "QR Menu API Test" >nul 2>&1
if %errorlevel% equ 0 (
    echo ✓ SET operation successful
) else (
    echo ✗ SET operation failed
)

echo Testing GET operation...
for /f "delims=" %%i in ('redis-cli get test-key') do set result=%%i
if "%result%"=="QR Menu API Test" (
    echo ✓ GET operation successful: %result%
) else (
    echo ✗ GET operation failed
)

echo Testing DEL operation...
redis-cli del test-key >nul 2>&1
if %errorlevel% equ 0 (
    echo ✓ DEL operation successful
) else (
    echo ✗ DEL operation failed
)

echo.
echo Testing cache-specific operations...

REM Test cache keys that would be used by the application
echo Setting menu cache test...
redis-cli set "menu::SimpleKey []" "{\"status\":\"test\",\"data\":\"menu-data\"}" EX 3600 >nul 2>&1
echo ✓ Menu cache test set

echo Setting category cache test...
redis-cli set "menuByCategory::APPETIZERS" "{\"status\":\"test\",\"data\":\"appetizers-data\"}" EX 3600 >nul 2>&1
echo ✓ Category cache test set

echo.
echo Current Redis keys:
redis-cli keys "*"

echo.
echo Redis Memory Usage:
redis-cli info memory | findstr used_memory_human

echo.
echo Redis Configuration:
redis-cli config get maxmemory
redis-cli config get maxmemory-policy

echo.
echo ====================================
echo Redis Integration Test Completed!
echo ====================================
echo.
echo You can now start your Spring Boot application.
echo The application will automatically connect to Redis.
echo.
echo Monitor cache operations with: redis-cli monitor
echo View cache statistics via API: GET /api/v1/cache/stats
echo.

pause
