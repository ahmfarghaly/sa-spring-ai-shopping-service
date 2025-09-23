-- Insert sample shops
INSERT INTO shops (id, name, description, categories, rating, logoUrl, created_at, updated_at) VALUES
(1, 'Acme Outfitters', 'Your one-stop shop for outdoor gear, apparel, and accessories.', 'OUTDOORS;APPAREL', 4.70, 'https://cdn.example.com/logos/acme.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Tech Galaxy', 'Premium consumer electronics and smart devices from top brands.', 'ELECTRONICS;GAMING', 4.55, 'https://cdn.example.com/logos/tech-galaxy.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Home & Hearth', 'Quality home and kitchen essentials to elevate everyday living.', 'HOME_GARDEN;KITCHEN', 4.30, 'https://cdn.example.com/logos/home-hearth.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample customers
INSERT INTO customers (id, name, email, age, gender, created_at, updated_at) VALUES
(1, 'John Doe', 'john.doe@email.com', 28, 'MALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Jane Smith', 'jane.smith@email.com', 32, 'FEMALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Mike Johnson', 'mike.johnson@email.com', 45, 'MALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Sarah Wilson', 'sarah.wilson@email.com', 29, 'FEMALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'David Brown', 'david.brown@email.com', 38, 'MALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Emily Davis', 'emily.davis@email.com', 26, 'FEMALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Robert Miller', 'robert.miller@email.com', 52, 'MALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Lisa Anderson', 'lisa.anderson@email.com', 34, 'FEMALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 'James Taylor', 'james.taylor@email.com', 41, 'MALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Maria Garcia', 'maria.garcia@email.com', 27, 'FEMALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample products
INSERT INTO products (id, title, description, category, price, stock_quantity, shop_id, created_at, updated_at) VALUES
-- Electronics
(1, 'iPhone 15 Pro', 'Latest Apple smartphone with advanced camera system and A17 Pro chip', 'ELECTRONICS', 999.99, 50, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Samsung Galaxy S24', 'Premium Android smartphone with AI-powered features', 'ELECTRONICS', 899.99, 45, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'MacBook Air M3', 'Ultra-thin laptop with Apple M3 chip and 13-inch display', 'ELECTRONICS', 1299.99, 30, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Dell XPS 13', 'Premium Windows laptop with Intel Core i7 processor', 'ELECTRONICS', 1199.99, 25, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'iPad Pro 12.9', 'Professional tablet with M2 chip and Liquid Retina display', 'ELECTRONICS', 1099.99, 35, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Sony WH-1000XM5', 'Premium noise-canceling wireless headphones', 'ELECTRONICS', 399.99, 60, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Apple Watch Series 9', 'Advanced smartwatch with health monitoring features', 'ELECTRONICS', 399.99, 40, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Nintendo Switch OLED', 'Portable gaming console with OLED display', 'ELECTRONICS', 349.99, 55, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Clothing
(9, 'Levi''s 501 Jeans', 'Classic straight-fit denim jeans', 'CLOTHING', 89.99, 100, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Nike Air Max 270', 'Comfortable running shoes with Air Max technology', 'CLOTHING', 149.99, 80, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'Adidas Ultraboost 22', 'High-performance running shoes with Boost midsole', 'CLOTHING', 179.99, 70, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'North Face Jacket', 'Waterproof outdoor jacket for all weather conditions', 'CLOTHING', 199.99, 45, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'Ralph Lauren Polo Shirt', 'Classic cotton polo shirt in various colors', 'CLOTHING', 79.99, 90, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'Zara Dress', 'Elegant evening dress for special occasions', 'CLOTHING', 129.99, 35, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'H&M Sweater', 'Cozy knit sweater perfect for winter', 'CLOTHING', 49.99, 120, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Home & Garden
(16, 'Dyson V15 Vacuum', 'Cordless vacuum cleaner with laser dust detection', 'HOME_GARDEN', 749.99, 20, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 'KitchenAid Stand Mixer', 'Professional-grade stand mixer for baking', 'HOME_GARDEN', 379.99, 25, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 'Instant Pot Duo', 'Multi-functional pressure cooker and slow cooker', 'HOME_GARDEN', 99.99, 50, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 'Philips Hue Smart Bulbs', 'Color-changing smart LED light bulbs (4-pack)', 'HOME_GARDEN', 199.99, 40, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'Weber Genesis Grill', 'Premium gas grill for outdoor cooking', 'HOME_GARDEN', 899.99, 15, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 'Roomba i7+', 'Self-emptying robot vacuum with smart mapping', 'HOME_GARDEN', 599.99, 30, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 'Nest Thermostat', 'Smart programmable thermostat with energy savings', 'HOME_GARDEN', 249.99, 35, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Books
(23, 'The Psychology of Money', 'Personal finance book by Morgan Housel', 'BOOKS', 16.99, 200, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 'Atomic Habits', 'Self-improvement book by James Clear', 'BOOKS', 18.99, 150, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 'Dune', 'Science fiction novel by Frank Herbert', 'BOOKS', 14.99, 100, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 'The Midnight Library', 'Fiction novel by Matt Haig', 'BOOKS', 15.99, 120, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 'Educated', 'Memoir by Tara Westover', 'BOOKS', 17.99, 80, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 'Sapiens', 'History book by Yuval Noah Harari', 'BOOKS', 19.99, 90, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Sports
(29, 'Yoga Mat Premium', 'Non-slip exercise mat for yoga and fitness', 'SPORTS', 39.99, 75, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'Dumbbells Set', 'Adjustable dumbbells for home workouts', 'SPORTS', 299.99, 20, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(31, 'Tennis Racket Pro', 'Professional tennis racket for competitive play', 'SPORTS', 199.99, 25, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 'Basketball Official', 'Official size basketball for indoor/outdoor play', 'SPORTS', 29.99, 60, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 'Fitness Tracker', 'Waterproof fitness tracker with heart rate monitor', 'SPORTS', 149.99, 45, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 'Protein Powder', 'Whey protein supplement for muscle building', 'SPORTS', 49.99, 100, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample orders
INSERT INTO orders (id, customer_id, order_date, status, total_amount, created_at, updated_at) VALUES
(1, 1, '2024-01-15', 'COMPLETED', 1149.98, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, '2024-01-16', 'COMPLETED', 229.98, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, '2024-01-17', 'SHIPPED', 899.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, '2024-01-18', 'PROCESSING', 179.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 5, '2024-01-19', 'COMPLETED', 749.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 1, '2024-01-20', 'PENDING', 399.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 6, '2024-01-21', 'COMPLETED', 89.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 7, '2024-01-22', 'SHIPPED', 1299.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9, 8, '2024-01-23', 'COMPLETED', 199.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 9, '2024-01-24', 'PROCESSING', 49.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample order items
INSERT INTO order_items (id, order_id, product_id, quantity, unit_price, created_at, updated_at) VALUES
-- Order 1 items
(1, 1, 1, 1, 999.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 10, 1, 149.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 2 items
(3, 2, 13, 2, 79.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 2, 23, 1, 16.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2, 24, 1, 18.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 2, 29, 1, 39.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 3 items
(7, 3, 2, 1, 899.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 4 items
(8, 4, 11, 1, 179.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 5 items
(9, 5, 16, 1, 749.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 6 items
(10, 6, 6, 1, 399.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 7 items
(11, 7, 9, 1, 89.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 8 items
(12, 8, 3, 1, 1299.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 9 items
(13, 9, 12, 1, 199.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Order 10 items
(14, 10, 15, 1, 49.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);