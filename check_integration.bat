@echo off
title QR Menu Integration Test

echo.
echo 🚀 QR MENU SYSTEM - AUTOMATED INTEGRATION TEST
echo ===============================================
echo.

echo 📋 Checking Prerequisites...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java not found! Please install Java 21+
    pause
    exit /b 1
) else (
    echo ✅ Java is installed
)

REM Check if Maven is installed  
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven not found! Please install Maven
    pause
    exit /b 1
) else (
    echo ✅ Maven is installed
)

REM Check if Node.js is installed
node -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js not found! Please install Node.js
    pause
    exit /b 1
) else (
    echo ✅ Node.js is installed
)

echo.
echo 🔧 STEP 1: Testing Backend Compilation...
cd /d "d:\SpringBoot\qr_menu"
call mvn clean compile -q
if %errorlevel% neq 0 (
    echo ❌ Backend compilation failed!
    pause
    exit /b 1
) else (
    echo ✅ Backend compiles successfully
)

echo.
echo 🌐 STEP 2: Testing Frontend TypeScript...
cd /d "c:\Users\Nimith\Desktop\Qr-Menu-ChhongCoffe"
call npm run typecheck
if %errorlevel% neq 0 (
    echo ❌ Frontend TypeScript errors found!
    pause
    exit /b 1
) else (
    echo ✅ Frontend TypeScript is clean
)

echo.
echo 🎯 STEP 3: Ready to Start Applications!
echo.
echo Next steps:
echo 1. Open Terminal #1 and run: cd "d:\SpringBoot\qr_menu" ^&^& mvn spring-boot:run
echo 2. Open Terminal #2 and run: cd "c:\Users\Nimith\Desktop\Qr-Menu-ChhongCoffe" ^&^& npm run dev
echo 3. Test URLs:
echo    - Backend API: http://localhost:8090/api/menu
echo    - Customer View: http://localhost:5173/qr-menu-chhong_caffe
echo    - Admin Panel: http://localhost:5173/qr-menu-chhong_caffe/admin
echo.

echo ✅ All prerequisites checked! System is ready for integration testing.
echo.
pause
