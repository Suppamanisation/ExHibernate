Drop table if exists employee;
Drop table if exists department;
create table DEPARTMENT(
	DPT_ID int not null primary key, 
    NAME varchar(100) not null
);

create table EMPLOYEE(
	EMP_ID int not null primary key,
    NAME varchar(100) not null,
    DESIGNATION varchar(100),
    DPT_ID int,
    foreign key (DPT_ID) references DEPARTMENT(DPT_ID)
);

insert into DEPARTMENT(DPT_ID,NAME) values(1,'Software Development');
insert into DEPARTMENT(DPT_ID,NAME) values(2,'Human Resources');
insert into EMPLOYEE(EMP_ID,NAME,DESIGNATION,DPT_ID) values(1,'Mike','Software Developer',1);
insert into EMPLOYEE(EMP_ID,NAME,DESIGNATION,DPT_ID) values(2,'David','Team Lead',1);
insert into EMPLOYEE(EMP_ID,NAME,DESIGNATION,DPT_ID) values(3,'Peter','Manager',2);
insert into EMPLOYEE(EMP_ID,NAME,DESIGNATION,DPT_ID) values(4,'Andrew','VP',2);