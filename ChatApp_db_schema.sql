PGDMP                         z           newDatabase    14.3    14.3     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16385    newDatabase    DATABASE     X   CREATE DATABASE "newDatabase" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'C';
    DROP DATABASE "newDatabase";
                newuser    false            �            1259    16396    Login    TABLE     m   CREATE TABLE public."Login" (
    name character varying(50) NOT NULL,
    password character varying(50)
);
    DROP TABLE public."Login";
       public         heap    newuser    false            �          0    16396    Login 
   TABLE DATA           1   COPY public."Login" (name, password) FROM stdin;
    public          newuser    false    209          i           2606    16400    Login Login_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public."Login"
    ADD CONSTRAINT "Login_pkey" PRIMARY KEY (name);
 >   ALTER TABLE ONLY public."Login" DROP CONSTRAINT "Login_pkey";
       public            newuser    false    209            �      x�KI��442��JL�3J�s�t� vm�     