DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL DEFAULT '',
  `wifi_lng` varchar(255) NOT NULL DEFAULT '',
  `wifi_lat` varchar(255) NOT NULL DEFAULT '',
  `gps_lng` varchar(255) NOT NULL DEFAULT '',
  `gps_lat` varchar(255) NOT NULL DEFAULT '',
  `lbs_lng` varchar(255) NOT NULL DEFAULT '',
  `lbs_lat` varchar(255) NOT NULL DEFAULT '',
  `created_time` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

DROP TABLE IF EXISTS `user`;
  CREATE TABLE `user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL DEFAULT '',
    `password` varchar(255) NOT NULL DEFAULT '',
    `lng` varchar(255) NOT NULL DEFAULT '',
    `lat` varchar(255) NOT NULL DEFAULT '',
    `gps` varchar(255) NOT NULL DEFAULT '',
    `wifi` varchar(255) NOT NULL DEFAULT '',
    `lbs` varchar(255) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;