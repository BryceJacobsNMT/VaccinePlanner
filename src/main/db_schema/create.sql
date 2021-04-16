DROP TABLE IF EXISTS DiseaseStatistic CASCADE;
DROP TABLE IF EXISTS IncreasedRiskDisease CASCADE;
DROP TABLE IF EXISTS OccupationDisease CASCADE;
DROP TABLE IF EXISTS SevereIllnessDisease CASCADE;
DROP TABLE IF EXISTS RacialDisease CASCADE;
DROP TABLE IF EXISTS AgeDisease CASCADE;
DROP TABLE IF EXISTS Disease;


DROP TABLE IF EXISTS OccupationPriority;
DROP TABLE IF EXISTS IncreasedRiskPriority;
DROP TABLE IF EXISTS SevereIllnessPriority;
DROP TABLE IF EXISTS RacialPriority;
DROP TABLE IF EXISTS AgePriority;
DROP TABLE IF EXISTS Prioritization;

DROP TABLE IF EXISTS AgeGroupPopulation CASCADE;
DROP TABLE IF EXISTS RacialMixPopulation CASCADE;
DROP TABLE IF EXISTS Population;

DROP TABLE IF EXISTS VaccineDeliveryAvailability CASCADE;
DROP TABLE IF EXISTS VaccineAvailabilityModelContinuous CASCADE;
DROP TABLE IF EXISTS VaccineAvailabilityModelDiscrete CASCADE;
DROP TABLE IF EXISTS VaccineDelivery CASCADE;


create table Population(
        id INTEGER NOT NULL,
	chronic_medical_condition_percentage REAL not null,
	increased_risk_percentage REAL not null,
	severe_illness_percentage REAL not null,
	PRIMARY KEY (id),
        CONSTRAINT chronic_check CHECK (0 <= chronic_medical_condition_percentage AND chronic_medical_condition_percentage <= 1),
        CONSTRAINT increased_check CHECK ( 0 <= increased_risk_percentage AND increased_risk_percentage <= 1),
        CONSTRAINT severe_check CHECK ( 0 <= severe_illness_percentage AND severe_illness_percentage <= 1)
);

create table RacialMixPopulation (
        population_id INTEGER NOT NULL,
        racial_category CHARACTER(20) NOT NULL,
        percent REAL not null,
        PRIMARY KEY( population_id, racial_category),
        FOREIGN KEY( population_id) REFERENCES Population( id) ON DELETE CASCADE,
        CONSTRAINT percent_check CHECK ( 0 <= percent AND percent <= 1 )
);

create table AgeGroupPopulation (
    population_id INTEGER NOT NULL,
    age_group CHARACTER(20) NOT NULL,
    percent REAL not null,
    PRIMARY KEY( population_id, age_group),
    FOREIGN KEY( population_id ) REFERENCES Population( id) ON DELETE CASCADE,
    CONSTRAINT percent_check CHECK ( 0 <= percent AND percent <= 1 )
);




create table Disease(
    id INTEGER NOT NULL,
    PRIMARY KEY (id)
);

create table DiseaseStatistic (
    id INTEGER NOT NULL,
    infection_rate REAL not null,
    hospitalization_rate REAL not null,
    death_rate REAL not null,
    spread_rate REAL not null,
    PRIMARY KEY (id)
);

create table IncreasedRiskDisease(
    disease_id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    increased_risk CHARACTER(30) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id),
    FOREIGN KEY (disease_id) REFERENCES Disease(id),
    PRIMARY KEY (disease_id, increased_risk)
);

create table OccupationDisease(
    disease_id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    occupation CHARACTER(40) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id),
    FOREIGN KEY (disease_id ) REFERENCES Disease(id),
    PRIMARY KEY (disease_id, occupation)
);

create table SevereIllnessDisease(
    disease_id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    severe_illness CHARACTER(40) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id),
    FOREIGN KEY (disease_id) REFERENCES Disease( id ),
    PRIMARY KEY (disease_id, severe_illness)
);

create table RacialDisease(
    disease_id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    racial_disease CHARACTER(20) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id),
    FOREIGN KEY (disease_id) REFERENCES Disease( id),
    PRIMARY KEY (disease_id, racial_disease)
);

create table AgeDisease(
    disease_id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    age_group CHARACTER(20) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id),
    FOREIGN KEY (disease_id) REFERENCES Disease( id),
    PRIMARY KEY (disease_id, age_group)
);



create table Prioritization(
    id INTEGER NOT NULL,
    PRIMARY KEY (id)
);

