PGDMP      /                |            rentACar    15.7    16.3 '    "           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            #           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            $           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            %           1262    24944    rentACar    DATABASE        CREATE DATABASE "rentACar" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE "rentACar";
                postgres    false            �            1259    25022    book    TABLE     �   CREATE TABLE public.book (
    id integer NOT NULL,
    car_id integer NOT NULL,
    user_id integer NOT NULL,
    price integer NOT NULL,
    start_date date NOT NULL,
    finish_date date NOT NULL,
    note text
);
    DROP TABLE public.book;
       public         heap    postgres    false            �            1259    25021    book_id_seq    SEQUENCE     �   CREATE SEQUENCE public.book_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.book_id_seq;
       public          postgres    false    223            &           0    0    book_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.book_id_seq OWNED BY public.book.id;
          public          postgres    false    222            �            1259    24985    brand    TABLE     a   CREATE TABLE public.brand (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);
    DROP TABLE public.brand;
       public         heap    postgres    false            �            1259    24984    brand_id_seq    SEQUENCE     �   CREATE SEQUENCE public.brand_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.brand_id_seq;
       public          postgres    false    217            '           0    0    brand_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.brand_id_seq OWNED BY public.brand.id;
          public          postgres    false    216            �            1259    25006    car    TABLE     �   CREATE TABLE public.car (
    id integer NOT NULL,
    model_id integer NOT NULL,
    color character varying(100) NOT NULL,
    km integer NOT NULL,
    plate character varying(10) NOT NULL,
    daily_price integer NOT NULL
);
    DROP TABLE public.car;
       public         heap    postgres    false            �            1259    25005 
   car_id_seq    SEQUENCE     �   CREATE SEQUENCE public.car_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.car_id_seq;
       public          postgres    false    221            (           0    0 
   car_id_seq    SEQUENCE OWNED BY     9   ALTER SEQUENCE public.car_id_seq OWNED BY public.car.id;
          public          postgres    false    220            �            1259    24992    model    TABLE       CREATE TABLE public.model (
    id integer NOT NULL,
    brand_id integer NOT NULL,
    name character varying(100) NOT NULL,
    type character varying(100) NOT NULL,
    fuel character varying(50) NOT NULL,
    gear character varying(50) NOT NULL,
    year integer NOT NULL
);
    DROP TABLE public.model;
       public         heap    postgres    false            �            1259    24991    model_id_seq    SEQUENCE     �   CREATE SEQUENCE public.model_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.model_id_seq;
       public          postgres    false    219            )           0    0    model_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.model_id_seq OWNED BY public.model.id;
          public          postgres    false    218            �            1259    24949    user    TABLE     �   CREATE TABLE public."user" (
    id integer NOT NULL,
    password text NOT NULL,
    role character varying(25) NOT NULL,
    name character varying(250) NOT NULL,
    phone character varying(25) NOT NULL,
    email character varying(250) NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    24948    users_id_seq    SEQUENCE     �   CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    215            *           0    0    users_id_seq    SEQUENCE OWNED BY     >   ALTER SEQUENCE public.users_id_seq OWNED BY public."user".id;
          public          postgres    false    214            }           2604    25025    book id    DEFAULT     b   ALTER TABLE ONLY public.book ALTER COLUMN id SET DEFAULT nextval('public.book_id_seq'::regclass);
 6   ALTER TABLE public.book ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    223    223            z           2604    24988    brand id    DEFAULT     d   ALTER TABLE ONLY public.brand ALTER COLUMN id SET DEFAULT nextval('public.brand_id_seq'::regclass);
 7   ALTER TABLE public.brand ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    217    217            |           2604    25009    car id    DEFAULT     `   ALTER TABLE ONLY public.car ALTER COLUMN id SET DEFAULT nextval('public.car_id_seq'::regclass);
 5   ALTER TABLE public.car ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    221    221            {           2604    24995    model id    DEFAULT     d   ALTER TABLE ONLY public.model ALTER COLUMN id SET DEFAULT nextval('public.model_id_seq'::regclass);
 7   ALTER TABLE public.model ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    218    219            y           2604    24952    user id    DEFAULT     e   ALTER TABLE ONLY public."user" ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 8   ALTER TABLE public."user" ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215                      0    25022    book 
   TABLE DATA           Y   COPY public.book (id, car_id, user_id, price, start_date, finish_date, note) FROM stdin;
    public          postgres    false    223   (                 0    24985    brand 
   TABLE DATA           )   COPY public.brand (id, name) FROM stdin;
    public          postgres    false    217   Y(                 0    25006    car 
   TABLE DATA           J   COPY public.car (id, model_id, color, km, plate, daily_price) FROM stdin;
    public          postgres    false    221   �(                 0    24992    model 
   TABLE DATA           K   COPY public.model (id, brand_id, name, type, fuel, gear, year) FROM stdin;
    public          postgres    false    219   ))                 0    24949    user 
   TABLE DATA           H   COPY public."user" (id, password, role, name, phone, email) FROM stdin;
    public          postgres    false    215   �)       +           0    0    book_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.book_id_seq', 2, true);
          public          postgres    false    222            ,           0    0    brand_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.brand_id_seq', 5, true);
          public          postgres    false    216            -           0    0 
   car_id_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('public.car_id_seq', 6, true);
          public          postgres    false    220            .           0    0    model_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.model_id_seq', 8, true);
          public          postgres    false    218            /           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 4, true);
          public          postgres    false    214            �           2606    25029    book book_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.book DROP CONSTRAINT book_pkey;
       public            postgres    false    223            �           2606    24990    brand brand_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.brand
    ADD CONSTRAINT brand_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.brand DROP CONSTRAINT brand_pkey;
       public            postgres    false    217            �           2606    25011    car car_pkey 
   CONSTRAINT     J   ALTER TABLE ONLY public.car
    ADD CONSTRAINT car_pkey PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.car DROP CONSTRAINT car_pkey;
       public            postgres    false    221            �           2606    24997    model model_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.model
    ADD CONSTRAINT model_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.model DROP CONSTRAINT model_pkey;
       public            postgres    false    219                       2606    24956    user users_pkey 
   CONSTRAINT     O   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 ;   ALTER TABLE ONLY public."user" DROP CONSTRAINT users_pkey;
       public            postgres    false    215               >   x�3�4�4�44����4202�50�50�3M9SR�RsS��8MA
����&�La� Ͳ         %   x�3��urvuq�2�t��2��/H������ b 8         �   x�-�K
1��u�}�J�;�e���A�qW�Q?Al��TJ]�*�ޗY�H8o��a:M��rzKa��!��}x�/�d'b���>��<��=�����r���JY��:ԑ�\�f��Rwq��<�s!|�C"�         �   x�}ν
�@�z�a�]�̕�ˑH.'D���/�OD�SL�13�	:Ј�vmh��E���ڒʕ�mb�BM�y�I�y���t�[HΖ̢�}N�1n��55�+H/��w��q���v����eD���7�         c   x�3�LL��̃�9��
�ŉ9���ƆFf��&i0�����e�ihdlbjƙ����Z�雚��Z�ZU��g�AΔԼ��T����� H�"�     