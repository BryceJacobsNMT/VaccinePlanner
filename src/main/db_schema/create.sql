
DROP TABLE IF EXISTS RacialMix;
DROP TABLE IF EXISTS AgeGroup;
DROP TABLE IF EXISTS IncreasedRiskDisease;
DROP TABLE IF EXISTS OccupationDisease;
DROP TABLE IF EXISTS SevereIllness;
DROP TABLE IF EXISTS RacialDisease;
DROP TABLE IF EXISTS Disease;
DROP TABLE IF EXISTS DiseaseStatistic;
DROP TABLE IF EXISTS Prioritization;
DROP TABLE IF EXISTS OccupationPriority;
DROP TABLE IF EXISTS IncreasedRiskPriority;
DROP TABLE IF EXISTS SevereIllnessPriority;
DROP TABLE IF EXISTS RacialPriority;
DROP TABLE IF EXISTS AgePriority;
DROP TABLE IF EXISTS Population;


create table Population(
        id INTEGER NOT NULL,
	chronic_medical_condition_percentage REAL not null,
	increased_risk_percentage REAL not null,
	severe_illness_percentage REAL not null,
	target_date date not null,
	PRIMARY KEY (id),
        CONSTRAINT chronic_check CHECK (0 <= chronic_medical_condition_percentage AND chronic_medical_condition_percentage <= 1),
        CONSTRAINT increased_check CHECK ( 0 <= increased_risk_percentage AND increased_risk_percentage <= 1),
        CONSTRAINT severe_check CHECK ( 0 <= severe_illness_percentage AND severe_illness_percentage <= 1)
);

create table RacialMix (
        population_id INTEGER NOT NULL,
        racial_category CHARACTER(20) NOT NULL,
        percent REAL not null,
        PRIMARY KEY( population_id, racial_category),
        FOREIGN KEY( population_id) REFERENCES Population( id) ON DELETE CASCADE,
        CONSTRAINT percent_check CHECK ( 0 <= percent AND percent <= 1 )
);

create table AgeGroup (
    population_id INTEGER NOT NULL,
    age_group CHARACTER(20) NOT NULL,
    percent REAL not null,
    PRIMARY KEY( population_id, age_group),
    FOREIGN KEY( population_id ) REFERENCES Population( id) ON DELETE CASCADE,
    CONSTRAINT percent_check CHECK ( 0 <= percent AND percent <= 1 )
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
    id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    increased_risk CHARACTER(20) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

create table OccupationDisease(
    id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    occupation CHARACTER(20) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

create table SevereIllness(
    id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    severe_illness CHARACTER(20) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

create table RacialDisease(
    id INTEGER NOT NULL,
    disease_statistic_id INTEGER NOT NULL,
    racial_disease CHARACTER(20) NOT NULL,
    FOREIGN KEY ( disease_statistic_id ) REFERENCES DiseaseStatistic( id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

create table Disease(
    id INTEGER NOT NULL,
    occupation_disease_id INTEGER NOT NULL,
    increased_risk_id INTEGER NOT NULL,
    severe_illness_id INTEGER NOT NULL,
    racial_disease_id INTEGER NOT NULL,
    FOREIGN KEY(occupation_disease_id) REFERENCES OccupationDisease( id) ON DELETE CASCADE,
    FOREIGN KEY(increased_risk_id) REFERENCES IncreasedRiskDisease( id ) ON DELETE CASCADE,
    FOREIGN KEY( severe_illness_id) REFERENCES SevereIllness(id) ON DELETE CASCADE,
    FOREIGN KEY( racial_disease_id) REFERENCES RacialDisease(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);




create table OccupationPriority (
    id INTEGER NOT NULL,
    occupation CHARACTER(20) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    PRIMARY KEY(id)
);

create table IncreasedRiskPriority (
    id INTEGER NOT NULL,
    increased_risk CHARACTER(20) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    PRIMARY KEY(id)
);

create table SevereIllnessPriority (
    id INTEGER NOT NULL,
    severe_illness CHARACTER(20) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    PRIMARY KEY(id)
);

create table RacialPriority (
    id INTEGER NOT NULL,
    racial_category CHARACTER(20) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    PRIMARY KEY(id)
);

create table AgePriority (
    id INTEGER NOT NULL,
    age_group CHARACTER(20) NOT NULL,
    priority_group INTEGER NOT NULL,
    CONSTRAINT priority_check CHECK (1 <= priority_group AND priority_group <= 10 ),
    PRIMARY KEY(id)
);

create table Prioritization(
    id INTEGER NOT NULL,
    occupation_priority_id INTEGER NOT NULL,
    increased_risk_priority_id INTEGER NOT NULL,
    severe_illness_priority_id INTEGER NOT NULL,
    racial_priority_id INTEGER NOT NULL,
    age_priority_id INTEGER NOT NULL,
    FOREIGN KEY(occupation_priority_id) REFERENCES OccupationPriority( id) ON DELETE CASCADE,
    FOREIGN KEY(increased_risk_priority_id) REFERENCES IncreasedRiskPriority( id ) ON DELETE CASCADE,
    FOREIGN KEY( severe_illness_priority_id) REFERENCES SevereIllnessPriority(id) ON DELETE CASCADE,
    FOREIGN KEY( racial_priority_id) REFERENCES RacialPriority(id) ON DELETE CASCADE,
    FOREIGN KEY( age_priority_id) REFERENCES AgePriority(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
