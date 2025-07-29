@echo off
title Add Sample Data via API

echo 📊 ADDING SAMPLE DATA VIA REST API
echo ==================================
echo.

echo Testing backend connection first...
curl -s -f http://localhost:8090/api/menu >nul
if %errorlevel% neq 0 (
    echo ❌ Backend not responding! Make sure Spring Boot is running on port 8090
    echo Command: cd "d:\SpringBoot\qr_menu" ^&^& mvn spring-boot:run
    pause
    exit /b 1
)

echo ✅ Backend is running!
echo.

echo 🍹 Adding Sample Drinks...

REM Add Iced Coffee
curl -X POST http://localhost:8090/api/items ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Iced Coffee\",\"size\":\"M\",\"price\":\"3.50\",\"category\":\"DRINK\",\"description\":\"Fresh brewed coffee served over ice\",\"image\":\"https://images.unsplash.com/photo-1461988320302-91bde64fc8e4?w=400\"}"

echo Added: Iced Coffee

REM Add Hot Chocolate  
curl -X POST http://localhost:8090/api/items ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Hot Chocolate\",\"size\":\"L\",\"price\":\"4.00\",\"category\":\"DRINK\",\"description\":\"Rich and creamy hot chocolate\",\"image\":\"https://images.unsplash.com/photo-1542990253-0d0f5be5f0ed?w=400\"}"

echo Added: Hot Chocolate

REM Add Cappuccino
curl -X POST http://localhost:8090/api/items ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Cappuccino\",\"size\":\"M\",\"price\":\"4.25\",\"category\":\"DRINK\",\"description\":\"Espresso with steamed milk foam\",\"image\":\"https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=400\"}"

echo Added: Cappuccino

echo.
echo 🍔 Adding Sample Foods...

REM Add Grilled Chicken Sandwich
curl -X POST http://localhost:8090/api/items ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Grilled Chicken Sandwich\",\"size\":\"L\",\"price\":\"8.50\",\"category\":\"FOOD\",\"description\":\"Juicy grilled chicken with fresh vegetables\",\"image\":\"https://images.unsplash.com/photo-1553979459-d2229ba7433a?w=400\"}"

echo Added: Grilled Chicken Sandwich

REM Add Margherita Pizza
curl -X POST http://localhost:8090/api/items ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Margherita Pizza\",\"size\":\"L\",\"price\":\"12.00\",\"category\":\"FOOD\",\"description\":\"Classic pizza with tomato, mozzarella, and basil\",\"image\":\"https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=400\"}"

echo Added: Margherita Pizza

REM Add Beef Burger
curl -X POST http://localhost:8090/api/items ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Beef Burger\",\"size\":\"M\",\"price\":\"9.25\",\"category\":\"FOOD\",\"description\":\"Beef patty with cheese and fries\",\"image\":\"https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400\"}"

echo Added: Beef Burger

echo.
echo ✅ Sample data added successfully!
echo.
echo 🎯 Test your integration:
echo 1. Backend API: http://localhost:8090/api/menu
echo 2. Customer View: http://localhost:5173/qr-menu-chhong_caffe  
echo 3. Admin Panel: http://localhost:5173/qr-menu-chhong_caffe/admin
echo.
echo You should now see 3 drinks and 3 food items!
echo.
pause
