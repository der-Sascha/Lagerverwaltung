-- ============================================================
-- Datenbankschema + Testdaten: Materialverwaltung Krankenhaus
-- Erstellt für: Sascha Schulz | Gruppe 2551
-- Korrigiertes 6-Tabellen-Schema (Mai 2026)
--
-- Architekturprinzip:
--   materialien = reine Stammdaten (KEIN Bestand, KEIN Lager, KEIN Ablaufdatum)
--   bestandsbewegungen = Buchungshistorie; aktueller Bestand = SUM(EINGANG) - SUM(AUSGANG)
--   bestellungen = Einzelpositions-Bestellung inkl. Ziel-Lager
-- ============================================================

DROP DATABASE IF EXISTS DOIT;
CREATE DATABASE DOIT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE DOIT;

-- ============================================================
-- TABELLEN-DEFINITIONEN
-- ============================================================

-- 1. kategorien
CREATE TABLE kategorien (
    kategorie_id  INT          AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    beschreibung  TEXT
);

-- 2. stationslager
CREATE TABLE stationslager (
    lager_id  INT          AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    standort  VARCHAR(100),
    typ       VARCHAR(50)
);

-- 3. lieferanten
CREATE TABLE lieferanten (
    lieferant_id INT          AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(150) NOT NULL,
    kontakt      VARCHAR(100),
    telefon      VARCHAR(50),
    email        VARCHAR(100)
);

-- 4. materialien  <- STAMMDATEN: kein Bestand, kein Lager, kein Ablaufdatum
CREATE TABLE materialien (
    material_id    INT          AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(150) NOT NULL,
    einheit        VARCHAR(30)  NOT NULL,
    mindestbestand INT          NOT NULL DEFAULT 0,
    kategorie_id   INT          NOT NULL,
    FOREIGN KEY (kategorie_id) REFERENCES kategorien(kategorie_id)
);

-- 5. bestellungen  <- Einzelpositions-Bestellung: ein Material, ein Lieferant, ein Ziel-Lager
CREATE TABLE bestellungen (
    bestellung_id INT         AUTO_INCREMENT PRIMARY KEY,
    material_id   INT         NOT NULL,
    lieferant_id  INT         NOT NULL,
    lager_id      INT         NOT NULL,
    menge         INT         NOT NULL,
    bestelldatum  DATE        NOT NULL,
    lieferdatum   DATE,
    status        VARCHAR(50) NOT NULL DEFAULT 'offen',
    FOREIGN KEY (material_id)  REFERENCES materialien(material_id),
    FOREIGN KEY (lieferant_id) REFERENCES lieferanten(lieferant_id),
    FOREIGN KEY (lager_id)     REFERENCES stationslager(lager_id)
);

-- 6. bestandsbewegungen  <- Buchungshistorie; Bestand = SUM(EINGANG) - SUM(AUSGANG) pro material+lager
CREATE TABLE bestandsbewegungen (
    bewegung_id   INT          AUTO_INCREMENT PRIMARY KEY,
    material_id   INT          NOT NULL,
    lager_id      INT          NOT NULL,
    bewegungstyp  ENUM('EINGANG','AUSGANG') NOT NULL,
    menge         INT          NOT NULL,
    ablaufdatum   DATE,                        -- nur bei EINGANG relevant (Chargenablaufdatum)
    datum         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    bemerkung     TEXT,
    FOREIGN KEY (material_id) REFERENCES materialien(material_id),
    FOREIGN KEY (lager_id)    REFERENCES stationslager(lager_id)
);

-- ============================================================
-- TESTDATEN
-- ============================================================

-- 1. KATEGORIEN (6 Einträge)
INSERT INTO kategorien (name, beschreibung) VALUES
('Verbandsmaterial',   'Wundverbaende, Pflaster, Bandagen und Kompressen'),
('Injektionsmaterial', 'Spritzen, Kanuelen, Infusionszubehoer'),
('Schutzausruestung',  'Handschuhe, Masken, Schutzkittel, Brillen'),
('Hygienematerial',    'Desinfektionsmittel, Reinigungstueecher, Seife'),
('Diagnostikmaterial', 'Teststreifen, Blutentnahmeroehrchen, Mundspatel'),
('Wundversorgung',     'Wundspuelungen, Wundauflagen, Nahtmaterial');


