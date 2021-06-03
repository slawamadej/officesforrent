--CREATE DATABASE officesForRent
--CHARACTER SET utf8mb4
--COLLATE utf8mb4_unicode_ci;

--czy mam tworzyc nowa baze czy na istniejacej robic?????

--CREATE SCHEMA officeForRent;

--$2a$10$eIfKiHwinw1D67kLyuxbX.NAzyz0UHaI/hBlPI2z/GN6tZHO6Zscy

CREATE TABLE officeForRent.users(
     userId INT UNSIGNED AUTO_INCREMENT
    ,username VARCHAR(100) UNIQUE NOT NULL
    ,password VARCHAR(100) NOT NULL
    ,role VARCHAR(50)
    ,email VARCHAR(100) UNIQUE NOT NULL
    ,token VARCHAR(50)
    ,isEnabled VARCHAR(1)
    ,isExpired VARCHAR(1)
    ,isLocked VARCHAR(1)
    ,createdDate DATETIME
    ,lastUpdatedDate DATETIME
    ,PRIMARY KEY(userId)
);

CREATE TABLE officeForRent.addresses(
     addressId INT UNSIGNED AUTO_INCREMENT
    ,town VARCHAR(100)
    ,district VARCHAR(100)
    ,street VARCHAR(100)
    ,postcode VARCHAR(50)
    ,flatNo VARCHAR(10)
    ,latitude FLOAT(10,7)
    ,longitude FLOAT(11,7)
    ,PRIMARY KEY(addressId)
);

CREATE TABLE officeForRent.entities(
     entityId INT UNSIGNED AUTO_INCREMENT
    ,userId INT UNSIGNED NOT NULL
    ,consentAgree VARCHAR(1)
    ,type VARCHAR(1) NOT NULL
    ,firstName VARCHAR(200)
    ,name VARCHAR(400)
    ,taxNumber VARCHAR(100)
    ,addressId INT UNSIGNED
    ,PRIMARY KEY(entityId)
    ,FOREIGN KEY(userId) REFERENCES users(userId)
    ,FOREIGN KEY(addressId) REFERENCES addresses(addressId)
                                           ON DELETE CASCADE
);

CREATE TABLE officeForRent.offices(
     officeId INT UNSIGNED AUTO_INCREMENT
    ,entityId INT UNSIGNED NOT NULL
    ,name VARCHAR(400)
    ,capacity INT
    ,area FLOAT(5,2)
    ,PRIMARY KEY(officeId)
    ,FOREIGN KEY(entityId) REFERENCES entities(entityId)
);

CREATE TABLE officeForRent.translations(
     code VARCHAR(20)
    ,lang VARCHAR(10)
    ,description VARCHAR(200)
    ,PRIMARY KEY(code, lang)
);

CREATE TABLE officeForRent.details(
     detailId INT UNSIGNED AUTO_INCREMENT
    ,detailType VARCHAR(20)
    ,detail VARCHAR(20)
    ,PRIMARY KEY(detailId)
    ,FOREIGN KEY(detailType) REFERENCES translations(code)
    ,FOREIGN KEY(detail) REFERENCES translations(code)
);

CREATE TABLE officeForRent.officeDetails(
     officeId INT UNSIGNED
    ,detailId INT UNSIGNED
    ,PRIMARY KEY(officeId, detailId)
    ,FOREIGN KEY(officeId) REFERENCES offices(officeId)
    ,FOREIGN KEY(detailId) REFERENCES details(detailId)
);

CREATE TABLE officeSchedules(
     scheduleId INT UNSIGNED AUTO_INCREMENT
    ,officeId INT UNSIGNED NOT NULL
    ,userId INT UNSIGNED
    ,scheduleType VARCHAR(20) NOT NULL
    ,fromDate DATE
    ,toDate DATE
    ,fromTime TIME
    ,toTime TIME
    ,day VARCHAR(20)
    ,PRIMARY KEY(scheduleId)
    ,FOREIGN KEY(officeId) REFERENCES offices(officeId)
    ,FOREIGN KEY(userId) REFERENCES users(userId)
    ,FOREIGN KEY(scheduleType) REFERENCES translations(code)
    ,FOREIGN KEY(day) REFERENCES translations(code)
);

