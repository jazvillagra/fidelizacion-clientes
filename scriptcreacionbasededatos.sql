CREATE TABLE public.cliente
(
    id integer NOT NULL,
    nombre character varying(50) NOT NULL,
    apellido character varying(50) NOT NULL,
    nro_documento character varying(50) NOT NULL UNIQUE,
    tipo_documento character varying(50) NOT NULL,
    nacionalidad character varying(50) NOT NULL,
    email character varying(50),
    telefono character varying(50),
    fecha_nacionalidad character varying(50) NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE public.cliente_sec;

CREATE TABLE public.concepto_canje
(
    id integer NOT NULL,
    desc_concepto integer NOT NULL,
    puntos_requeridos character varying(200) NOT NULL,
    CONSTRAINT concepto_canje_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE public.concepto_canje_sec;

CREATE TABLE public.regla
(
    id integer NOT NULL,
    limite_max integer NOT NULL,
    limito_min integer NOT NULL,
    monto_equivalencia integer NOT NULL,
    CONSTRAINT regla_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE public.regla_sec;

CREATE TABLE public.vencimiento_puntos
(
    id integer NOT NULL,
    fecha_ini_validez date NOT NULL,
    fecha_fin_validez date NOT NULL,
    duracion integer NOT NULL,
    CONSTRAINT vencimiento_puntos_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE public.vencimiento_puntos_sec;

CREATE TABLE public.bolsa_puntos
(
    id integer NOT NULL,
    id_cliente integer NOT NULL,
    fecha_asignacion date NOT NULL,
    fecha_vencimiento date NOT NULL,
    puntaje_asignado integer not null,
    puntaje_utilizado integer not null,
    saldo integer not null,
    monto integer not null,
    CONSTRAINT bolsa_puntos_pkey PRIMARY KEY (id),
    CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente)
        REFERENCES cliente(id)
);
CREATE SEQUENCE public.bolsa_puntos_sec;

CREATE TABLE public.canje_puntos
(
    id integer NOT NULL,
    id_cliente integer NOT NULL,
    fecha_uso date NOT NULL,
    puntaje_utilizado integer not null,
    CONSTRAINT canje_puntos_pkey PRIMARY KEY (id),
    CONSTRAINT fk_id_cliente FOREIGN KEY(id_cliente)
        REFERENCES cliente(id)
);
CREATE SEQUENCE public.canje_puntos_sec;

CREATE TABLE public.det_canje_puntos
(
    id integer NOT NULL,
    id_canje integer NOT NULL,
    id_bolsa_puntos integer NOT NULL,
    puntaje_utilizado integer not null,
    CONSTRAINT det_canje_puntos_pkey PRIMARY KEY (id),
    CONSTRAINT fk_id_canje FOREIGN KEY(id_canje)
        REFERENCES canje_puntos(id),
    CONSTRAINT fk_id_bolsa_puntos FOREIGN KEY(id_bolsa_puntos)
        REFERENCES bolsa_puntos(id)
);
CREATE SEQUENCE public.det_canje_puntos_sec;