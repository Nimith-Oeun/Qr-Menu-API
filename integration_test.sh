#!/bin/bash

echo "🚀 Starting QR Menu System Integration Test"
echo "========================================="

echo ""
echo "📋 Prerequisites Check:"
echo "✅ PostgreSQL should be running on port 5432"
echo "✅ Redis should be running on port 6379"
echo "✅ Database 'qr_menu' should exist"

echo ""
echo "🔧 Starting Spring Boot Backend..."
echo "Command: java -jar target/qr_menu-0.0.1-SNAPSHOT.jar"
echo "Expected: Application starts on http://localhost:8090"

echo ""
echo "🎯 Test Backend API:"
echo "1. Open browser: http://localhost:8090/api/menu"
echo "2. Should see: JSON response with empty array or sample data"

echo ""
echo "🌐 Starting React Frontend (in new terminal):"
echo "Command: cd \"c:\\Users\\Nimith\\Desktop\\Qr-Menu-ChhongCoffe\" && npm run dev"
echo "Expected: Vite dev server starts on http://localhost:5173"

echo ""
echo "🧪 Test Frontend Integration:"
echo "1. Customer View: http://localhost:5173/qr-menu-chhong_caffe"
echo "2. Admin Panel: http://localhost:5173/qr-menu-chhong_caffe/admin"

echo ""
echo "📊 Add Sample Data:"
echo "1. Connect to PostgreSQL"
echo "2. Run: \\i d:\\SpringBoot\\qr_menu\\sample_data.sql"
echo "3. Refresh frontend to see sample menu items"

echo ""
echo "✅ Integration Test Complete when:"
echo "- Backend API returns data"
echo "- Frontend displays menu items"
echo "- Admin panel can add/edit/delete items"
echo "- Changes reflect in real-time"
