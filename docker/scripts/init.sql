create table cards (
    card_number         varchar(16)     not null,
    password            varchar(50)     not null,
    balance             decimal(15,2)   not null,
    constraint cards_pk primary key(card_number)
);