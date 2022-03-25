CREATE TABLE data
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    time             TIMESTAMP             NOT NULL,
    system_time      TIMESTAMP             NOT NULL,
    fix              DOUBLE                NOT NULL,
    quality_location DOUBLE                NOT NULL,
    speed            DOUBLE                NOT NULL,
    altitude         DOUBLE                NOT NULL,
    satellites       INT                   NOT NULL,
    temperature      DOUBLE                NOT NULL,
    pressure         DOUBLE                NOT NULL,
    accelerationx    DOUBLE                NOT NULL,
    accelerationy    DOUBLE                NOT NULL,
    accelerationz    DOUBLE                NOT NULL,
    CONSTRAINT pk_data PRIMARY KEY (id)
);