-- 2. STATIONSLAGER (5 Einträge)
INSERT INTO stationslager (name, standort, typ) VALUES
('Station 1 - Innere Medizin', 'Gebaeude A, EG',    'Station'),
('Station 2 - Chirurgie',      'Gebaeude A, 1. OG', 'Station'),
('OP-Saal',                    'Gebaeude B, EG',    'OP'),
('ICU - Intensivstation',      'Gebaeude C, EG',    'ICU'),
('Zentrallager',               'Gebaeude D, UG',    'Zentrallager');


-- 3. LIEFERANTEN (4 Einträge)
INSERT INTO lieferanten (name, kontakt, telefon, email) VALUES
('MedSupply GmbH',      'Klaus Bauer',   '0621-112233', 'bestellung@medsupply.de'),
('KlinikBedarf AG',     'Sandra Koch',   '0711-445566', 'info@klinikbedarf.de'),
('PharmaLogistik GmbH', 'Thomas Riedel', '089-778899',  'order@pharmalogistik.de'),
('Sanitaets Direkt e.K.', 'Maria Huber', '0221-334455', 'kontakt@sanitaetsdirekt.de');


-- 4. MATERIALIEN (20 Einträge) -- NUR Stammdaten, kein Bestand, kein Lager
INSERT INTO materialien (name, einheit, mindestbestand, kategorie_id) VALUES
-- Verbandsmaterial (kat 1)
('Mullbinde 6 cm',            'Rolle',   20,  1),   --  1
('Pflaster-Set gemischt',     'Packung', 15,  1),   --  2
('Kompressen 10x10 steril',   'Packung', 100, 1),   --  3
-- Injektionsmaterial (kat 2)
('Einmalspritze 10ml',        'Stueck',  50,  2),   --  4
('Einmalspritze 5ml',         'Stueck',  100, 2),   --  5
('Kanuele 0,9mm',             'Stueck',  80,  2),   --  6
('Infusionsschlauch',         'Stueck',  20,  2),   --  7
-- Schutzausruestung (kat 3)
('Einmalhandschuhe L',        'Box',     25,  3),   --  8
('Einmalhandschuhe S',        'Box',     15,  3),   --  9
('OP-Handschuhe M',           'Paar',    20,  3),   -- 10
('OP-Kittel steril L',        'Stueck',  10,  3),   -- 11
('Mundschutz FFP2',           'Stueck',  50,  3),   -- 12
('Schutzbrille',              'Stueck',  5,   3),   -- 13
-- Hygienematerial (kat 4)
('Desinfektionsmittel',       'Flasche', 10,  4),   -- 14
-- Diagnostikmaterial (kat 5)
('Blutentnahmeroehrchen EDTA','Stueck',  60,  5),   -- 15
('Blutzucker-Teststreifen',   'Packung', 8,   5),   -- 16
('Mundspatel Holz',           'Stueck',  200, 5),   -- 17
-- Wundversorgung (kat 6)
('Wundauflage steril 10x10',  'Stueck',  30,  6),   -- 18
('Nahtmaterial Vicryl 2-0',   'Packung', 10,  6),   -- 19
('Skalpell Einmal Gr. 22',    'Stueck',  15,  6);   -- 20


-- 5. BESTANDSBEWEGUNGEN
--    Aktueller Bestand je Material+Lager = SUM(EINGANG) - SUM(AUSGANG)
--    Materialien mit Bestand <= Mindestbestand loesen Warnung aus (rot in der UI)

INSERT INTO bestandsbewegungen (material_id, lager_id, bewegungstyp, menge, ablaufdatum, datum, bemerkung) VALUES

