🚀 QR MENU SYSTEM - LIVE INTEGRATION TEST
==========================================

📋 PREREQUISITES CHECKLIST:
- [ ] PostgreSQL running on port 5432
- [ ] Redis running on port 6379  
- [ ] Database 'qr_menu' exists
- [ ] Java 21+ installed
- [ ] Node.js installed

🔧 STEP 1: START SPRING BOOT BACKEND
====================================

In Terminal/PowerShell #1:
```powershell
cd d:\SpringBoot\qr_menu
mvn spring-boot:run
```

Expected Output:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::

...
Started QrMenuApplication in X.XXX seconds
```

✅ SUCCESS INDICATOR: See "Started QrMenuApplication" message

🧪 STEP 2: TEST BACKEND API
============================

Open browser and test these URLs:

1. Basic Menu API:
   URL: http://localhost:8090/api/menu
   Expected: {"success":true,"data":[],"message":"Menu items retrieved successfully"}

2. Separated Menu API:
   URL: http://localhost:8090/api/menu-separated  
   Expected: {"success":true,"data":{"drinks":[],"foods":[]},"message":"Separated menu retrieved successfully"}

3. Health Check:
   URL: http://localhost:8090/actuator/health
   Expected: {"status":"UP"}

✅ SUCCESS INDICATOR: All URLs return JSON (arrays may be empty)

🌐 STEP 3: START REACT FRONTEND
===============================

In Terminal/PowerShell #2:
```powershell
cd "c:\Users\Nimith\Desktop\Qr-Menu-ChhongCoffe"
npm run dev
```

Expected Output:
```
  VITE v6.2.2  ready in XXXms
  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
```

✅ SUCCESS INDICATOR: See "Local: http://localhost:5173/" message

🎯 STEP 4: TEST FRONTEND INTEGRATION
=====================================

1. Customer Menu View:
   URL: http://localhost:5173/qr-menu-chhong_caffe
   Expected: Menu interface with Drink/Food tabs
   May show: "No drinks/foods available" (normal if no data)

2. Admin Panel:
   URL: http://localhost:5173/qr-menu-chhong_caffe/admin
   Expected: Admin interface with "Add New Item" button

✅ SUCCESS INDICATOR: Both pages load, no console errors

📊 STEP 5: ADD SAMPLE DATA
===========================

Option A - Quick Test (Admin Panel):
1. Go to: http://localhost:5173/qr-menu-chhong_caffe/admin
2. Click "Add New Item"
3. Fill form:
   - Name: "Test Coffee"
   - Size: "M"  
   - Price: "3.50"
   - Category: "Drink"
   - Description: "Test item"
4. Click "Add Item"
5. Check customer view for new item

Option B - Full Sample Data (Database):
1. Open PostgreSQL client (pgAdmin, psql, etc.)
2. Connect to 'qr_menu' database
3. Run SQL from: d:\SpringBoot\qr_menu\sample_data.sql
4. Refresh frontend pages

✅ STEP 6: VERIFY INTEGRATION
==============================

Test Scenarios:
- [ ] Add item via admin → appears in customer view
- [ ] Edit item via admin → changes reflect immediately  
- [ ] Delete item via admin → removes from customer view
- [ ] Switch between Drink/Food tabs → filters correctly
- [ ] API endpoints return populated data
- [ ] No console errors in browser
- [ ] Backend logs show successful requests

🚨 TROUBLESHOOTING
==================

Backend Issues:
- "Connection refused" → Check PostgreSQL/Redis running
- "Port 8090 in use" → Kill existing processes: netstat -ano | findstr :8090
- "Database error" → Verify 'qr_menu' database exists

Frontend Issues:  
- "Failed to fetch" → Verify backend running on port 8090
- Blank page → Check browser console for errors
- "Network Error" → Check Windows Firewall

Integration Issues:
- Data not showing → Test API directly in browser first
- CORS errors → Backend configured for origins: "*"
- Type errors → All TypeScript should compile clean

📈 SUCCESS METRICS
===================

✅ Backend API responds with JSON
✅ Frontend loads without errors  
✅ Admin can add/edit/delete items
✅ Customer view shows real-time updates
✅ Category filtering works
✅ Database persists changes

🎉 INTEGRATION COMPLETE!
========================

When all checkboxes are marked, your QR Menu system is fully operational!
