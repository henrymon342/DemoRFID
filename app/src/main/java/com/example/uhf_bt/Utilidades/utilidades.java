package com.example.uhf_bt.Utilidades;

public class utilidades {

    // STOCK
    public static final String TABLA_STOCK = "stock";
    public static final String STOCK_ID = "id";
    public static final String STOCK_EPC = "epc";
    public static final String STOCK_TID = "tid";
    public static final String STOCK_USER_MEMORY = "userMemory";
    public static final String STOCK_DESCRIPTION = "description";
    public static final String STOCK_LAST_SCAN = "lastScanDate";
    public static final String STOCK_FK_ROOM = "room_id";

    // ROOM
    public static final String TABLA_ROOM = "room";
    public static final String ROOM_ID = "id";
    public static final String ROOM_NAME = "name";
    public static final String ROOM_FK_BUILDING = "building_id";

    // BUILDING
    public static final String TABLA_BUILDING = "building";
    public static final String BUILDING_ID = "id";
    public static final String BUILDING_NAME = "name";

    // LECTOR
    public static final String TABLA_LECTOR = "lector";
    public static final String LECTOR_ID = "id";
    public static final String LECTOR_ALIAS = "alias";
    public static final String LECTOR_MARCA = "marca";
    public static final String LECTOR_MODELO = "modelo";
    public static final String LECTOR_DESCRIPTION = "description";
    public static final String LECTOR_MAC = "mac";
    public static final String LECTOR_NET_ID = "id_NET";

    // ASSIGNATION LECTOR
    public static final String TABLA_ASSIGNATION_LECTOR = "assignation_lector";
    public static final String ASSIGNATION_ID = "id";
    public static final String ASSIGNATION_FECHA_INI = "fecha_ini";
    public static final String ASSIGNATION_FECHA_FIN = "fecha_fin";
    public static final String ASSIGNATION_FK_USUARIO = "usuario_id";
    public static final String ASSIGNATION_FK_LECTOR = "lector_id";

    // USUARIO
    public static final String TABLA_USER = "user";
    public static final String USER_ID = "id";
    public static final String USER_NOMBRE = "name";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NET_ID = "id_NET";

    // SEARCH_LIST
    public static final String TABLA_SEARCH_LIST = "search_list";
    public static final String SEARCH_LIST_ID = "id";
    public static final String SEARCH_LIST_DESCRIPTION = "description";
    public static final String SEARCH_LIST_EPC = "epc";
    public static final String SEARCH_LIST_ESTADO = "estado";

    /* ---CREAR TABLAS---- */

    // CREAR RFID
    public static final String CREAR_TABLA_STOCK = "CREATE TABLE " + TABLA_STOCK + "("
            + STOCK_ID + " INTEGER PRIMARY KEY, "
            + STOCK_EPC + " TEXT, "
            + STOCK_TID + " TEXT, "
            + STOCK_USER_MEMORY + " TEXT, "
            + STOCK_DESCRIPTION + " TEXT, "
            + STOCK_LAST_SCAN + " TEXT, "
            + STOCK_FK_ROOM + " INTEGER, "
            + "FOREIGN KEY (" + STOCK_FK_ROOM + ") REFERENCES " + TABLA_ROOM + " (" + ROOM_ID + "))";

    // CREAR ROOM
    public static final String CREAR_TABLA_ROOM = "CREATE TABLE " + TABLA_ROOM + "("
            + ROOM_ID + " INTEGER PRIMARY KEY, "
            + ROOM_NAME + " TEXT, "
            + ROOM_FK_BUILDING + " INTEGER, "
            + "FOREIGN KEY (" + ROOM_FK_BUILDING + ") REFERENCES " + TABLA_BUILDING + " (" + BUILDING_ID + "))";

    // CREAR BUILDING
    public static final String CREAR_TABLA_BUILDING = "CREATE TABLE " + TABLA_BUILDING + "("
            + BUILDING_ID + " INTEGER PRIMARY KEY, "
            + BUILDING_NAME + " TEXT)";

    // CREAR LECTOR
    public static final String CREAR_TABLA_LECTOR = "CREATE TABLE " + TABLA_LECTOR + "("
            + LECTOR_ID + " INTEGER PRIMARY KEY, "
            + LECTOR_ALIAS + " TEXT, "
            + LECTOR_MARCA + " TEXT, "
            + LECTOR_MODELO + " TEXT, "
            + LECTOR_DESCRIPTION + " TEXT, "
            + LECTOR_MAC + " TEXT, "
            + LECTOR_NET_ID + " INTEGER)";

    // CREAR ASSIGNATION_LECTOR
    public static final String CREAR_TABLA_ASSIGNATION_LECTOR = "CREATE TABLE " + TABLA_ASSIGNATION_LECTOR + "("
            + ASSIGNATION_ID + " INTEGER PRIMARY KEY, "
            + ASSIGNATION_FECHA_INI + " TEXT, "
            + ASSIGNATION_FECHA_FIN + " TEXT, "
            + ASSIGNATION_FK_USUARIO + " INTEGER, "
            + ASSIGNATION_FK_LECTOR + " INTEGER, "
            + "FOREIGN KEY (" + ASSIGNATION_FK_USUARIO + ") REFERENCES " + TABLA_USER + " (" + USER_ID + "),"
            + "FOREIGN KEY (" + ASSIGNATION_FK_LECTOR + ") REFERENCES " + TABLA_LECTOR + " (" + LECTOR_ID + "))";

    // CREAR USUARIO
    public static final String CREAR_TABLA_USER = "CREATE TABLE " + TABLA_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY, "
            + USER_NOMBRE + " TEXT, "
            + USER_PASSWORD + " TEXT, "
            + USER_NET_ID + " INTEGER)";

    // CREAR SEARCH
    public static final String CREAR_TABLA_SEARCH_LIST = "CREATE TABLE " + TABLA_SEARCH_LIST + "("
            + SEARCH_LIST_ID + " INTEGER PRIMARY KEY, "
            + SEARCH_LIST_DESCRIPTION + " TEXT, "
            + SEARCH_LIST_EPC + " TEXT, "
            + SEARCH_LIST_ESTADO + " TEXT)";
}
