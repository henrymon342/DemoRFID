package com.example.uhf_bt.Utilidades;

public class utilidades {
    public static final String TABLA_USUARIO = "user";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "name";
    public static final String CAMPO_EMAIL = "email";
    public static final String CAMPO_PASSWORD = "password";

    public static final String TABLA_BUILDING = "building";
    public static final String CAMPO_ID_BUILDING = "idbuilding";
    public static final String CAMPO_NOMBRE_BUILDING = "nombre";

    public static final String TABLA_ROOM = "room";
    public static final String CAMPO_ID_ROOM = "idroom";
    //public static final String CAMPO_ID_BUILDING = "idbuilding";
    public static final String CAMPO_NOMBRE_ROOM = "nombre";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "(" + CAMPO_ID + " INTEGER, " + CAMPO_NOMBRE + " TEXT, " + CAMPO_EMAIL + " TEXT, " + CAMPO_PASSWORD + " TEXT)";
    public static final String CREAR_TABLA_BUILDING = "CREATE TABLE " + TABLA_BUILDING + "(" + CAMPO_ID_BUILDING + " INTEGER, " + CAMPO_NOMBRE_BUILDING + " TEXT)";
    public static final String CREAR_TABLA_ROOM = "CREATE TABLE " + TABLA_ROOM + "(" + CAMPO_ID_ROOM +" INTERGER PRIMARY KEY AUTOINCREMENT, " + " INTEGER, " + CAMPO_NOMBRE_ROOM + " TEXT, " + CAMPO_ID_BUILDING + " INTEGER)" ;
    public static final String VACIAR_TABLA_USUARIO = "DELETE FROM " + TABLA_USUARIO + "; UPDATE sqlite_sequence set seq=0 where name=" + TABLA_USUARIO;
    public static final String USUARIO_X_ID = "SELECT * FROM "+ TABLA_USUARIO + "WHERE "+CAMPO_ID+"= ?";
}
