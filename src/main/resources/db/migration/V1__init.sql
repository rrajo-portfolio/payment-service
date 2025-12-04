CREATE TABLE payments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    currency VARCHAR(255) NOT NULL,
    status VARCHAR(255),
    transaction_id VARCHAR(255),
    created_at DATETIME(6),
    updated_at DATETIME(6),
    PRIMARY KEY (id),
    UNIQUE KEY UK_transaction_id (transaction_id)
) ENGINE=InnoDB;
