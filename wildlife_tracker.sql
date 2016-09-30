--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: animals; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE animals (
    id integer NOT NULL,
    name character varying,
    type character varying,
    health character varying,
    age character varying
);


ALTER TABLE animals OWNER TO satchelgrant;

--
-- Name: animals_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE animals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animals_id_seq OWNER TO satchelgrant;

--
-- Name: animals_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE animals_id_seq OWNED BY animals.id;


--
-- Name: locations; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE locations (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE locations OWNER TO satchelgrant;

--
-- Name: locations_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE locations_id_seq OWNER TO satchelgrant;

--
-- Name: locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE locations_id_seq OWNED BY locations.id;


--
-- Name: rangers; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE rangers (
    id integer NOT NULL,
    name character varying,
    badgeid character varying
);


ALTER TABLE rangers OWNER TO satchelgrant;

--
-- Name: rangers_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE rangers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rangers_id_seq OWNER TO satchelgrant;

--
-- Name: rangers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE rangers_id_seq OWNED BY rangers.id;


--
-- Name: sightings; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE sightings (
    id integer NOT NULL,
    animalid integer,
    locationid integer,
    rangerid integer,
    timespotted timestamp without time zone
);


ALTER TABLE sightings OWNER TO satchelgrant;

--
-- Name: sightings_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE sightings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sightings_id_seq OWNER TO satchelgrant;

--
-- Name: sightings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE sightings_id_seq OWNED BY sightings.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY animals ALTER COLUMN id SET DEFAULT nextval('animals_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY locations ALTER COLUMN id SET DEFAULT nextval('locations_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY rangers ALTER COLUMN id SET DEFAULT nextval('rangers_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY sightings ALTER COLUMN id SET DEFAULT nextval('sightings_id_seq'::regclass);


--
-- Data for Name: animals; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY animals (id, name, type, health, age) FROM stdin;
1	Cat	animal	\N	\N
\.


--
-- Name: animals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('animals_id_seq', 1, true);


--
-- Data for Name: locations; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY locations (id, name) FROM stdin;
1	By the tree
\.


--
-- Name: locations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('locations_id_seq', 1, true);


--
-- Data for Name: rangers; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY rangers (id, name, badgeid) FROM stdin;
\.


--
-- Name: rangers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('rangers_id_seq', 1, false);


--
-- Data for Name: sightings; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY sightings (id, animalid, locationid, rangerid, timespotted) FROM stdin;
\.


--
-- Name: sightings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('sightings_id_seq', 1, false);


--
-- Name: animals_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (id);


--
-- Name: locations_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY locations
    ADD CONSTRAINT locations_pkey PRIMARY KEY (id);


--
-- Name: rangers_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY rangers
    ADD CONSTRAINT rangers_pkey PRIMARY KEY (id);


--
-- Name: sightings_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY sightings
    ADD CONSTRAINT sightings_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: satchelgrant
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM satchelgrant;
GRANT ALL ON SCHEMA public TO satchelgrant;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

