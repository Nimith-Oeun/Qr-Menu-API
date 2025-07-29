@echo off
title API Integration Test

echo 🧪 TESTING BACKEND API ENDPOINTS
echo ==================================
echo.

echo Testing if backend is running on port 8090...
echo.

REM Test basic menu endpoint
echo 📋 Testing: GET /api/menu
curl -s -o nul -w "%%{http_code}" http://localhost:8090/api/menu
if %errorlevel% equ 0 (
    echo ✅ Menu API is responding
) else (
    echo ❌ Menu API not responding - Is backend running?
)

echo.

REM Test separated menu endpoint  
echo 📋 Testing: GET /api/menu-separated
curl -s -o nul -w "%%{http_code}" http://localhost:8090/api/menu-separated
if %errorlevel% equ 0 (
    echo ✅ Separated Menu API is responding
) else (
    echo ❌ Separated Menu API not responding
)

echo.

REM Test health endpoint
echo 📋 Testing: GET /actuator/health
curl -s -o nul -w "%%{http_code}" http://localhost:8090/actuator/health
if %errorlevel% equ 0 (
    echo ✅ Health endpoint is responding
) else (
    echo ❌ Health endpoint not responding
)

echo.
echo 🌐 Testing if frontend is running on port 5173...
curl -s -o nul -w "%%{http_code}" http://localhost:5173
if %errorlevel% equ 0 (
    echo ✅ Frontend is responding
) else (
    echo ❌ Frontend not responding - Is npm run dev running?
)

echo.
echo 📊 API Test Complete!
echo.
echo If you see ✅ for all tests, your integration is working!
echo If you see ❌, make sure both applications are running:
echo   Backend: mvn spring-boot:run (in d:\SpringBoot\qr_menu)
echo   Frontend: npm run dev (in c:\Users\Nimith\Desktop\Qr-Menu-ChhongCoffe)
echo.
pause
