create table QUARTZ_JOB_DETAILS (
	JOB_NAME VARCHAR(80) not null,
	JOB_GROUP VARCHAR(80) not null,
	DESCRIPTION VARCHAR(120) null,
	JOB_CLASS_NAME VARCHAR(128) not null,
	IS_DURABLE BOOLEAN not null,
	IS_VOLATILE BOOLEAN not null,
	IS_STATEFUL BOOLEAN not null,
	REQUESTS_RECOVERY BOOLEAN not null,
	JOB_DATA SBLOB null,
	primary key (JOB_NAME, JOB_GROUP)
);

create table QUARTZ_TRIGGERS (
	TRIGGER_NAME VARCHAR(80) not null,
	TRIGGER_GROUP VARCHAR(80) not null,
	JOB_NAME VARCHAR(80) not null,
	JOB_GROUP VARCHAR(80) not null,
	IS_VOLATILE BOOLEAN not null,
	DESCRIPTION VARCHAR(120) null,
	NEXT_FIRE_TIME LONG null,
	PREV_FIRE_TIME LONG null,
	PRIORITY INTEGER null,
	TRIGGER_STATE VARCHAR(16) not null,
	TRIGGER_TYPE VARCHAR(8) not null,
	START_TIME LONG not null,
	END_TIME LONG null,
	CALENDAR_NAME VARCHAR(80) null,
	MISFIRE_INSTR INTEGER null,
	JOB_DATA SBLOB null,
	primary key (TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_BLOB_TRIGGERS (
	TRIGGER_NAME VARCHAR(80) not null,
	TRIGGER_GROUP VARCHAR(80) not null,
	BLOB_DATA SBLOB null,
	primary key (TRIGGER_NAME, TRIGGER_GROUP)
);

create table QUARTZ_CALENDARS (
	CALENDAR_NAME VARCHAR(80) not null primary key,
	CALENDAR SBLOB not null
);