-- === Station 1 - Innere Medizin (lager_id = 1) ===
-- Mullbinde: +50 -5 = 45  (Mindest 20, OK)
(1,  1, 'EINGANG',  50, '2027-03-01', '2026-04-10 08:00:00', 'Erstbefuellung MedSupply GmbH'),
(1,  1, 'AUSGANG',   5, NULL,         '2026-04-15 10:30:00', 'Ausgabe an Pflegepersonal'),
-- Pflaster-Set: +12 = 12  (WARNUNG: 12 < Mindest 15)
(2,  1, 'EINGANG',  12, '2026-12-31', '2026-04-10 08:15:00', 'Erstbefuellung MedSupply GmbH'),
-- Einmalhandschuhe L: +30 = 30  (Mindest 25, OK)
(8,  1, 'EINGANG',  30, '2027-06-30', '2026-04-10 08:30:00', 'Erstbefuellung KlinikBedarf AG'),
-- Desinfektionsmittel: +20 -12 = 8  (WARNUNG: 8 < Mindest 10)
(14, 1, 'EINGANG',  20, '2026-09-15', '2026-04-10 08:45:00', 'Erstbefuellung PharmaLogistik GmbH'),
(14, 1, 'AUSGANG',  12, NULL,         '2026-04-28 11:00:00', 'Monatsverbrauch Station 1'),
-- Einmalspritze 10ml: +200 = 200  (Mindest 50, OK)
(4,  1, 'EINGANG', 200, '2027-01-01', '2026-04-12 09:00:00', 'Lieferung MedSupply GmbH'),

-- === Station 2 - Chirurgie (lager_id = 2) ===
-- Wundauflage: +60 = 60  (Mindest 30, OK)
(18, 2, 'EINGANG',  60, '2027-05-01', '2026-04-08 07:30:00', 'Erstbefuellung Sanitaets Direkt e.K.'),
-- Kanuele: +200 -50 = 150  (Mindest 80, OK)
(6,  2, 'EINGANG', 200, '2027-02-28', '2026-04-08 07:45:00', 'Erstbefuellung MedSupply GmbH'),
(6,  2, 'AUSGANG',  50, NULL,         '2026-04-20 14:00:00', 'Verbrauch OP-Vorbereitung'),
-- OP-Handschuhe M: +18 = 18  (WARNUNG: 18 < Mindest 20)
(10, 2, 'EINGANG',  18, '2026-11-30', '2026-04-08 08:00:00', 'Erstbefuellung KlinikBedarf AG'),
-- Schutzbrille: +10 = 10  (Mindest 5, OK)
(13, 2, 'EINGANG',  10, '2028-01-01', '2026-04-08 08:15:00', 'Erstbefuellung KlinikBedarf AG'),
-- Infusionsschlauch: +35 = 35  (Mindest 20, OK)
(7,  2, 'EINGANG',  35, '2027-08-01', '2026-04-08 08:30:00', 'Erstbefuellung PharmaLogistik GmbH'),

-- === OP-Saal (lager_id = 3) ===
-- Nahtmaterial Vicryl: +10 -5 = 5  (WARNUNG: 5 < Mindest 10)
(19, 3, 'EINGANG',  10, '2026-07-31', '2026-04-01 06:00:00', 'Erstbefuellung Sanitaets Direkt e.K.'),
(19, 3, 'AUSGANG',   5, NULL,         '2026-05-01 06:30:00', 'Verbrauch Morgen-OPs'),
-- OP-Kittel steril L: +20 = 20  (Mindest 10, OK)
(11, 3, 'EINGANG',  20, '2028-06-01', '2026-04-01 06:15:00', 'Erstbefuellung KlinikBedarf AG'),
-- Skalpell: +40 = 40  (Mindest 15, OK)
(20, 3, 'EINGANG',  40, '2027-04-01', '2026-04-01 06:30:00', 'Erstbefuellung Sanitaets Direkt e.K.'),
-- Mundschutz FFP2: +75 = 75  (Mindest 50, OK)
(12, 3, 'EINGANG',  75, '2026-12-31', '2026-04-01 06:45:00', 'Erstbefuellung KlinikBedarf AG'),

