
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS options;
DROP TABLE IF EXISTS polls;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS acl_entry;
DROP TABLE IF EXISTS acl_object_identity;
DROP TABLE IF EXISTS acl_class;
DROP TABLE IF EXISTS acl_sid;

CREATE TABLE `users` (
  `user_id`     BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
  `username`    VARCHAR(50) NOT NULL,
  `email`       VARCHAR(50) NOT NULL,
  `password`    VARCHAR(450) NOT NULL,
  `description` VARCHAR(250) DEFAULT NULL,
  `imagePath`   VARCHAR(250) DEFAULT NULL,
  `joined`      DATETIME NOT NULL DEFAULT 0,
  `enabled`     TINYINT(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE `polls` (
  `poll_id`     BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
  `poll_name`   VARCHAR(100) NOT NULL,
  `description` VARCHAR(500),
  `votes`       BIGINT(20) NOT NULL DEFAULT '0',
  `owner_id`    BIGINT(20) unsigned NOT NULL,
  `created`     DATETIME NOT NULL DEFAULT 0,
  PRIMARY KEY (`poll_id`),
  CONSTRAINT `owner_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `options` (
  `option_id`   BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
  `option_name` VARCHAR(100) NOT NULL,
  `votes`       BIGINT(20) NOT NULL DEFAULT '0',
  `poll_id`     BIGINT(20) unsigned NOT NULL,
  `added`       DATETIME NOT NULL DEFAULT 0,
  PRIMARY KEY (`option_id`),
  CONSTRAINT `poll_id_fk` FOREIGN KEY (`poll_id`) REFERENCES `polls` (`poll_id`) ON DELETE CASCADE
);

CREATE TABLE `votes` (
  `vote_id`     BIGINT(20) unsigned NOT NULL AUTO_INCREMENT,
  `option_id`   BIGINT(20) unsigned NOT NULL,
  `voter_id`    BIGINT(20) unsigned NOT NULL,
  PRIMARY KEY (`vote_id`),
  CONSTRAINT `option_id_fk` FOREIGN KEY (`option_id`) REFERENCES `options` (`option_id`) ON DELETE CASCADE,
  CONSTRAINT `voter_id_fk` FOREIGN KEY (`voter_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE `authorities` (
  `username` VARCHAR(50) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  CONSTRAINT `username_fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
);

-- Acl Required Tables

CREATE TABLE `acl_sid` (
  `id`        BIGINT(20) NOT NULL AUTO_INCREMENT,
  `principal` TINYINT(1) NOT NULL,
  `sid`       VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uk_1` (`sid`,`principal`)
);

CREATE TABLE `acl_class` (
  `id`    BIGINT(20) NOT NULL AUTO_INCREMENT,
  `class` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uk_2` (`class`)
);

CREATE TABLE `acl_object_identity` (
  `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT,
  `object_id_class`    BIGINT(20) NOT NULL,
  `object_id_identity` BIGINT(20) NOT NULL,
  `parent_object`      BIGINT(20) DEFAULT NULL,
  `owner_sid`          BIGINT(20) DEFAULT NULL,
  `entries_inheriting` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uk_3` (`object_id_class`,`object_id_identity`),
  CONSTRAINT `foreign_fk_1` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identity` (`id`),
  CONSTRAINT `foreign_fk_2` FOREIGN KEY (`object_id_class`) REFERENCES `acl_class` (`id`),
  CONSTRAINT `foreign_fk_3` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sid` (`id`)
);

CREATE TABLE `acl_entry` (
  `id`                  BIGINT(20) NOT NULL AUTO_INCREMENT,
  `acl_object_identity` BIGINT(20) NOT NULL,
  `ace_order`           INT(11) NOT NULL,
  `sid`                 BIGINT(20) NOT NULL,
  `mask`                INT(11) NOT NULL,
  `granting`            TINYINT(1) NOT NULL,
  `audit_success`       TINYINT(1) NOT NULL,
  `audit_failure`       TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uk_4` (`acl_object_identity`,`ace_order`),
  CONSTRAINT `foreign_fk_4` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identity` (`id`),
  CONSTRAINT `foreign_fk_5` FOREIGN KEY (`sid`) REFERENCES `acl_sid` (`id`)
);