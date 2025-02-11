CREATE TABLE IF NOT EXISTS `webdb`.`guestbook` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `contents` TEXT NOT NULL,
  `reg_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

desc guestbook;

CREATE TABLE IF NOT EXISTS `webdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `gender` ENUM('male', 'female') NOT NULL,
  `join_date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

desc user;

INSERT INTO user (`id`, `name`, `email`, `password`, `gender`, `join_date`) 
VALUES (NULL, '홍길동', 'hong@example.com', 'password123', 'male', DATE_FORMAT(NOW(), '%y-%m-%d'));

SELECT * FROM user;
SELECT * FROM guestbook;
Drop table user;