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
    fecha_nacimiento date NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE public.cliente_sec;

CREATE TABLE public.concepto_canje
(
    id integer NOT NULL,
    desc_concepto character varying(200) NOT NULL,
    puntos_requeridos  integer NOT NULL,
    CONSTRAINT concepto_canje_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE public.concepto_canje_sec;

CREATE TABLE public.regla
(
    id integer NOT NULL,
    limite_max integer,
    limite_min integer,
    id_vencimiento integer,
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
alter table regla
	add constraint fk_id_vencimiento
		foreign key (id_vencimiento) references vencimiento_puntos
			on delete cascade;
CREATE SEQUENCE public.vencimiento_puntos_sec;

CREATE TABLE public.bolsa_puntos
(
    id integer NOT NULL,
    id_cliente integer NOT NULL,
    fecha_asignacion date NOT NULL,
    fecha_vencimiento date,
    puntaje_asignado integer not null,
    puntaje_utilizado integer,
    saldo integer,
    monto integer,
    CONSTRAINT bolsa_puntos_pkey PRIMARY KEY (id)
);
alter table bolsa_puntos
	add constraint fk_id_cliente
		foreign key (id_cliente) references cliente
			on delete cascade;
CREATE SEQUENCE public.bolsa_puntos_sec;



CREATE TABLE public.canje_puntos
(
    id integer NOT NULL,
    id_cliente integer NOT NULL,
    fecha_uso date NOT NULL,
    puntaje_utilizado integer not null,
    id_concepto integer,
    CONSTRAINT canje_puntos_pkey PRIMARY KEY (id)
);
alter table canje_puntos
	add constraint fk_id_cliente
		foreign key (id_cliente) references cliente
			on delete cascade;
alter table canje_puntos
	add constraint fk_id_conepto
		foreign key (id_concepto) references concepto_canje
			on delete cascade;

CREATE SEQUENCE public.canje_puntos_sec;

CREATE TABLE public.det_canje_puntos
(
    id integer NOT NULL,
    id_canje integer NOT NULL,
    id_bolsa_puntos integer NOT NULL,
    puntaje_utilizado integer not null,
    CONSTRAINT det_canje_puntos_pkey PRIMARY KEY (id)
);
alter table det_canje_puntos
	add constraint fk_id_canje
		foreign key (id_canje) references canje_puntos
			on delete cascade;
alter table det_canje_puntos
	add constraint fk_id_bolsa_puntos
		foreign key (id_bolsa_puntos) references bolsa_puntos
			on delete cascade;

CREATE SEQUENCE public.det_canje_puntos_sec;
