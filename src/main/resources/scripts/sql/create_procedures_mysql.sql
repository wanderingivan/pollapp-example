-- Procedures

DROP PROCEDURE IF EXISTS addVote//
DROP PROCEDURE IF EXISTS createPoll//


CREATE PROCEDURE `addVote`(IN optionId BIGINT, IN pollId BIGINT)
BEGIN
	UPDATE polls SET votes = votes + 1 WHERE poll_id = pollId;
	UPDATE options SET votes = votes + 1 WHERE option_id = optionId;
END//

CREATE PROCEDURE `createPoll`(IN pollName VARCHAR(255),IN optionNames VARCHAR(1000),IN description VARCHAR(500),IN ownerUsername VARCHAR(50), OUT pollId BIGINT)
BEGIN
	DECLARE remainder TEXT;
	DECLARE delimiter CHAR(1);
	DECLARE pos INT DEFAULT 1 ;
	DECLARE optionName VARCHAR(1000);

	SET delimiter = ',';
	SET remainder = optionNames;

	INSERT INTO polls(poll_name,description,owner_id,created) VALUES(pollName,description,(SELECT user_id FROM users WHERE username=ownerUsername),CURRENT_TIMESTAMP());
	SET pollId = LAST_INSERT_ID();

	WHILE CHAR_LENGTH(remainder) > 0 AND Pos > 0 DO
	  SET pos = INSTR(remainder, `delimiter`);
	  IF pos = 0 THEN
	    SET optionName = remainder;
	  ELSE
	    SET optionName = LEFT(Remainder, Pos - 1);
      END IF;
      IF TRIM(optionName) != '' THEN
	    INSERT INTO options(option_name,poll_id,added) VALUES (optionName,pollId,CURRENT_TIMESTAMP());
 	  END IF;
      SET remainder = SUBSTRING(remainder, Pos + 1);
    END WHILE;
END//