CREATE TABLE books (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `title` varchar(100) NOT NULL,
    `author` varchar(100) NOT NULL,
    `publication_year` INTEGER DEFAULT NULL,
    `status` enum('BORROWED', 'AVAILABLE') DEFAULT 'AVAILABLE',
    `isbn` varchar(20) UNIQUE,
    PRIMARY KEY(`id`)
);

CREATE TABLE patrons (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `phone` varchar(20) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE borrowing_records (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `patron_id` bigint(20) NOT NULL,
    `book_id` bigint(20) NOT NULL,
    `borrow_date` bigint(20) NOT NULL,
    `return_date` bigint(20) DEFAULT NULL,
    PRIMARY KEY(`id`),
    KEY `borrowing_records_book_id_FK_idx` (`book_id`),
    KEY `borrowing_records_patron_id_FK_idx` (`patron_id`),
    CONSTRAINT `borrowing_records_book_id_FK` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `borrowing_records_patron_id_FK` FOREIGN KEY (`patron_id`) REFERENCES `patrons` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

