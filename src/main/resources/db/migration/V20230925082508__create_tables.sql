CREATE TABLE users (
	id BIGINT NOT NULL auto_increment,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(120) NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
    PRIMARY KEY (id)
)default charset = utf8;

CREATE TABLE actives (
	id BIGINT NOT NULL auto_increment,
    name VARCHAR(70) NOT NULL,
    ticket VARCHAR(10) NOT NULL,
    category VARCHAR(12) NOT NULL,
    type VARCHAR(8) NOT NULL,
    currency VARCHAR(8) NOT NULL,
    quantity BIGINT NULL,
    pm DECIMAL(32,16) NULL,
    pvp DECIMAL(32,16) NULL,
    user_id BIGINT NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
	PRIMARY KEY (id)
)default charset = utf8;

CREATE TABLE reports (
	id BIGINT NOT NULL auto_increment,
    note VARCHAR(255) NOT NULL,
    date_note TIMESTAMP NOT NULL,
    actives_id BIGINT NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
	PRIMARY KEY (id)
)default charset = utf8;

CREATE TABLE launchs (
	id BIGINT NOT NULL auto_increment,
	actives_id BIGINT NOT NULL,
    date_launch TIMESTAMP NOT NULL,
    type VARCHAR(5) NOT NULL,
    quantity BIGINT NOT NULL,
    price DECIMAL(32,16) NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
	PRIMARY KEY (id)
)default charset = utf8;

CREATE TABLE wallets (
	id BIGINT NOT NULL auto_increment,
    category VARCHAR(70) NOT NULL,
    quantity BIGINT NULL,
    percent INT NULL,
    user_id BIGINT NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
	PRIMARY KEY (id)
)default charset = utf8;

ALTER TABLE users ENGINE=INNODB;
ALTER TABLE actives ENGINE=INNODB;
ALTER TABLE reports ENGINE=INNODB;
ALTER TABLE launchs ENGINE=INNODB;
ALTER TABLE wallets ENGINE=INNODB;

ALTER TABLE actives ADD CONSTRAINT fk_ac_user_id FOREIGN KEY(user_id) REFERENCES users(id);
ALTER TABLE reports ADD CONSTRAINT fk_rp_actives_id FOREIGN KEY(actives_id) REFERENCES actives(id);
ALTER TABLE launchs ADD CONSTRAINT fk_lc_actives_id FOREIGN KEY(actives_id) REFERENCES actives(id);
ALTER TABLE wallets ADD CONSTRAINT fk_wa_user_id FOREIGN KEY(user_id) REFERENCES users(id);

CREATE INDEX actives_ticket_idx ON actives (ticket);
CREATE INDEX actives_type_idx ON actives (type);
CREATE INDEX actives_category_idx ON actives (category);
CREATE INDEX actives_ccurrency_idx ON actives (currency);
CREATE INDEX reports_date_note_idx ON reports (date_note);
CREATE INDEX launchs_date_note_idx ON launchs (date_launch);
CREATE INDEX launchs_type ON launchs (type);
CREATE INDEX wallets_category_idx ON wallets (category);

