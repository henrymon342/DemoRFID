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
    public static final String CAMPO_USER_MEMORY = "memory";
    public static final String CAMPO_ANTENNA_NAME = "antenna";
    public static final String CAMPO_PEAK_RSSI = "rssi";
    public static final String CAMPO_DATE_TIME = "time";
    public static final String CAMPO_READER_NAME = "reader";
    public static final String CAMPO_START_EVENT = "event";
    public static final String CAMPO_COUNT = "count";
    public static final String CAMPO_TAG_EVENT = "tag";
    public static final String CAMPO_DIRECTION = "direction";
    public static final String CAMPO_FID_INVENTARIO = "idInventario";

    // UBICACION
    public static final String TABLA_UBICACION= "ubicacion";
    public static final String CAMPO_ID_UBICACION = "id";
    public static final String CAMPO_EDIFICIO = "edificio";
    public static final String CAMPO_ROOM = "room";

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
            + CAMPO_USER_MEMORY + " TEXT, "
            + CAMPO_ANTENNA_NAME + " TEXT, "
            + CAMPO_PEAK_RSSI + " TEXT, "
            + CAMPO_DATE_TIME + " TEXT, "
            + CAMPO_READER_NAME + " TEXT, "
            + CAMPO_START_EVENT + " TEXT, "
            + CAMPO_COUNT + " TEXT, "
            + CAMPO_TAG_EVENT + " TEXT, "
            + CAMPO_DIRECTION + " TEXT, "
            + CAMPO_FID_INVENTARIO + " INTEGER, "
            + "FOREIGN KEY ("+CAMPO_FID_INVENTARIO+") REFERENCES "+TABLA_INVENTARIO+" ("+CAMPO_ID_INVENTARIO+")" ;

    // CREAR UBICACION
    public static final String CREAR_TABLA_UBICACION = "CREATE TABLE " + TABLA_UBICACION + "("
            + CAMPO_ID_UBICACION +" INTEGER PRIMARY KEY, "
            + CAMPO_EDIFICIO + " TEXT, "
            + CAMPO_ROOM + " TEXT)" ;

    // CREAR INVENTARIO
    public static final String CREAR_TABLA_INVENTARIO="CREATE TABLE " + TABLA_INVENTARIO + "("
            + CAMPO_ID_INVENTARIO +" INTEGER PRIMARY KEY, "
            + CAMPO_DURACION + " TEXT, "
            + CAMPO_CANTIDAD_TAGS + " TEXT, "
            + CAMPO_FECHA_ESCANEO + " TEXT, "
            + CAMPO_NAME_BUILDING + " TEXT, "
            + CAMPO_NAME_ROOM + " TEXT)" ;

}
