DROP DATABASE IF EXISTS firstgen;
CREATE DATABASE firstgen;
USE firstgen;

-- Create the Person table
CREATE TABLE IF NOT EXISTS person (
    samID INT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    date_of_birth DATE
);

-- Create the Mentor table
CREATE TABLE IF NOT EXISTS mentor (
    samID INT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),

    FOREIGN KEY (samID) REFERENCES person(samID) on DELETE CASCADE

);

-- Create the Student table
CREATE TABLE IF NOT EXISTS student (
    samID INT PRIMARY KEY,
    major VARCHAR(255),
    minor VARCHAR(255) DEFAULT NULL,
    gpa DECIMAL(3, 2),
    mtrId INT, 
    FOREIGN KEY (samID) REFERENCES person(samID) ON DELETE CASCADE,

    FOREIGN KEY (mtrID) REFERENCES mentor(samID)
);

-- Create the Notes table
CREATE TABLE IF NOT EXISTS notes (
    noteID INT PRIMARY KEY,
	samID INT NOT NULL,
    note TEXT,
    date DATE,
    mtrID INT NOT NULL,
    FOREIGN KEY (samID) REFERENCES student(samID),
    FOREIGN KEY (mtrID) REFERENCES mentor(samID)
);

-- Create the Event table
CREATE TABLE IF NOT EXISTS event (
    eventID INT PRIMARY KEY,
    eName VARCHAR(255),
    eDate DATE
);

-- Create junction table event_attendance for event table
CREATE TABLE IF NOT EXISTS event_attendance (		
	entryNum INT PRIMARY KEY, 						-- Orders table by entry sequence
    studentID INT,
	eventID INT,
    present VARCHAR(5),								-- Entry tracks if the given student attended the event.
   
    FOREIGN KEY (studentID) REFERENCES student(samID), 
    FOREIGN KEY (eventID) REFERENCES event(eventID)
);

-- Create the Application table
CREATE TABLE IF NOT EXISTS application (
    applicantID INT PRIMARY KEY,
    join_date DATE,
    FOREIGN KEY (applicantID) REFERENCES student(samID)
);

-- Create the Application_Info table
CREATE TABLE IF NOT EXISTS application_info (
	entryNum INT PRIMARY KEY,	-- Orders table by entry sequence
    applicantID INT,			
    app_key INT,				-- First Gen application number from a google form.
    question_num INT,			
    question_text VARCHAR(1000),
    answer_text VARCHAR(3000),
    FOREIGN KEY (applicantID) REFERENCES application(applicantID)
);


-- Dummy Data Queries
	-- Insert Person, Mentor, and Student
insert into person values(000376298, 'Dougathy Gilbert', 'dgilbert@shsu.edu', '2004-04-27');
insert into mentor values(000376298, 'dGilbert', 'BiggestPhamFan<3!');
insert into person values(1, 'James', 'James@shsu.edu', '1968-04-27');
insert into mentor values(1, 'mentor1', 'mentor1');
insert into person values(000289736, 'Gary Dinglesnorpee', 'gqd123@shsu.edu', '2015-06-10');
insert into student values(000289736, 'Theatre Arts', 'Criminal Justice', '2.01', '000376298');
insert into person values(000434461, 'Marlin Tower II', 'mxt092@firstgen.com', '2001-12-09');
insert into mentor values(000434461, 'mTower2', 'Seven7Seven$$');
insert into person values(000897239, 'Betsy Hepititus', 'student1@firstgen.com', '1969-06-09');
insert into student values(000897239, 'Criminal Justice', 'N/A', '1.52', '000434461');
insert into person values(000234768, 'Peter Griffin', 'plg523@shsu.edu', '1966-07-18');
insert into mentor values(000234768, 'pGriffin', 'ShutUpMeg*1738');
insert into person values(000987546, 'Conye Northsouth', 'con@firstgen.com', '1984-03-16');
insert into student values(000987546, 'Political Science', 'Rapping', '3.94', '000234768');
insert into person values(000123456, 'John', 'student1@shsu.edu', '2000-01-01');
insert into student values(000123456, 'Computer Science', 'N/A', '2.00', '1');

	-- Insert Notes
