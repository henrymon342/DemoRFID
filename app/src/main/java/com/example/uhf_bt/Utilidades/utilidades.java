package com.example.uhf_bt.Utilidades;

public class utilidades {
    // USUARIO
    public static final String TABLA_USUARIO = "user";
    public static final String CAMPO_ID_USER = "id";
    public static final String CAMPO_NOMBRE = "name";
    public static final String CAMPO_PASSWORD = "password";

    // RFID
    public static final String TABLA_RFID_TAG_LIST = "rfid";
    public static final String CAMPO_ID_RFID = "id";
    public static final String CAMPO_EPC = "epc";
    public static final String CAMPO_TID = "tid";
    public static final String CAMPO_COUNT = "count";
    public static final String CAMPO_DESCRIPCION = "descripcion";
    public static final String CAMPO_FID_INVENTARIO = "idInventario";


    // ROOM
    public static final String TABLA_ROOM= "room";
    public static final String CAMPO_ID_ROOM = "id";
    public static final String CAMPO_ROOM_NAME = "name";
    public static final String CAMPO_FID_BUILDING = "fid";

    // BUILDING
    public static final String TABLA_BUILDING= "building";
    public static final String CAMPO_ID_BUILDING = "id";
    public static final String CAMPO_BUILDING_NAME = "name";


    // INVENTARIO
    public static final String TABLA_INVENTARIO="inventario";
    public static final String CAMPO_ID_INVENTARIO="id";
    public static final String CAMPO_DURACION="duracion";
    public static final String CAMPO_CANTIDAD_TAGS="cantidad_tags";
    public static final String CAMPO_FECHA_ESCANEO="fecha_escaneo";
    public static final String CAMPO_NAME_BUILDING="building";
    public static final String CAMPO_NAME_ROOM="room";


    // CREAR USUARIO
    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "("
            + CAMPO_ID_USER + " INTEGER PRIMARY KEY, "
            + CAMPO_NOMBRE + " TEXT, "
            + CAMPO_PASSWORD + " TEXT)";

    // CREAR RFID
    public static final String CREAR_TABLA_RFID = "CREATE TABLE " + TABLA_RFID_TAG_LIST + "("
            + CAMPO_ID_RFID + " INTEGER PRIMARY KEY, "
            + CAMPO_EPC + " TEXT, "
            + CAMPO_TID + " TEXT, "
            + CAMPO_COUNT + " TEXT, "
            + CAMPO_DESCRIPCION + " TEXT, "
            + CAMPO_FID_INVENTARIO + " INTEGER, "
            + "FOREIGN KEY ("+CAMPO_FID_INVENTARIO+") REFERENCES "+TABLA_INVENTARIO+" ("+CAMPO_ID_INVENTARIO+"))" ;


    // CREAR ROOM
    public static final String CREAR_TABLA_ROOM = "CREATE TABLE " + TABLA_ROOM + "("
            + CAMPO_ID_ROOM +" INTEGER PRIMARY KEY, "
            + CAMPO_ROOM_NAME + " TEXT, "
            + CAMPO_FID_BUILDING + " INTEGER, "
            + "FOREIGN KEY ("+CAMPO_FID_BUILDING+") REFERENCES "+TABLA_BUILDING+" ("+CAMPO_ID_BUILDING+"))" ;;

    // CREAR BUILDING
    public static final String CREAR_TABLA_BUILDING = "CREATE TABLE " + TABLA_BUILDING + "("
            + CAMPO_ID_BUILDING +" INTEGER PRIMARY KEY, "
            + CAMPO_BUILDING_NAME + " TEXT)" ;

    // CREAR INVENTARIO
    public static final String CREAR_TABLA_INVENTARIO="CREATE TABLE " + TABLA_INVENTARIO + "("
            + CAMPO_ID_INVENTARIO +" INTEGER PRIMARY KEY, "
            + CAMPO_DURACION + " TEXT, "
            + CAMPO_CANTIDAD_TAGS + " TEXT, "
            + CAMPO_FECHA_ESCANEO + " TEXT, "
            + CAMPO_NAME_BUILDING + " TEXT, "
            + CAMPO_NAME_ROOM + " TEXT)" ;

}
