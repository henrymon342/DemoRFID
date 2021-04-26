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

    // UBICACION
    public static final String TABLA_UBICACION= "ubicacion";
    public static final String CAMPO_ID_UBICACION = "id";
    public static final String CAMPO_EDIFICIO = "edificio";
    public static final String CAMPO_ROOM = "room";


    // CREAR USUARIO
    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "("
            + CAMPO_ID_USER + " INTEGER PRIMARY KEY, "
            + CAMPO_NOMBRE + " TEXT, "
            + CAMPO_PASSWORD + " TEXT)";

    // CREAR RFID
    public static final String CREAR_TABLA_RFID = "CREATE TABLE " + TABLA_RFID_TAG_LIST + "("
            + CAMPO_ID_RFID + " INTEGER PRIMARY KEY, "
            + CAMPO_TID + " TEXT, "
            + CAMPO_USER_MEMORY + " TEXT, "
            + CAMPO_ANTENNA_NAME + " TEXT, "
            + CAMPO_PEAK_RSSI + " TEXT, "
            + CAMPO_DATE_TIME + " TEXT, "
            + CAMPO_READER_NAME + " TEXT, "
            + CAMPO_START_EVENT + " TEXT, "
            + CAMPO_COUNT + " TEXT, "
            + CAMPO_TAG_EVENT + " TEXT, "
            + CAMPO_DIRECTION + " TEXT)";

    // CREAR UBICACION
    public static final String CREAR_TABLA_UBICACION = "CREATE TABLE " + TABLA_UBICACION + "("
            + CAMPO_ID_UBICACION +" INTEGER PRIMARY KEY, "
            + CAMPO_EDIFICIO + " TEXT, "
            + CAMPO_ROOM + " TEXT)" ;

}