insert into notes values(1, 000987546, 'Wants to Change Major to Rapping', '2007-03-23', '000234768');
insert into notes values(2, 000123456, 'Needs help with Registering for Grad School', '2022-07-08', '1');
insert into notes values(3, 000897239, 'Leaving the University due to Personal Reasons', '1989-03-23', '000434461');
insert into notes values(4, 000289736, 'Wants help with Finding a Job Opportunity on Broadway', '2018-10-06', '000376298');

	-- Insert Event
insert into event values(1, 'Party Rokkers', '2000-01-01');
insert into event values(2, 'New Kids', '1998-10-02');
insert into event values(3, 'Poggers Invitational', '2000-01-01');
insert into event values(4, 'Crawfish Boil', '2015-11-07');

	-- Insert Event Attendance
insert into event_attendance values(1, 000123456, 1, 'No');
insert into event_attendance values(2, 000897239, 1, 'Yes');
insert into event_attendance values(3, 000987546, 1, 'N/A');
insert into event_attendance values(4, 000289736, 1, 'Yes');
insert into event_attendance values(5, 000123456, 2, 'N/A');
insert into event_attendance values(6, 000897239, 2, 'Yes');
insert into event_attendance values(7, 000987546, 2, 'No');
insert into event_attendance values(8, 000289736, 2, 'Yes');
insert into event_attendance values(9, 000123456, 3, 'Yes');
insert into event_attendance values(10, 000897239, 3, 'No');
insert into event_attendance values(11, 000987546, 3, 'Yes');
insert into event_attendance values(12, 000289736, 3, 'N/A');
insert into event_attendance values(13, 000123456, 4, 'No');
insert into event_attendance values(14, 000897239, 4, 'No');
insert into event_attendance values(15, 000987546, 4, 'N/A');
insert into event_attendance values(16, 000289736, 4, 'Yes');

	-- Insert Application
insert into application values(000123456, '2016-10-15');
insert into application values(000897239, '2014-04-20');
insert into application values(000987546, '2023-08-29');
insert into application values(000289736, '2021-09-06'); 

	-- Insert Application Info
insert into application_info values(1, 000123456, '34567890', 1, 'How do you like your steak?', 'Medium Rare');
insert into application_info values(2, 000123456, '23456789', 2, 'What is your favorite animal?', 'Dog');
insert into application_info values(3, 000897239, '56149218', 1, 'How do you like your steak?', 'Well Done');
insert into application_info values(4, 000897239, '83673509', 2, 'What is your favorite animal?', 'Cat');
insert into application_info values(5, 000987546, '85342313', 1, 'How do you like your steak?', 'Rare');
insert into application_info values(6, 000987546, '24209842', 2, 'What is your favorite animal?', 'Goose');
insert into application_info values(7, 000289736, '63792230', 1, 'How do you like your steak?', 'Medium Well');
insert into application_info values(8, 000289736, '39443927', 2, 'What is your favorite animal?', 'Ferret');

	-- Encrypt preloaded Mentors
update mentor set password = 'dzZpLdLX5wJ3ZQVlVNgEMIAgohibt2k444Gqt6PuUlQ='
where samID = 1;
update mentor set password = 'E0E3r4fKTxz6OK+6H4vp3SBChhtmyJsFdlg74quFJEk='
where samID = 234768;
update mentor set password = 'Tyb2M0cuvomtCQm97DiYCsfIO95zdqXFO0gdTU2Jaz0='
where samID = 376298;
update mentor set password = 'm+blPYhyqbp3SyqVRmjMViWujN6sSdNG6nTfs5HcEk4='
where samID = 434461;
