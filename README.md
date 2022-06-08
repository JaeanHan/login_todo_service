# login_todo_service

# DB

## user table

    CREATE TABLE `user` (
    `usercode` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
    `email` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
    `username` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
    `password` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
    `role` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
    `create_date` DATETIME NOT NULL,
    `update_date` DATETIME NOT NULL,
    PRIMARY KEY (`usercode`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE
    )
    COLLATE='utf8mb3_general_ci'
    ENGINE=InnoDB;
    )

## todos table

    CREATE TABLE `todos` (
    `todocode` INT(11) NOT NULL AUTO_INCREMENT,
    `usercode` INT(11) NOT NULL COMMENT 'foreign key',
    `toDo` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
    `state` VARCHAR(50) NOT NULL COLLATE 'utf8mb3_general_ci',
    `importance` INT(11) NOT NULL,
    `create_date` DATETIME NOT NULL,
    `update_date` DATETIME NOT NULL,
    PRIMARY KEY (`todocode`, `usercode`) USING BTREE,
    INDEX `todoCode` (`usercode`) USING BTREE,
    CONSTRAINT `todoCode` FOREIGN KEY (`usercode`) REFERENCES `login_practice`.`user` (`usercode`) ON UPDATE NO ACTION ON DELETE NO ACTION
    )
    COLLATE='utf8mb3_general_ci'
    ENGINE=InnoDB;
