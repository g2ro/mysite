DROP TABLE IF EXISTS `WEBDB`.`user` ;
CREATE TABLE IF NOT EXISTS `webdb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `gender` ENUM('male', 'female') NOT NULL,
  `join_date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
select * from user;
DROP TABLE IF EXISTS `webdb`.`board` ;


CREATE TABLE IF NOT EXISTS `webdb`.`board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL,
  `contents` TEXT NOT NULL,
  `hit` INT NOT NULL,
  `reg_date` DATETIME NOT NULL,
  `g_no` INT NOT NULL,
  `o_no` INT NOT NULL,
  `depth` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_board_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `webdb`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

desc board;

-- insert -- 
INSERT INTO board values(null, title, contents, 0, reg_date, g_no, o_no, depth, user_id); 

SELECT * from board
ORDER BY g_no desc, o_no asc
LIMIT ? OFFSET ?;

SELECT title, name, hit, reg_date, g_no, o_no, depth
FROM board b
	JOIN user u ON(b.user_id = u.id)
ORDER BY g_no desc, o_no asc
LIMIT ? OFFSET ?;

SELECT title, name, hit, reg_date, g_no, o_no, depth
FROM board b
JOIN user u ON (b.user_id = u.id)
ORDER BY g_no DESC, o_no ASC
LIMIT 5 OFFSET 0;

SELECT IFNULL(MAX(g_no), 0) AS max_g_no
FROM board;

-- length --
SELECT count(*) from board;

-- findbyid --
SELECT id, title, contents, hit, reg_date, g_no, o_no, depth, user_id
FROM board
WHERE id = 1;

-- update --
select * from board;

UPDATE board
SET hit = hit + 1
WHERE id = 1;

-- delete --
DELETE FROM board
WHERE id = 3;

-- update by reply --
select * from board;
UPDATE board
SET o_no = o_no + 1
WHERE g_no = 1
	AND o_no >= o_no + 1;
    

SELECT g_no, o_no, depth
FROM board
WHERE id = 11;

select * from board;
select * from user;
desc board;