create table OccupationPriority (
    prioritization_id INTEGER NOT NULL,
    occupation CHARACTER(40) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    FOREIGN KEY (prioritization_id) REFERENCES Prioritization(id) on DELETE CASCADE,
    PRIMARY KEY(prioritization_id,occupation)
);

create table IncreasedRiskPriority (
    prioritization_id INTEGER NOT NULL,
    increased_risk CHARACTER(30) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    FOREIGN KEY (prioritization_id) REFERENCES Prioritization(id) on DELETE CASCADE,
    PRIMARY KEY(prioritization_id, increased_risk)
);

create table SevereIllnessPriority (
    prioritization_id INTEGER NOT NULL,
    severe_illness CHARACTER(40) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    FOREIGN KEY (prioritization_id) REFERENCES Prioritization(id) on DELETE CASCADE,
    PRIMARY KEY(prioritization_id, severe_illness)
);

create table RacialPriority (
    prioritization_id INTEGER NOT NULL,
    racial_category CHARACTER(20) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    FOREIGN KEY (prioritization_id) REFERENCES Prioritization( id ) on DELETE CASCADE,
    PRIMARY KEY(prioritization_id, racial_category)
);

create table AgePriority (
    prioritization_id INTEGER NOT NULL,
    age_group CHARACTER(20) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    FOREIGN KEY (prioritization_id) REFERENCES Prioritization( id) ON DELETE CASCADE,
    PRIMARY KEY(prioritization_id, age_group)
);



create table VaccineDelivery(
    id INTEGER NOT NULL,
    PRIMARY KEY (id)
);


create table VaccineAvailabilityModelContinuous(
    id INTEGER NOT NULL,
    initial_amount INTEGER NOT NULL,
    growth_factor REAL NOT NULL,
    model_type CHARACTER(20) NOT NULL,
    CONSTRAINT initial_check CHECK (0 <= initial_amount),
    CONSTRAINT growth_factor CHECK (0 <= growth_factor ),  
    PRIMARY KEY(id)
);

create table VaccineAvailabilityModelDiscrete(
    id INTEGER NOT NULL,
    elapsed_days INTEGER NOT NULL,
    doses INTEGER NOT NULL,
    vaccine CHARACTER(20) NOT NULL,
    CONSTRAINT elapsed_check CHECK ( 0 <= elapsed_days),
    CONSTRAINT doses CHECK ( 0 <= doses ),
    PRIMARY KEY (id)
);

create table VaccineDeliveryAvailability(
    vaccine_delivery_id INTEGER NOT NULL,
    vaccine_availability_id INTEGER NOT NULL,
    vaccine CHARACTER(20) NOT NULL,
    FOREIGN KEY(vaccine_delivery_id) REFERENCES VaccineDelivery(id) ON DELETE CASCADE,
    FOREIGN KEY(vaccine_availability_id) REFERENCES VaccineAvailabilityModelContinuous(id) ON DELETE CASCADE,
    PRIMARY KEY (vaccine_delivery_id, vaccine)
);

ALTER TABLE DiseaseStatistic OWNER TO vaccineplannerapp;
ALTER TABLE IncreasedRiskDisease OWNER TO vaccineplannerapp;
ALTER TABLE  OccupationDisease  OWNER TO vaccineplannerapp;
ALTER TABLE  SevereIllnessDisease  OWNER TO vaccineplannerapp;
ALTER TABLE  RacialDisease  OWNER TO vaccineplannerapp;
ALTER TABLE  AgeDisease  OWNER TO vaccineplannerapp;
ALTER TABLE  Disease  OWNER TO vaccineplannerapp;


ALTER TABLE  OccupationPriority  OWNER TO vaccineplannerapp;
ALTER TABLE  IncreasedRiskPriority  OWNER TO vaccineplannerapp;
ALTER TABLE  SevereIllnessPriority  OWNER TO vaccineplannerapp; 
ALTER TABLE  RacialPriority  OWNER TO vaccineplannerapp;
ALTER TABLE  AgePriority  OWNER TO vaccineplannerapp;
ALTER TABLE  Prioritization  OWNER TO vaccineplannerapp;

ALTER TABLE  AgeGroupPopulation  OWNER TO vaccineplannerapp;
ALTER TABLE  RacialMixPopulation  OWNER TO vaccineplannerapp;
ALTER TABLE  Population  OWNER TO vaccineplannerapp;

ALTER TABLE  VaccineDeliveryAvailability  OWNER TO vaccineplannerapp;
ALTER TABLE  VaccineAvailabilityModelContinuous  OWNER TO vaccineplannerapp;
ALTER TABLE  VaccineAvailabilityModelDiscrete  OWNER TO vaccineplannerapp;
ALTER TABLE  VaccineDelivery  OWNER TO vaccineplannerapp;