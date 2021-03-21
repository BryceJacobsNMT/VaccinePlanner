# VaccinePlanner

The VaccinePlanner uses a postgresql database.  To run this application you will need to ensure that postgres is installed on your system.

The VaccinePlanner database will need to be set up using the following steps:
1.  Log into postgres with super user privileges: 'sudo -u postgres psql'
2.  Create the database:  'create database vaccine_planner_db;'
3.  Create the VaccinePlannerApp user:  'create user vaccineplannerapp with encrypted password 'COVID19';'
4.  Grant all database privileges to the vaccineplannerapp user: 'grant all privileges on database vaccine_planner_db to vaccineplannerapp;'

Once the database and VaccinePlannerApp user has been created, the tables need to be created.  There is a script called, 'create.sql' located in the src/main/db_schema.  From the plsql command line, run '\i absolute_path_to_create.sql' in order to create the database tables.

To start the postgresql database server, type 'sudo service postgresql start'.  The code assumes that postgresql has been configured to use the default port of 5432.