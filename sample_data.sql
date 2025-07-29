-- Sample data for QR Menu System
-- Run this after starting the Spring Boot application

-- Insert sample drink items
INSERT INTO menu_items (name, size, price, image, category, description, is_active, created_at, updated_at) VALUES
('Iced Coffee', 'M', '3.50', 'https://images.unsplash.com/photo-1461988320302-91bde64fc8e4?w=400', 'DRINK', 'Fresh brewed coffee served over ice', true, NOW(), NOW()),
('Hot Chocolate', 'L', '4.00', 'https://images.unsplash.com/photo-1542990253-0d0f5be5f0ed?w=400', 'DRINK', 'Rich and creamy hot chocolate', true, NOW(), NOW()),
('Green Tea Latte', 'M', '4.50', 'https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=400', 'DRINK', 'Smooth green tea with steamed milk', true, NOW(), NOW()),
('Fresh Orange Juice', 'L', '3.00', 'https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400', 'DRINK', 'Freshly squeezed orange juice', true, NOW(), NOW()),
('Cappuccino', 'M', '4.25', 'https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=400', 'DRINK', 'Espresso with steamed milk foam', true, NOW(), NOW()),
('Smoothie Bowl', 'L', '5.50', 'https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=400', 'DRINK', 'Mixed fruit smoothie in a bowl', true, NOW(), NOW()),
('Thai Iced Tea', 'M', '3.75', 'https://images.unsplash.com/photo-1571934811356-5cc061b6821f?w=400', 'DRINK', 'Traditional Thai tea with milk', true, NOW(), NOW());

-- Insert sample food items
INSERT INTO menu_items (name, size, price, image, category, description, is_active, created_at, updated_at) VALUES
('Grilled Chicken Sandwich', 'L', '8.50', 'https://images.unsplash.com/photo-1553979459-d2229ba7433a?w=400', 'FOOD', 'Juicy grilled chicken with fresh vegetables', true, NOW(), NOW()),
('Caesar Salad', 'M', '6.75', 'https://images.unsplash.com/photo-1546793665-c74683f339c1?w=400', 'FOOD', 'Fresh romaine lettuce with caesar dressing', true, NOW(), NOW()),
('Margherita Pizza', 'L', '12.00', 'https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=400', 'FOOD', 'Classic pizza with tomato, mozzarella, and basil', true, NOW(), NOW()),
('BBQ Pork Ribs', 'L', '15.50', 'https://images.unsplash.com/photo-1544025162-d76694265947?w=400', 'FOOD', 'Tender pork ribs with BBQ sauce', true, NOW(), NOW()),
('Beef Burger', 'M', '9.25', 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400', 'FOOD', 'Beef patty with cheese and fries', true, NOW(), NOW()),
('Fish and Chips', 'L', '11.00', 'https://images.unsplash.com/photo-1544982503-9f984c14501a?w=400', 'FOOD', 'Crispy battered fish with golden chips', true, NOW(), NOW()),
('Chicken Wings', 'M', '7.50', 'https://images.unsplash.com/photo-1527477396000-e27163b481c2?w=400', 'FOOD', 'Spicy buffalo chicken wings', true, NOW(), NOW()),
('Pasta Carbonara', 'M', '10.75', 'https://images.unsplash.com/photo-1621996346565-e3dbc353d2e5?w=400', 'FOOD', 'Creamy pasta with bacon and parmesan', true, NOW(), NOW());
