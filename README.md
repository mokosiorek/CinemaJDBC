# CinemaJDBC

PROJECT CINEMAJDBC

This project was created with IntelliJ IDEA IDE. And can be run via this IDE.

A simple console project with SQLite database with database repository based on JDBC.

The project idea is to simulate the ticket purchase at a cinema with data management based on SQL database. Project functionality include:
basic CRUD operations on customers and movies including sorting results of query. Simulation of purchase of ticket under assumption that we have unlimited projection rooms
and unlimited tickets available for every half an hour. Possibility of creating loyalty card for customer who bought more than 5 tickets. The ticket is being send on customer's email. 
Feature allowing customer to have his purchase history send over email with filtering of results.

For the email sending feature to work it is required to provide valid gmail account e-mail address and password in MailUtils singleton class. EMAIL2 and PASS variables.
The application uses two libraries to send emails. Simple Java Mail and JavaXMail. JavaXMail was used, because Simple Java Mail didn't work with provided e-mails.

To create the contents of emails send to customers, j2html library was used.
