CREATE TABLE public.users (
	id SERIAL,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(120) NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

---

CREATE TABLE public.actives (
	id SERIAL,
    name VARCHAR(70) NOT NULL,
    ticket VARCHAR(10) NOT NULL,
    category VARCHAR(12) NOT NULL,
    type VARCHAR(8) NOT NULL,
    currency VARCHAR(8) NOT NULL,
    quantity INT NULL,
    evaluation INT NULL,
    user_id INT NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
	CONSTRAINT actives_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id)
);

CREATE INDEX actives_ticket_idx ON public.actives (ticket);
CREATE INDEX actives_type_idx ON public.actives (type);
CREATE INDEX actives_category_idx ON public.actives (category);
CREATE INDEX actives_ccurrency_idx ON public.actives (currency);

---

CREATE TABLE public.reports (
	id SERIAL,
    note VARCHAR(255) NOT NULL,
    date_note TIMESTAMP NOT NULL,
    actives_id INT NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
	CONSTRAINT reports_pkey PRIMARY KEY (id),
    CONSTRAINT fk_actives_id FOREIGN KEY (actives_id) REFERENCES public.actives(id)
);

CREATE INDEX reports_date_note_idx ON public.reports (date_note);

---

CREATE TABLE public.wallets (
	id SERIAL,
    category VARCHAR(70) NOT NULL,
    quantity INT NULL,
    percent INT NULL,
    user_id INT NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP,
	CONSTRAINT wallets_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id)
);

CREATE INDEX wallets_category_idx ON public.wallets (category);

---