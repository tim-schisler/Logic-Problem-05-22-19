DROP DATABASE IF EXISTS rewarddb;
CREATE DATABASE rewarddb;
USE rewarddb;

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
    (2, 100, '2019/03/03'),
    (2, 200, '2019/04/02'),
    (2, 55, '2019/05/03'),
    (2, 120, '2019/05/20');

-- Create user and grant privileges
DELIMITER //
CREATE PROCEDURE drop_user_if_exists()
BEGIN
DECLARE userCount BIGINT DEFAULT 0 ;
SELECT COUNT(*) INTO userCount FROM mysql.user
WHERE User = 'audience' and  Host = 'localhost';
IF userCount > 0 THEN
DROP USER audience@localhost;
END IF;
END ; //
DELIMITER ;


CALL drop_user_if_exists() ;
CREATE USER audience@localhost IDENTIFIED BY 'sesame';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP
ON rewarddb.*
TO audience@localhost;
USE rewarddb;

SELECT * FROM transactions WHERE customer_id = 2;