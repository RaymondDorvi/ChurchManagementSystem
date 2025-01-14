
Run the following SQL code in MySQL to create the Database


```
CREATE DATABASE ChurchDB;

USE ChurchDB;

-- Table for members
CREATE TABLE members (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(15),
    email VARCHAR(255),
    join_date DATE
);

-- Table for events
CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    location VARCHAR(255)
);

-- Table for donations
CREATE TABLE donations (
    donation_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    amount DECIMAL(10, 2),
    date DATE NOT NULL,
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);
```