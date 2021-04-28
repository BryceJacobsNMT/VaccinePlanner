# VaccinePlanner

############################################################################
#  Current Implementation Designed to Read Files from the UI and Write
#  Files for use by the UI.
############################################################################

The VaccinePlanner is designed to take up to four input files specifying the
information to be used in a model.  The first piece of information that can
be specified concerns information about a population of people.  A sample file,
SamplePopulation.txt, in the docs directory of this application shows the format of
the population file.  The second file that can be specified contains information
about the disease like how infectious it is.  A sample file, SampleDisease.txt, in the docs directory of this application shows the format of the disease file.  A third piece of information that can be specified is information about how people in the population should be prioritized.  A sample file containing priority information is SamplePrioritization.txt in the docs directory.  The fourth piece of information that can be specified concerns the amount of vaccine available at any given amount of time.  A sample file containing this information can be found in SampleVaccineDelivery.txt.  In all cases, if any of the four pieces are not specified, the application will use default information contained in the sample files.

The application is designed to work as a command line interface that will take the names of input files and output summary statistics and graphs modeling the application spread under the input conditions to a directory for display by the web based user interface.  Alternatively, this application can be run as a GUI application and the results displayed as part of the built-in user interface.


############################################################################
#  Original Implementation Using a Postgres Database
############################################################################

The VaccinePlanner uses a postgresql database.  To run this application you will need to ensure that postgres is installed on your system.

The VaccinePlanner database will need to be set up using the following steps:
1.  Log into postgres with super user privileges: 'sudo -u postgres psql'
2.  Create the database:  'create database vaccine_planner_db;'
3.  Create the VaccinePlannerApp user:  'create user vaccineplannerapp with encrypted password 'COVID19';'
4.  Grant all database privileges to the vaccineplannerapp user: 'grant all privileges on database vaccine_planner_db to vaccineplannerapp;'

Once the database and VaccinePlannerApp user has been created, the tables need to be created.  There is a script called, 'create.sql' located in the src/main/db_schema.  From the plsql command line, run '\i absolute_path_to_create.sql' in order to create the database tables.

To start the postgresql database server, type 'sudo service postgresql start'.  The code assumes that postgresql has been configured to use the default port of 5432.