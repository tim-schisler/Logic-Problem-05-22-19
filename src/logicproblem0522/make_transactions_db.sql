CREATE DATABASE rewarddb;
USE rewarddb;

DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions (
	trans_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    customer_id INT UNSIGNED NOT NULL,
    total INT UNSIGNED NOT NULL,
    trans_date DATE NOT NULL,
    PRIMARY KEY(trans_id)
);

INSERT INTO transactions (customer_id, total, trans_date)
	VALUES
    (1, 49, '2019/03/18'),
    (1, 99, '2019/04/12'),
    (1, 101, '2019/04/28'),
    (1, 50, '2019/05/09'),
    (1, 120, '2019/05/21'),
    (2, 100, '2019/03/01'),
    (2, 200, '2019/04/02'),
    (2, 55, '2019/05/03'),
    (2, 120, '2019/05/20');
