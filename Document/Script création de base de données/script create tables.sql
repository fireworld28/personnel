DROP DATABASE IF EXISTS `personnel`;
CREATE DATABASE personnel;
USE personnel;

DROP TABLE IF EXISTS `ligue`;
CREATE TABLE ligue
(
    id_ligue   INT          NOT NULL AUTO_INCREMENT,
    nom_ligue  VARCHAR(100) NOT NULL,
    id_employe INT          NULL,
    CONSTRAINT pk_ligue PRIMARY KEY (id_ligue)
);

DROP TABLE IF EXISTS `employe`;
CREATE TABLE employe
(
    id_employe           INT          NOT NULL AUTO_INCREMENT,
    nom_employe          VARCHAR(100) NOT NULL,
    prenom_employe       VARCHAR(100) NOT NULL,
    mail_employe         VARCHAR(150) NOT NULL,
    password_employe     VARCHAR(255) NOT NULL,
    date_arrivee_employe DATE         NOT NULL,
    date_depart_employe  DATE         NULL,
    id_ligue             INT          NOT NULL,
    CONSTRAINT pk_employe PRIMARY KEY (id_employe),
    CONSTRAINT fk_employe_ligue FOREIGN KEY (id_ligue)
        REFERENCES ligue (id_ligue) ON DELETE CASCADE,
    CONSTRAINT ck_employe_dates
        CHECK (date_depart_employe IS NULL OR date_depart_employe >= date_arrivee_employe)
);

ALTER TABLE ligue
    ADD CONSTRAINT fk_ligue_administrateur FOREIGN KEY (id_employe)
        REFERENCES employe (id_employe) ON DELETE SET NULL;
