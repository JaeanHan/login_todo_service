# login_todo_service

## reference

images source : [https://ls.graphics/illustrations](https://ls.graphics/illustrations)

## Index View

![indexView](https://user-images.githubusercontent.com/65104605/173661821-53eb4596-fc4f-4c43-bb2e-48d7f443d99b.gif)

## logged-in View

![logged-in view 0](https://user-images.githubusercontent.com/65104605/174486021-d978082f-91f6-4a5d-b40c-9c33c70d6e93.gif)

![logged-in view 1](https://user-images.githubusercontent.com/65104605/174486031-27330bc8-cb0c-4026-b282-51bf51862e81.gif)

![logged-in view 2](https://user-images.githubusercontent.com/65104605/174486040-cc4c7e62-7438-475a-9436-69c136ef4040.gif)

![logged-in view 3](https://user-images.githubusercontent.com/65104605/174486652-1537d45a-6119-4704-b903-679e9adbcc3a.gif)

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
    ENGINE=InnoDB
