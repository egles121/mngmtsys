use management_app;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS hibernate_sequence;

  CREATE TABLE department (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(45) NOT NULL,
    PRIMARY KEY (id)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE employee (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  job_title varchar(45) NOT NULL,
  department_id bigint(20) NOT NULL,
  start_date timestamp NOT NULL,
  end_date timestamp DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_employee_department FOREIGN KEY (department_id)
      REFERENCES department(id)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
