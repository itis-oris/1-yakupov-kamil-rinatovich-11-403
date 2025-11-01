create sequence account_id_seq
    START WITH 1000000000000000000
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 50;

create table accounts(
                         account_id BIGINT default nextval('account_id_seq'),
                         login      VARCHAR(50),
                         password   VARCHAR(100),
                         name       VARCHAR(50),
                         surname    VARCHAR(50),
                         role       VARCHAR(50),
--------------------------------------------------
                         CONSTRAINT account_id_pk PRIMARY KEY (account_id),
                         CONSTRAINT login_nn      CHECK       (login    is not null),
                         CONSTRAINT password_nn   CHECK       (password is not null),
                         CONSTRAINT name_nn       CHECK       (name     is not null),
                         CONSTRAINT surname_nn    CHECK       (surname  is not null),
                         CONSTRAINT role_nn       CHECK       (role     is not null),
                         CONSTRAINT login_uq      UNIQUE      (login)
);

create sequence slot_id_seq
    START WITH 1000000000000000000
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 50;

create table slots(
                      slot_id BIGINT default nextval('slot_id_seq'),
                      name    TEXT,
                      date    DATE,
                      time    TIME,
                      type    VARCHAR(50),
--------------------------------------------------
                      CONSTRAINT slot_id_pk PRIMARY KEY (slot_id),
                      CONSTRAINT date_nn    CHECK       (date   is not null),
                      CONSTRAINT time_nn    CHECK       (time   is not null),
                      CONSTRAINT type_nn    CHECK       (type   is not null)
);

create sequence account_slot_id_seq
    START WITH 1000000000000000000
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 50;

create table account_slot(
                             account_slot_id BIGINT default nextval('account_slot_id_seq'),
                             account_id      BIGINT,
                             slot_id         BIGINT,
--------------------------------------------------
                             CONSTRAINT account_slot_id_pk PRIMARY KEY (account_slot_id),
                             CONSTRAINT account_id_fk      FOREIGN KEY (account_id)       REFERENCES accounts(account_id),
                             CONSTRAINT slot_id_fk         FOREIGN KEY (slot_id)          REFERENCES slots(slot_id),
                             CONSTRAINT account_id_nn      CHECK       (account_id is not null),
                             CONSTRAINT slot_id_nn         CHECK       (slot_id    is not null),
                             CONSTRAINT account_slot_uq    UNIQUE      (account_id, slot_id)
);

create sequence records_id_seq
    START WITH 1000000000000000000
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 50;

create table records(
                        record_id   BIGINT default nextval('records_id_seq'),
                        slot_id     BIGINT,
                        account_id  BIGINT,
                        chats_count INT,
                        status      VARCHAR(50),
                        comment     TEXT,
--------------------------------------------------
                        CONSTRAINT record_id_pk     PRIMARY KEY (record_id),
                        CONSTRAINT slot_id_fk       FOREIGN KEY (slot_id)    REFERENCES slots(slot_id),
                        CONSTRAINT account_id_fk    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
                        CONSTRAINT slot_id_nn       CHECK       (slot_id    is not null),
                        CONSTRAINT account_id_nn    CHECK       (account_id is not null),
                        CONSTRAINT status_nn        CHECK       (status     is not null),
                        CONSTRAINT slot_account_uq  UNIQUE      (slot_id, account_id)
);

create sequence transaction_id_seq
    START WITH 1000000000000000000
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 50;

create table transactions(
                             transaction_id  BIGINT default nextval('transaction_id_seq'),
                             from_account_id BIGINT,
                             to_account_id   BIGINT,
                             slot_id         BIGINT,
                             date            TIMESTAMP,
                             comment         TEXT,
--------------------------------------------------
                             CONSTRAINT transaction_id_pk  PRIMARY KEY (transaction_id),
                             CONSTRAINT from_account_id_fk FOREIGN KEY (from_account_id)  REFERENCES accounts(account_id),
                             CONSTRAINT to_account_id_fk   FOREIGN KEY (to_account_id)    REFERENCES accounts(account_id),
                             CONSTRAINT slot_id_fk         FOREIGN KEY (slot_id)          REFERENCES slots(slot_id),
                             CONSTRAINT from_account_id_nn CHECK       (from_account_id is not null),
                             CONSTRAINT to_account_id_nn   CHECK       (to_account_id   is not null),
                             CONSTRAINT slot_id_nn         CHECK       (slot_id         is not null),
                             CONSTRAINT date_nn            CHECK       (date            is not null)
);

insert into accounts(login, password, name, surname, role) values ('kamilyakupov25@mail.ru', 'qwertyuiop10', 'Камиль', 'Якупов', 'Старший куратор');
insert into accounts(login, password, name, surname, role) values ('veldyaeva07@gmail.com', 'asdfghjkl01', 'Александра', 'Вельдяева', 'Младший куратор');

insert into slots(date, time, type) values ('2025-11-01', '12:00:00',  'Рабочий час');
insert into slots(date, time, type) values ('2025-11-01', '18:00:00', 'Рабочий час');

insert into account_slot(account_id, slot_id) values (1000000000000000000, 1000000000000000000);
insert into account_slot(account_id, slot_id) values (1000000000000000001, 1000000000000000000);
insert into account_slot(account_id, slot_id) values (1000000000000000001, 1000000000000000001);

insert into records(slot_id, account_id, chats_count, status) values (1000000000000000000, 1000000000000000001, 5, 'Окончена');

insert into transactions(from_account_id, to_account_id, slot_id, date, comment) values (1000000000000000000, 1000000000000000001, 1000000000000000000, '2025-10-31 16:00:00', 'Поменялся, так как не могу выйти в эту смену');
insert into transactions(from_account_id, to_account_id, slot_id, date, comment) values (1000000000000000001, 1000000000000000000, 1000000000000000000, '2025-10-31 17:00:00', 'Отдала обратно, появились дела');