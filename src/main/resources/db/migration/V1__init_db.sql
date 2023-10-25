CREATE TABLE coffee
(
    id         UUID PRIMARY KEY,
    name       TEXT      NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    SMALLINT  NOT NULL DEFAULT 0
);

CREATE TABLE shop
(
    id                        UUID PRIMARY KEY,
    location                  TEXT      NOT NULL,
    contact                   TEXT      NOT NULL,
    menu                      TEXT      NOT NULL,
    order_queues              TEXT      NOT NULL,
    allowed_order_queue_count INT       NOT NULL,
    allowed_order_queue_size  INT       NOT NULL,
    opening_time              TIME      NOT NULL,
    closing_time              TIME      NOT NULL,
    updated_by                UUID,

    created_at                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version                   SMALLINT  NOT NULL DEFAULT 0
);

CREATE TABLE customer
(
    id            UUID PRIMARY KEY,
    mobile_number TEXT UNIQUE NOT NULL,
    password      TEXT        NOT NULL,
    name          TEXT        NOT NULL,
    home_address  TEXT,
    work_address  TEXT,
    score         INT         NOT NULL DEFAULT 0,

    created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version       SMALLINT    NOT NULL DEFAULT 0
);

CREATE TABLE staff
(
    id         UUID PRIMARY KEY,
    email      TEXT UNIQUE NOT NULL,
    password   TEXT        NOT NULL,
    name       TEXT        NOT NULL,
    type       TEXT        NOT NULL,
    shop_id    UUID        NOT NULL,

    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version    SMALLINT    NOT NULL DEFAULT 0
);

CREATE TABLE order_archive
(
    id           UUID PRIMARY KEY,
    order_record TEXT      NOT NULL,

    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version      SMALLINT  NOT NULL DEFAULT 0
);

INSERT INTO coffee(id, name)
VALUES ('ecc96dd0-6e73-4b9e-9219-8319876ed92a', 'Capuchino');
INSERT INTO coffee(id, name)
VALUES ('ed589dcd-dc46-4331-8b72-1e14197e711f', 'Latte');

INSERT INTO shop(id, location, contact, menu, order_queues, allowed_order_queue_count, allowed_order_queue_size,
                 opening_time, closing_time)
VALUES ('45ac7c10-a815-4bee-a256-ef6718a5370f', '{"lat":7.7,"lng":9.9}',
        '{"street":"street","houseNumber":"house number","postalCode":"postal code","city":"city","phoneNumber":"111"}',
        '[{"coffeeId":"ecc96dd0-6e73-4b9e-9219-8319876ed92a","price":{"amount":"1","currency":"SGD"}},{"coffeeId":"ed589dcd-dc46-4331-8b72-1e14197e711f","price":{"amount":"1E+1","currency":"SGD"}}]',
        '[{"id":1,"orders":[{"id":"c56408f1-60b9-473e-9ac1-72b429a4d0a7","customer":{"id":"2baafdaa-582b-486a-8a2c-122094eb9213","mobileNumber":"777","name":"customer name","score":12},"menuItem":{"coffeeId":"ecc96dd0-6e73-4b9e-9219-8319876ed92a","price":{"amount":"1","currency":"SGD"}},"amount":5,"status":"AWAITING_SERVE","createdAt":"2023-10-24T00:00:34.4944799","updatedAt":"2023-10-24T00:00:34.4944799"}]}]',
        3, 100, '07:00:00', '19:00:00');

INSERT INTO staff(id, email, password, name, type, shop_id)
VALUES ('1d07112c-34ae-4705-a44a-1073ffa41473', 'staff@shop.com',
        '$argon2id$v=19$m=16384,t=2,p=1$Z1zHaeDiok2Gtb4LF5g8ug$QAgw//BFwREwTgpOd0/cKjHE73ech8dAfwcn5uavzV0', 'Owner',
        'OWNER', '45ac7c10-a815-4bee-a256-ef6718a5370f');