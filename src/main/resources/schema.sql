CREATE TABLE IF NOT EXISTS transactiontexts (
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  original_content TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  started_date DATETIME NOT NULL,
  completed_date DATETIME NOT NULL,
  credit_amount DECIMAL NOT NULL,
  debit_amount DECIMAL NOT NULL,
  description VARCHAR(1000) DEFAULT NULL,
  original_content_id LONG NOT NULL,
  FOREIGN KEY (original_content_id) REFERENCES transactiontexts(id)
);

CREATE TABLE IF NOT EXISTS companynames (
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  display_name VARCHAR(250) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS companyidentifiers (
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  identifier VARCHAR(250) NOT NULL UNIQUE,
  company_name LONG,
 FOREIGN KEY (company_name) REFERENCES companynames(id)
);

CREATE TABLE IF NOT EXISTS categories (
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  display_name VARCHAR(250) NOT NULL UNIQUE,
  parent_id LONG
);

CREATE TABLE IF NOT EXISTS userpreferences (
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  time_frame INT NOT NULL,
  input_folder TEXT NOT NULL,
  page_size INT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id LONG AUTO_INCREMENT  PRIMARY KEY,
  user_name VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  preferences_id LONG UNIQUE,
  FOREIGN KEY (preferences_id) REFERENCES userpreferences(id)
);