-- === ICU - Intensivstation (lager_id = 4) ===
-- Blutentnahmeroehrchen: +150 -30 = 120  (Mindest 60, OK)
(15, 4, 'EINGANG', 150, '2027-03-15', '2026-04-05 08:00:00', 'Lieferung PharmaLogistik GmbH'),
(15, 4, 'AUSGANG',  30, NULL,         '2026-04-25 09:00:00', 'Monatsverbrauch ICU'),
-- Blutzucker-Teststreifen: +8 -2 = 6  (WARNUNG: 6 < Mindest 8)
(16, 4, 'EINGANG',   8, '2026-08-31', '2026-04-05 08:15:00', 'Erstbefuellung PharmaLogistik GmbH'),
(16, 4, 'AUSGANG',   2, NULL,         '2026-05-03 16:00:00', 'Tagesverbrauch ICU'),
-- Einmalspritze 5ml: +300 = 300  (Mindest 100, OK)
(5,  4, 'EINGANG', 300, '2027-01-01', '2026-04-05 08:30:00', 'Erstbefuellung MedSupply GmbH'),
-- Einmalhandschuhe S: +14 = 14  (WARNUNG: 14 < Mindest 15)
(9,  4, 'EINGANG',  14, '2027-06-30', '2026-04-05 08:45:00', 'Erstbefuellung KlinikBedarf AG'),

-- === Zentrallager (lager_id = 5) ===
-- Kompressen: +200 = 200  (Mindest 100, OK)
(3,  5, 'EINGANG', 200, '2027-06-01', '2026-03-20 10:00:00', 'Erstbefuellung Zentrallager'),
-- Mundspatel Holz: +500 = 500  (Mindest 200, OK)
(17, 5, 'EINGANG', 500, '2028-01-01', '2026-03-20 10:15:00', 'Erstbefuellung Zentrallager');


-- 6. BESTELLUNGEN (4 Einträge) -- Nachbestellungen fuer Warnungs-Materialien
--    lager_id = Ziel-Lager der Bestellung
INSERT INTO bestellungen (material_id, lieferant_id, lager_id, menge, bestelldatum, lieferdatum, status) VALUES
(2,  1, 1, 30, '2026-05-02', '2026-05-07', 'offen'),      -- Pflaster-Set -> Station 1
(14, 3, 1, 20, '2026-05-01', '2026-05-06', 'offen'),      -- Desinfektionsmittel -> Station 1
(19, 4, 3, 20, '2026-05-03', '2026-05-08', 'offen'),      -- Nahtmaterial Vicryl -> OP-Saal
(16, 3, 4, 10, '2026-05-02', NULL,         'offen');      -- Teststreifen -> ICU (Nachbestellung wegen Warnung)


-- ============================================================
-- KONTROLLABFRAGE (nach dem Import ausfuehren zum Pruefen)
-- Aktuellen Bestand je Material und Lager berechnen:
-- ============================================================
/*
SELECT
    m.name                                                                      AS Material,
    s.name                                                                      AS Lager,
    m.mindestbestand                                                            AS Mindestbestand,
    SUM(CASE WHEN b.bewegungstyp = 'EINGANG' THEN b.menge ELSE -b.menge END)   AS Bestand,
    CASE
        WHEN SUM(CASE WHEN b.bewegungstyp = 'EINGANG' THEN b.menge ELSE -b.menge END) <= m.mindestbestand
        THEN '*** WARNUNG ***'
        ELSE 'OK'
    END AS Status
FROM bestandsbewegungen b
JOIN materialien   m ON m.material_id = b.material_id
JOIN stationslager s ON s.lager_id    = b.lager_id
GROUP BY b.material_id, b.lager_id, m.name, s.name, m.mindestbestand
ORDER BY s.name, m.name;
*/
