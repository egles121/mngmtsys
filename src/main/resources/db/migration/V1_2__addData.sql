INSERT INTO department(name) VALUES ('HR');
INSERT INTO department(name) VALUES ('OPERATIONS');
INSERT INTO department(name) VALUES ('FINANCE');
INSERT INTO department(name) VALUES ('SALES');

INSERT INTO employee(first_name, last_name, job_title, department_id, start_date, end_date)
VALUES ('Angela', 'Kinsey', 'Accountant', '3', '2015-04-03', null);

INSERT INTO employee(first_name, last_name, job_title, department_id, start_date, end_date)
VALUES ('Toby', 'Flenderson', 'HR Representative', '1', '2020-01-21', '2022-05-19');

INSERT INTO employee(first_name, last_name, job_title, department_id, start_date, end_date)
VALUES ('Taylor', 'Jackson', 'Logistics Manager', '2', '2022-11-05', null);

INSERT INTO employee(first_name, last_name, job_title, department_id, start_date, end_date)
VALUES ('Michael', 'Scott', 'Regional Sales Manager', '4', '2021-05-03', null);

INSERT INTO hibernate_sequence (next_val) VALUES (0);
