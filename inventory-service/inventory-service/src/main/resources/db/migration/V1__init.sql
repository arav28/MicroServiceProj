CREATE TABLE t_inventory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number VARCHAR(255) DEFAULT NULL,
    sku_code VARCHAR(255),
    quantity INT
);