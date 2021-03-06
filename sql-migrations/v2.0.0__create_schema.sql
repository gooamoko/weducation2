------------------------------------------------------------------------------------------------------------------------
-- Скрипт для создания БД информационной системы учебного заведения
-- Представляет собой максимально приближенное состояние на момент окончания поддержки 1-й версии
------------------------------------------------------------------------------------------------------------------------

-- Функция для получения даты в специальном формате ггггммн
-- гггг - 4-х значный год, мм - 2-х значный месяц, н - порядковый номер недели
CREATE OR REPLACE FUNCTION public.getdate(year integer, month integer, week integer) RETURNS integer
    LANGUAGE sql
AS $$
    SELECT (year * 1000 + month * 10 + week);
$$;

-- Учетные записи
CREATE TABLE IF NOT EXISTS public.accounts (
    aco_pcode       serial NOT NULL,
    aco_login       varchar(50) NOT NULL,
    aco_password    varchar(50) NOT NULL,
    aco_role        integer NOT NULL,
    aco_fullname    varchar(255) NOT NULL,
    aco_code        integer,
    aco_roles       varchar,
    CONSTRAINT accounts_pk PRIMARY KEY(aco_pcode),
    CONSTRAINT unique_account UNIQUE (aco_login)
);

-- Информация о клиентских сессиях
-- todo Грохнуть
CREATE TABLE IF NOT EXISTS public.clientsessions (
    cls_pcode           serial NOT NULL,
    cls_acocode         integer,
    cls_ipaddr          varchar(128),
    cls_created         timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT clientsessions_pk PRIMARY KEY(cls_pcode),
    CONSTRAINT sessions_accounts_fk FOREIGN KEY (cls_acocode) REFERENCES public.accounts(aco_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Сообщения от одного пользователя другому
-- todo Грохнуть
CREATE TABLE IF NOT EXISTS public.messages (
    msg_pcode       serial NOT NULL,
    msg_from        varchar(255) NOT NULL,
    msg_srcaccount  integer,
    msg_dstaccount  integer NOT NULL,
    msg_title       varchar(255) NOT NULL,
    msg_text        text NOT NULL,
    msg_datetime    timestamp without time zone DEFAULT now() NOT NULL,
    msg_received    boolean DEFAULT false NOT NULL,
    msg_deleted     boolean DEFAULT false NOT NULL,
    CONSTRAINT messages_pk PRIMARY KEY (msg_pcode),
    CONSTRAINT messages_accounts_dst_fk FOREIGN KEY (msg_dstaccount) REFERENCES public.accounts(aco_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT messages_accounts_src_fk FOREIGN KEY (msg_srcaccount) REFERENCES public.accounts(aco_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Учебные заведения
CREATE TABLE IF NOT EXISTS public.schools (
    scl_pcode           serial NOT NULL,
    scl_fullname        varchar(255) NOT NULL,
    scl_shortname       varchar(255) NOT NULL,
    scl_place           varchar(128) NOT NULL,
    scl_director        varchar(128) NOT NULL,
    scl_current         boolean DEFAULT false NOT NULL,
    CONSTRAINT schools_pk PRIMARY KEY (scl_pcode),
    CONSTRAINT unique_school UNIQUE (scl_fullname, scl_place)
);

-- Переименования учебного заведения
--todo Либо привязать к учебному заведению, либо грохнуть
CREATE TABLE IF NOT EXISTS public.renamings (
    ren_pcode           serial NOT NULL,
    ren_oldname         varchar(255) NOT NULL,
    ren_newname         varchar(255) NOT NULL,
    ren_date            date DEFAULT current_date NOT NULL,
    CONSTRAINT renamings_pk PRIMARY KEY (ren_pcode)
);

-- Специальности
CREATE TABLE IF NOT EXISTS public.specialities (
    spc_pcode           serial NOT NULL,
    spc_description     varchar(255) NOT NULL,
    spc_name            varchar(10) NOT NULL,
    spc_actual          boolean DEFAULT false NOT NULL,
    spc_aviable         boolean DEFAULT false NOT NULL,
    CONSTRAINT specialities_pk PRIMARY KEY (spc_pcode)
);

-- Количество мест по специальности
CREATE TABLE IF NOT EXISTS public.seats (
    sea_pcode           serial NOT NULL,
    sea_spccode         integer NOT NULL,
    sea_year            integer NOT NULL,
    sea_bcount          integer DEFAULT 0 NOT NULL,
    sea_ccount          integer DEFAULT 0 NOT NULL,
    sea_extramural      boolean DEFAULT false NOT NULL,
    CONSTRAINT seats_pk PRIMARY KEY (sea_pcode),
    CONSTRAINT unique_seats UNIQUE (sea_year, sea_spccode, sea_extramural),
    CONSTRAINT seats_specialities_fk FOREIGN KEY (sea_spccode) REFERENCES public.specialities(spc_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Населенные пункты. Наименование и тип
CREATE TABLE IF NOT EXISTS public.places (
    plc_pcode       serial NOT NULL,
    plc_type        integer DEFAULT 0 NOT NULL,
    plc_name        varchar(255) NOT NULL,
    CONSTRAINT places_pk PRIMARY KEY (plc_pcode),
    CONSTRAINT unique_place UNIQUE (plc_type, plc_name)
);

-- Учебные отделения
CREATE TABLE IF NOT EXISTS public.departments (
    dep_pcode       serial NOT NULL,
    dep_name        varchar(100) NOT NULL,
    dep_master      varchar(128) NOT NULL,
    dep_secretar    varchar(128) NOT NULL,
    CONSTRAINT departments_pk PRIMARY KEY (dep_pcode),
    CONSTRAINT unique_department UNIQUE (dep_name)
);

-- Профили отделений
CREATE TABLE IF NOT EXISTS public.departmentprofiles (
    dpr_pcode       serial NOT NULL,
    dpr_depcode     integer NOT NULL,
    dpr_spccode     integer NOT NULL,
    dpr_extramural  boolean DEFAULT false NOT NULL,
    CONSTRAINT departmentprofiles_pk PRIMARY KEY (dpr_pcode),
    CONSTRAINT unique_departmentprofile UNIQUE (dpr_depcode, dpr_spccode, dpr_extramural),
    CONSTRAINT departmentprofiles_departments_fk FOREIGN KEY (dpr_depcode) REFERENCES public.departments(dep_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT departmentprofiles_specialities_fk FOREIGN KEY (dpr_spccode) REFERENCES public.specialities(spc_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Персоны. Учащиеся.
CREATE TABLE IF NOT EXISTS public.persons (
    psn_pcode           serial NOT NULL,
    psn_plccode         integer,
    psn_lngcode         integer DEFAULT 0 NOT NULL,
    psn_birthplace      varchar(255),
    psn_male            boolean NOT NULL,
    psn_foreign         boolean NOT NULL,
    psn_birthdate       date NOT NULL,
    psn_firstname       varchar(50) NOT NULL,
    psn_middlename      varchar(50) NOT NULL,
    psn_lastname        varchar(50) NOT NULL,
    psn_orphan          boolean DEFAULT false NOT NULL,
    psn_invalid         boolean DEFAULT false NOT NULL,
    psn_passportseria   varchar(6),
    psn_passportnumber  varchar(10),
    psn_passportdate    date,
    psn_passportdept    varchar(255),
    psn_inn             varchar(12),
    psn_snils           varchar(15),
    psn_address         varchar(255),
    psn_phones          varchar(128),
    psn_avgball         double precision DEFAULT 0 NOT NULL,
    CONSTRAINT persons_pk PRIMARY KEY (psn_pcode),
    CONSTRAINT persons_places_fk FOREIGN KEY (psn_plccode) REFERENCES public.places(plc_pcode) ON UPDATE CASCADE
);


CREATE INDEX IF NOT EXISTS idx_persons_firstname ON public.persons USING btree (psn_firstname);

-- заявки для поступления персоны на специальность по определенной форме обучения
CREATE TABLE IF NOT EXISTS public.requests (
    req_pcode           serial NOT NULL,
    req_psncode         integer NOT NULL,
    req_spccode         integer NOT NULL,
    req_year            integer NOT NULL,
    req_extramural      boolean NOT NULL,
    CONSTRAINT requests_pk PRIMARY KEY (req_pcode),
    CONSTRAINT unique_requests UNIQUE (req_psncode, req_spccode, req_year, req_extramural),
    CONSTRAINT requests_persons_fk FOREIGN KEY (req_psncode) REFERENCES public.persons(psn_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT requests_specialities_fk FOREIGN KEY (req_spccode) REFERENCES public.specialities(spc_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Законные представители обучающихся
CREATE TABLE IF NOT EXISTS public.delegates (
    dlg_pcode       serial NOT NULL,
    dlg_psncode     integer NOT NULL,
    dlg_fullname    varchar(128) NOT NULL,
    dlg_job         varchar(255) NOT NULL,
    dlg_post        varchar(255),
    dlg_description varchar(255),
    dlg_phones      varchar(128),
    CONSTRAINT delegates_pk PRIMARY KEY (dlg_pcode),
    CONSTRAINT unique_delegate UNIQUE (dlg_psncode, dlg_fullname),
    CONSTRAINT delegates_persons_fk FOREIGN KEY (dlg_psncode) REFERENCES public.persons(psn_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Учебные планы
CREATE TABLE IF NOT EXISTS public.plans (
    pln_pcode           serial NOT NULL,
    pln_spccode         integer NOT NULL,
    pln_name            varchar(255) NOT NULL,
    pln_extramural      boolean DEFAULT false NOT NULL,
    pln_years           integer DEFAULT 0 NOT NULL,
    pln_months          integer DEFAULT 0 NOT NULL,
    pln_beginyear       integer DEFAULT 0 NOT NULL,
    pln_spcname         varchar(255) DEFAULT '' NOT NULL,
    pln_kvalification   varchar(128) DEFAULT '' NOT NULL,
    pln_specialization  varchar(128) DEFAULT '' NOT NULL,
    pln_spckey          varchar(128) DEFAULT '' NOT NULL,
    CONSTRAINT plans_pk PRIMARY KEY (pln_pcode),
    CONSTRAINT plans_specialities_fk FOREIGN KEY (pln_spccode) REFERENCES public.specialities(spc_pcode) ON UPDATE CASCADE
);

-- Модули (модуль это что-то типа части учебного плана, в которой несколько дисциплин)
CREATE TABLE IF NOT EXISTS public.modules (
    mod_pcode       serial NOT NULL,
    mod_plncode     integer NOT NULL,
    mod_exfcode     integer,
    mod_name        varchar(255) NOT NULL,
    mod_number      integer DEFAULT 0 NOT NULL,
    CONSTRAINT modules_pk PRIMARY KEY (mod_pcode),
    CONSTRAINT unique_module UNIQUE (mod_plncode, mod_name),
    CONSTRAINT modules_plans_fk FOREIGN KEY (mod_plncode) REFERENCES public.plans(pln_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Дисциплины
CREATE TABLE IF NOT EXISTS public.subjects (
    sub_pcode           serial NOT NULL,
    sub_modcode         integer,
    sub_plncode         integer NOT NULL,
    sub_fullname        varchar(255) NOT NULL,
    sub_shortname       varchar(30),
    sub_number          integer DEFAULT 0 NOT NULL,
    CONSTRAINT subjects_pk PRIMARY KEY (sub_pcode),
    CONSTRAINT subjects_modules_fk FOREIGN KEY (sub_modcode) REFERENCES public.modules(mod_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT subjects_plans_fk FOREIGN KEY (sub_plncode) REFERENCES public.plans(pln_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Учебная нагрузка по дисциплинам
CREATE TABLE IF NOT EXISTS public.load (
    lod_pcode       serial NOT NULL,
    lod_subcode     integer NOT NULL,
    lod_exfcode     integer,
    lod_course      integer NOT NULL,
    lod_semester    integer NOT NULL,
    lod_auditory    integer DEFAULT 0 NOT NULL,
    lod_maximum     integer DEFAULT 0 NOT NULL,
    lod_courseproj  integer DEFAULT 0 NOT NULL,
    CONSTRAINT load_pk PRIMARY KEY (lod_pcode),
    CONSTRAINT unique_load UNIQUE (lod_subcode, lod_course, lod_semester),
    CONSTRAINT load_subjects_fk FOREIGN KEY (lod_subcode) REFERENCES public.subjects(sub_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Группы студентов
CREATE TABLE IF NOT EXISTS public.groups (
    grp_pcode       serial NOT NULL,
    grp_name        varchar(20) NOT NULL,
    grp_active      boolean DEFAULT false NOT NULL,
    grp_spccode     integer NOT NULL,
    grp_master      varchar(150),
    grp_year        integer NOT NULL,
    grp_extramural  boolean DEFAULT false NOT NULL,
    grp_course      integer DEFAULT 1 NOT NULL,
    grp_plncode     integer,
    grp_commercial  boolean DEFAULT false NOT NULL,
    CONSTRAINT groups_pk PRIMARY KEY (grp_pcode),
    CONSTRAINT unique_group UNIQUE (grp_name),
    CONSTRAINT groups_specialities_fk FOREIGN KEY (grp_spccode) REFERENCES public.specialities(spc_pcode) ON UPDATE CASCADE
);

-- Семестры групп
CREATE TABLE IF NOT EXISTS public.groupsemesters (
    grs_pcode       serial NOT NULL,
    grs_grpcode     integer NOT NULL,
    grs_course      integer NOT NULL,
    grs_semester    integer NOT NULL,
    grs_beginweek   integer NOT NULL,
    grs_beginmonth  integer NOT NULL,
    grs_beginyear   integer NOT NULL,
    grs_endweek     integer NOT NULL,
    grs_endmonth    integer NOT NULL,
    grs_endyear     integer NOT NULL,
    CONSTRAINT groupsemesters_pk PRIMARY KEY (grs_pcode),
    CONSTRAINT unique_semester UNIQUE (grs_course, grs_semester, grs_grpcode),
    CONSTRAINT groupsemesters_groups_fk FOREIGN KEY (grs_grpcode) REFERENCES public.groups(grp_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Личная карточка студента
CREATE TABLE IF NOT EXISTS public.cards (
    crd_pcode               serial NOT NULL,
    crd_psncode             integer NOT NULL,
    crd_spccode             integer NOT NULL,
    crd_sclcode             integer NOT NULL,
    crd_grpcode             integer,
    crd_plncode             integer,
    crd_extramural          boolean DEFAULT false NOT NULL,
    crd_docorganization     varchar(255),
    crd_remanded            boolean DEFAULT false NOT NULL,
    crd_remandcommand       varchar(128),
    crd_diplomenumber       varchar(50),
    crd_appendixnumber      varchar(50),
    crd_regnumber           varchar(50),
    crd_comissiondirector   varchar(128),
    crd_gosexam             boolean NOT NULL,
    crd_diplomelength       double precision,
    crd_diplometheme        varchar(255),
    crd_diplomemark         integer,
    crd_red                 boolean NOT NULL,
    crd_bdate               date NOT NULL,
    crd_edate               date,
    crd_docdate             date,
    crd_comissiondate       date,
    crd_diplomedate         date,
    crd_active              boolean DEFAULT false NOT NULL,
    crd_commercial          boolean DEFAULT false NOT NULL,
    crd_biletnumber         varchar(10),
    crd_docname             varchar(255),
    crd_remandreason        varchar(255),
    CONSTRAINT cards_pk PRIMARY KEY (crd_pcode),
    CONSTRAINT cards_persons_fk FOREIGN KEY (crd_psncode) REFERENCES public.persons(psn_pcode) ON UPDATE CASCADE,
    CONSTRAINT cards_schools_fk FOREIGN KEY (crd_sclcode) REFERENCES public.schools(scl_pcode) ON UPDATE CASCADE,
    CONSTRAINT cards_specialities_fk FOREIGN KEY (crd_spccode) REFERENCES public.specialities(spc_pcode) ON UPDATE CASCADE
);

-- Информация о пропусках за неделю
CREATE TABLE IF NOT EXISTS public.weekmissings (
    wms_pcode       serial NOT NULL,
    wms_psncode     integer NOT NULL,
    wms_crdcode     integer NOT NULL,
    wms_year        integer NOT NULL,
    wms_month       integer NOT NULL,
    wms_week        integer NOT NULL,
    wms_legal       integer NOT NULL,
    wms_illegal     integer NOT NULL,
    CONSTRAINT weekmissings_pk PRIMARY KEY (wms_pcode),
    CONSTRAINT weekmissings_cards_fk FOREIGN KEY (wms_crdcode) REFERENCES public.cards(crd_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT weekmissings_persons_fk FOREIGN KEY (wms_psncode) REFERENCES public.persons(psn_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Практики
CREATE TABLE IF NOT EXISTS public.practics (
    prc_pcode           serial NOT NULL,
    prc_modcode         integer,
    prc_plncode         integer NOT NULL,
    prc_fullname        varchar(255) NOT NULL,
    prc_course          integer DEFAULT 1 NOT NULL,
    prc_semester        integer DEFAULT 1 NOT NULL,
    prc_length          real DEFAULT 0 NOT NULL,
    prc_shortname       varchar(30) DEFAULT 'FIXME' NOT NULL,
    CONSTRAINT practics_pk PRIMARY KEY (prc_pcode),
    CONSTRAINT practics_modules_fk FOREIGN KEY (prc_modcode) REFERENCES public.modules(mod_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT practics_plans_fk FOREIGN KEY (prc_plncode) REFERENCES public.plans(pln_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Оценки за практику
CREATE TABLE IF NOT EXISTS public.pmarks (
    pmk_pcode           serial NOT NULL,
    pmk_prccode         integer,
    pmk_crdcode         integer,
    pmk_mark            integer,
    CONSTRAINT pmarks_pk PRIMARY KEY (pmk_pcode),
    CONSTRAINT unique_pmark UNIQUE (pmk_crdcode, pmk_prccode),
    CONSTRAINT pmarks_cards_fk FOREIGN KEY (pmk_crdcode) REFERENCES public.cards(crd_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT pmarks_practics_fk FOREIGN KEY (pmk_prccode) REFERENCES public.practics(prc_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Практики, которые будут отображены в выписке
CREATE TABLE IF NOT EXISTS public.finalpractics (
    fpc_pcode       serial NOT NULL,
    fpc_plncode     integer NOT NULL,
    fpc_name        varchar(255),
    fpc_number      integer DEFAULT 0 NOT NULL,
    fpc_length      double precision NOT NULL,
    CONSTRAINT finalpractics_pk PRIMARY KEY (fpc_pcode),
    CONSTRAINT unique_finalpractic UNIQUE (fpc_name, fpc_plncode),
    CONSTRAINT finalpractics_plans_fk FOREIGN KEY (fpc_plncode) REFERENCES public.plans(pln_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Оценки за практику, которая будет в выписке
CREATE TABLE IF NOT EXISTS public.fpmarks (
    fpm_pcode       serial NOT NULL,
    fpm_fpccode     integer NOT NULL,
    fpm_crdcode     integer NOT NULL,
    fpm_mark        integer NOT NULL,
    CONSTRAINT fpmarks_pk PRIMARY KEY (fpm_pcode),
    CONSTRAINT unique_fpmark UNIQUE (fpm_fpccode, fpm_crdcode),
    CONSTRAINT fpmarks_cards_fk FOREIGN KEY (fpm_crdcode) REFERENCES public.cards(crd_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fpmarks_finalpractics_fk FOREIGN KEY (fpm_fpccode) REFERENCES public.finalpractics(fpc_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Оценки за курсовые проекты
CREATE TABLE IF NOT EXISTS public.cmarks (
    cmk_pcode       serial NOT NULL,
    cmk_subcode     integer NOT NULL,
    cmk_crdcode     integer NOT NULL,
    cmk_theme       varchar(255),
    cmk_mark        integer NOT NULL,
    cmk_course      integer DEFAULT 1 NOT NULL,
    cmk_semester    integer DEFAULT 1 NOT NULL,
    CONSTRAINT cmarks_pk PRIMARY KEY (cmk_pcode),
    CONSTRAINT unique_cmark UNIQUE (cmk_crdcode, cmk_subcode, cmk_theme),
    CONSTRAINT cmarks_cards_fk FOREIGN KEY (cmk_crdcode) REFERENCES public.cards(crd_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT cmarks_subjects_fk FOREIGN KEY (cmk_subcode) REFERENCES public.subjects(sub_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Итоговые оценки по дисциплинам в выписку
CREATE TABLE IF NOT EXISTS public.fmarks (
    fmk_pcode       serial NOT NULL,
    fmk_subcode     integer,
    fmk_crdcode     integer NOT NULL,
    fmk_mark        integer NOT NULL,
    fmk_audload     integer NOT NULL,
    fmk_maxload     integer NOT NULL,
    fmk_modcode     integer,
    CONSTRAINT fmarks_pk PRIMARY KEY (fmk_pcode),
    CONSTRAINT unique_fmark UNIQUE (fmk_crdcode, fmk_subcode, fmk_modcode),
    CONSTRAINT fmarks_cards_fk FOREIGN KEY (fmk_crdcode) REFERENCES public.cards(crd_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fmarks_modules_fk FOREIGN KEY (fmk_modcode) REFERENCES public.modules(mod_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fmarks_subjects_fk FOREIGN KEY (fmk_subcode) REFERENCES public.subjects(sub_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- ГОС экзамены
CREATE TABLE IF NOT EXISTS public.gosexams (
    gex_pcode       serial NOT NULL,
    gex_subcode     integer NOT NULL,
    gex_plncode     integer NOT NULL,
    CONSTRAINT gosexams_pk PRIMARY KEY (gex_pcode),
    CONSTRAINT unique_gosexam UNIQUE (gex_subcode, gex_plncode),
    CONSTRAINT gosexams_plans_fk FOREIGN KEY (gex_plncode) REFERENCES public.plans(pln_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT gosexams_subjects_fk FOREIGN KEY (gex_subcode) REFERENCES public.subjects(sub_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Оценки за ГОС экзамены
CREATE TABLE IF NOT EXISTS public.gmarks (
    gmk_pcode       serial  NOT NULL,
    gmk_subcode     integer NOT NULL,
    gmk_crdcode     integer NOT NULL,
    gmk_mark        integer NOT NULL,
    CONSTRAINT gmarks_pk PRIMARY KEY (gmk_pcode),
    CONSTRAINT unique_gmark UNIQUE (gmk_crdcode, gmk_subcode),
    CONSTRAINT gmarks_cards_fk FOREIGN KEY (gmk_crdcode) REFERENCES public.cards(crd_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT gmarks_subjects_fk FOREIGN KEY (gmk_subcode) REFERENCES public.subjects(sub_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Оценки за месяц
CREATE TABLE IF NOT EXISTS public.monthmarks (
    mmk_pcode       serial NOT NULL,
    mmk_subcode     integer NOT NULL,
    mmk_psncode     integer NOT NULL,
    mmk_crdcode     integer NOT NULL,
    mmk_year        integer NOT NULL,
    mmk_month       integer NOT NULL,
    mmk_mark        integer NOT NULL,
    CONSTRAINT monthmarks_pk PRIMARY KEY (mmk_pcode),
    CONSTRAINT unique_monthmark UNIQUE (mmk_year, mmk_month, mmk_crdcode, mmk_subcode),
    CONSTRAINT monthmarks_cards_fk FOREIGN KEY (mmk_crdcode) REFERENCES public.cards(crd_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT monthmarks_persons_fk FOREIGN KEY (mmk_psncode) REFERENCES public.persons(psn_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT monthmarks_subjects_fk FOREIGN KEY (mmk_subcode) REFERENCES public.subjects(sub_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Оценки за семестр
CREATE TABLE IF NOT EXISTS public.semestermarks (
    smk_pcode           serial NOT NULL,
    smk_psncode         integer NOT NULL,
    smk_crdcode         integer NOT NULL,
    smk_subcode         integer,
    smk_modcode         integer,
    smk_course          integer NOT NULL,
    smk_semester        integer NOT NULL,
    smk_mark            integer NOT NULL,
    CONSTRAINT semestermarks_pk PRIMARY KEY (smk_pcode),
    CONSTRAINT semestermarks_cards_fk FOREIGN KEY (smk_crdcode) REFERENCES public.cards(crd_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT semestermarks_modules_fk FOREIGN KEY (smk_modcode) REFERENCES public.modules(mod_pcode) ON UPDATE CASCADE,
    CONSTRAINT semestermarks_persons_fk FOREIGN KEY (smk_psncode) REFERENCES public.persons(psn_pcode) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT semestermarks_subjects_fk FOREIGN KEY (smk_subcode) REFERENCES public.subjects(sub_pcode) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE OR REPLACE VIEW public.missingsview AS
SELECT weekmissings.wms_legal                                                               AS wmv_legal,
       weekmissings.wms_illegal                                                             AS wmv_illegal,
       weekmissings.wms_crdcode                                                             AS wmv_crdcode,
       weekmissings.wms_year                                                                AS wmv_year,
       weekmissings.wms_month                                                               AS wmv_month,
       public.getdate(weekmissings.wms_year, weekmissings.wms_month, weekmissings.wms_week) AS wmv_date
FROM public.weekmissings;


CREATE OR REPLACE VIEW public.diplomeblanks AS
SELECT
    cards.crd_pcode                                                                         AS dbk_pcode,
    cards.crd_spccode                                                                       AS dbk_spccode,
    cards.crd_extramural                                                                    AS dbk_extramural,
    (persons.psn_firstname || ' ' || persons.psn_middlename || ' ' || persons.psn_lastname) AS dbk_fullname,
    (date_part('year', cards.crd_edate) <> date_part('year', now()))                        AS dbk_duplicate,
    cards.crd_red                                                                           AS dbk_red,
    cards.crd_diplomenumber                                                                 AS dbk_diplomenumber,
    cards.crd_appendixnumber                                                                AS dbk_appendixnumber,
    cards.crd_regnumber                                                                     AS dbk_regnumber,
    cards.crd_diplomedate                                                                   AS dbk_diplomedate,
    cards.crd_edate                                                                         AS dbk_edate
FROM public.cards, public.persons
WHERE (cards.crd_psncode = persons.psn_pcode)
  AND (cards.crd_remanded = false)
  AND (date_part('year', cards.crd_diplomedate) = date_part('year', now()));


-- Вьюха для поиска двойников персон
-- todo возможно, она не нужна
CREATE OR REPLACE VIEW public.dublicates AS
SELECT persons.psn_firstname    AS dbl_fname,
       persons.psn_middlename   AS dbl_mname,
       persons.psn_lastname     AS dbl_lname,
       persons.psn_birthdate    AS dbl_birthdate,
       count(persons.psn_pcode) AS dbl_count
FROM public.persons
WHERE ("substring"((persons.psn_firstname)::text, 1, 3) IN
       (SELECT DISTINCT "substring"((persons_1.psn_firstname)::text, 1, 3) AS "substring"
        FROM public.persons persons_1))
GROUP BY persons.psn_firstname, persons.psn_middlename, persons.psn_lastname, persons.psn_birthdate
HAVING (count(persons.psn_pcode) > 1)
ORDER BY (count(persons.psn_pcode)) DESC, persons.psn_firstname;


-- Вьюха для выборки оценок в бланк диплома
CREATE OR REPLACE VIEW public.fmarkview AS
SELECT 1                      AS fmv_id,
       fmarks.fmk_crdcode     AS fmv_crdcode,
       sum(fmarks.fmk_mark)   AS fmv_total,
       count(fmarks.fmk_mark) AS fmv_count
FROM public.fmarks
    WHERE ((fmarks.fmk_mark IS NOT NULL) AND (fmarks.fmk_mark >= 0) AND (fmarks.fmk_mark <= 5))
    GROUP BY fmarks.fmk_crdcode
UNION
SELECT 2                      AS fmv_id,
       cmarks.cmk_crdcode     AS fmv_crdcode,
       sum(cmarks.cmk_mark)   AS fmv_total,
       count(cmarks.cmk_mark) AS fmv_count
FROM public.cmarks
    WHERE ((cmarks.cmk_mark IS NOT NULL) AND (cmarks.cmk_mark >= 0) AND (cmarks.cmk_mark <= 5))
    GROUP BY cmarks.cmk_crdcode
UNION
SELECT 3                       AS fmv_id,
       fpmarks.fpm_crdcode     AS fmv_crdcode,
       sum(fpmarks.fpm_mark)   AS fmv_total,
       count(fpmarks.fpm_mark) AS fmv_count
FROM public.fpmarks
    WHERE ((fpmarks.fpm_mark IS NOT NULL) AND (fpmarks.fpm_mark >= 0) AND (fpmarks.fpm_mark <= 5))
    GROUP BY fpmarks.fpm_crdcode
UNION
SELECT 4                      AS fmv_id,
       gmarks.gmk_crdcode     AS fmv_crdcode,
       sum(gmarks.gmk_mark)   AS fmv_total,
       count(gmarks.gmk_mark) AS fmv_count
FROM public.gmarks
    WHERE ((gmarks.gmk_mark IS NOT NULL) AND (gmarks.gmk_mark >= 0) AND (gmarks.gmk_mark <= 5))
    GROUP BY gmarks.gmk_crdcode;


-- Функция для удаления старых оценок по группе
CREATE OR REPLACE FUNCTION public.clearoldgroupmarks(groupid integer) RETURNS void LANGUAGE sql
AS $$
            DELETE FROM fmarks
            WHERE fmk_subcode NOT IN (
                SELECT sub_pcode FROM subjects, groups
                    WHERE (sub_plncode = grp_plncode) AND (grp_pcode = groupId)
            )
            AND fmk_crdcode IN (
                SELECT crd_pcode FROM cards WHERE (crd_grpcode = groupId)
            );

            DELETE FROM fmarks
            WHERE fmk_modcode NOT IN (
                SELECT mod_pcode FROM modules, groups
                    WHERE (mod_plncode = grp_plncode) AND (grp_pcode = groupId)
            )
            AND fmk_crdcode IN (
                SELECT crd_pcode FROM cards WHERE (crd_grpcode = groupId)
            );

            DELETE FROM cmarks
            WHERE cmk_subcode NOT IN (
                SELECT sub_pcode FROM subjects, groups
                    WHERE (sub_plncode = grp_plncode) AND (grp_pcode = groupId)
            )
            AND cmk_crdcode IN (
                SELECT crd_pcode FROM cards WHERE (crd_grpcode = groupId)
            );

            DELETE FROM fpmarks
            WHERE fpm_fpccode NOT IN (
                SELECT fpc_pcode FROM finalpractics, groups
                    WHERE (fpc_plncode = grp_plncode) AND (grp_pcode = groupId)
            )
            AND fpm_crdcode IN (
                SELECT crd_pcode FROM cards WHERE (crd_grpcode = groupId)
            );

            DELETE FROM gmarks
            WHERE gmk_subcode NOT IN (
                SELECT sub_pcode FROM subjects, groups
                    WHERE (sub_plncode = grp_plncode) AND (grp_pcode = groupId)
            )
            AND gmk_crdcode IN (
                SELECT crd_pcode FROM cards WHERE (crd_grpcode = groupId)
            );

            DELETE FROM pmarks WHERE pmk_prccode NOT IN (
                SELECT prc_pcode FROM practics, groups
                    WHERE (prc_plncode = grp_plncode) AND (grp_pcode = groupId)
            )
            AND pmk_crdcode IN (
                SELECT crd_pcode FROM cards WHERE (crd_grpcode = groupId)
            );

            DELETE FROM monthmarks
            WHERE mmk_subcode NOT IN (
                SELECT sub_pcode FROM subjects, groups
                    WHERE (sub_plncode = grp_plncode) AND (grp_pcode = groupId)
            )
            AND mmk_crdcode IN (
                SELECT crd_pcode FROM cards WHERE (crd_grpcode = groupId)
            );
$$;


-- Функция, которая подсчитывает количество оценок у персоны
CREATE OR REPLACE FUNCTION public.countmarks(personid integer) RETURNS bigint LANGUAGE sql
AS $$
            SELECT (SELECT COUNT(*) FROM monthmarks WHERE mmk_psncode = personId) +
            (SELECT COUNT(*) FROM fmarks, persons, cards WHERE (crd_pcode = fmk_crdcode) AND (psn_pcode = crd_psncode) AND (psn_pcode = personId));
$$;

-- Функция, которая возвращает средний балл
CREATE OR REPLACE FUNCTION public.getaveragemark(cardid integer) RETURNS numeric LANGUAGE sql
AS $$
            SELECT CAST(CAST(SUM(fmv_total) AS decimal(5,2)) / SUM(fmv_count) AS decimal(3,2)) FROM fmarkview 
            WHERE (fmv_crdcode = cardId);
$$;

-- Функция, которая определяет "вес" специальности. Чем больше данных по специальности, тем она важнее
CREATE OR REPLACE FUNCTION public.getspecialityweight(integer) RETURNS bigint LANGUAGE sql
AS $_$
            SELECT (SELECT COUNT(*) FROM departmentprofiles WHERE (dpr_spccode = $1)) +
            (SELECT COUNT(*) FROM cards WHERE (crd_spccode = $1)) +
            (SELECT COUNT(*) FROM groups WHERE (grp_spccode = $1)) +
            (SELECT COUNT(*) FROM plans WHERE (pln_spccode = $1)) +
            (SELECT COUNT(*) FROM requests WHERE (req_spccode = $1)) +
            (SELECT COUNT(*) FROM seats WHERE (sea_spccode = $1));
$_$;

-- Функция, которая переносит данные из одной специальности в другую
CREATE OR REPLACE FUNCTION public.movespeciality(src integer, dst integer) RETURNS void LANGUAGE sql
AS $_$
            UPDATE cards SET crd_spccode = $2 WHERE (crd_spccode = $1);

            UPDATE departmentprofiles SET dpr_spccode = $2 WHERE (dpr_spccode = $1);

            UPDATE groups SET grp_spccode = $2 WHERE (grp_spccode = $1);

            UPDATE plans SET pln_spccode = $2 WHERE (pln_spccode = $1);

            DELETE FROM specialities WHERE (spc_pcode = $1);
$_$;

-- Функция, которая заменяет одну специальность на другую
CREATE OR REPLACE FUNCTION public.replacespeciality(integer, integer) RETURNS void LANGUAGE sql
AS $_$
            UPDATE plans SET pln_spccode = $2 WHERE (pln_spccode = $1);
            UPDATE groups SET grp_spccode = $2 WHERE (grp_spccode = $1);
            UPDATE cards SET crd_spccode = $2 WHERE (crd_spccode = $1);
            DELETE FROM departmentprofiles WHERE (dpr_spccode = $1);
            DELETE FROM requests WHERE (req_spccode = $1);
            DELETE FROM seats WHERE (sea_spccode = $1);
            DELETE FROM specialities WHERE (spc_pcode = $1);
$_$;
