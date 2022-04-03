CREATE TABLE data
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    altitude         DOUBLE                NOT NULL,
    longitude        VARCHAR(100)          NOT NULL,
    latitude         VARCHAR(100)          NOT NULL,
    temperature      DOUBLE                NOT NULL,
    speed            DOUBLE                NOT NULL,
    CONSTRAINT pk_data PRIMARY KEY (id)
);