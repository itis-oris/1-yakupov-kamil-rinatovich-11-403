create sequence account_id_seq
    START WITH 1000000000000000000
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 50;

create sequence transaction_id_seq
    START WITH 1000000000000000000
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 50;

create sequence slot_id_seq
    START WITH 1000000000000000000
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 50;

create sequence account_slot_id_seq
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
                         CONSTRAINT name_nn       CHECK       (name is not null),
                         CONSTRAINT surname_nn    CHECK       (surname  is not null),
                         CONSTRAINT role_nn       CHECK       (role     is not null),
                         CONSTRAINT login_uq      UNIQUE      (login)
);

create table slots(
                      slot_id  BIGINT default nextval('slot_id_seq'),
                      name     TEXT,
                      date     DATE,
                      time     TIME,
                      type     VARCHAR(50),
--------------------------------------------------
                      CONSTRAINT slot_id_pk PRIMARY KEY (slot_id),
                      CONSTRAINT date_nn    CHECK       (date   is not null),
                      CONSTRAINT time_nn    CHECK       (time   is not null),
                      CONSTRAINT type_nn    CHECK       (type   is not null),
                      CONSTRAINT date_time_type_uq UNIQUE (date, time, type)
);

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

create table records(
                        account_slot_id BIGINT,
                        chats_count INT,
                        status      VARCHAR(50),
                        comment     TEXT,
--------------------------------------------------
                        CONSTRAINT record_id_pk PRIMARY KEY (account_slot_id),
                        CONSTRAINT status_nn          CHECK       (status is not null),
                        CONSTRAINT account_slot_id_fk FOREIGN KEY (account_slot_id) REFERENCES account_slot(account_slot_id)
);

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

insert into accounts(login, password, name, surname, role) values ('kamilyakupov25@mail.ru', '$2a$10$qYC8mj5kKvT2OmDRYAaSWuS0HLjJWE.kv/8lRgfCYWIDwZMEcVZNK', 'Камиль', 'Якупов', 'Старший куратор');
insert into accounts(login, password, name, surname, role) values ('veldyaeva07@gmail.com', '$2a$10$1pQe2D6Cr48MUlWrCpQ1yOfdJZ6Jowp7kluoaEDkkJiH8munSv66C', 'Александра', 'Вельдяева', 'Младший куратор');

insert into slots(date, time, type, exchange) values ('2025-11-01', '12:00:00',  'Рабочий час', false);
insert into slots(date, time, type, exchange) values ('2025-11-01', '18:00:00', 'Рабочий час', true);

insert into account_slot(account_id, slot_id) values (1000000000000000000, 1000000000000000000);
insert into account_slot(account_id, slot_id) values (1000000000000000001, 1000000000000000000);
insert into account_slot(account_id, slot_id) values (1000000000000000001, 1000000000000000001);

insert into records(slot_id, account_id, chats_count, status) values (1000000000000000000, 1000000000000000001, 5, 'Окончена');

insert into transactions(from_account_id, to_account_id, slot_id, date, comment) values (1000000000000000000, 1000000000000000001, 1000000000000000000, '2025-10-31 16:00:00', 'Поменялся, так как не могу выйти в эту смену');
insert into transactions(from_account_id, to_account_id, slot_id, date, comment) values (1000000000000000001, 1000000000000000000, 1000000000000000000, '2025-10-31 17:00:00', 'Отдала обратно, появились дела');

select s.slot_id, name, date, time, type
from slots s
         join account_slot as acs on s.slot_id = acs.slot_id
where account_id = 1000000000000000000 AND date >= current_date AND time >= current_time
order by date, time;

select r.record_id, r.slot_id, r.account_id, r.chats_count, r.status, r.comment, s.name, s.date, s.time, s.type
from slots s
         join records r on s.slot_id = r.slot_id
where account_id = 1000000000000000000
order by date, time;