INSERT INTO user (name, date_of_birth) VALUES ('John Wick', TO_DATE('12-09-1964', 'dd-MM-yyyy'));
INSERT INTO user (name, date_of_birth) VALUES ('Winston', TO_DATE('12-09-1972', 'dd-MM-yyyy'));
INSERT INTO user (name, date_of_birth) VALUES ('Viggo Tarasov', TO_DATE('30-01-1965', 'dd-MM-yyyy'));

INSERT INTO email (address, user_id) VALUES ('jwick@mail.com', 1);
INSERT INTO email (address, user_id) VALUES ('jwickpersonal@mail.com', 1);
INSERT INTO email (address, user_id) VALUES ('winston@mailcom', 2);