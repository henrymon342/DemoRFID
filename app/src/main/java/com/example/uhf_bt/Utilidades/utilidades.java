package com.example.uhf_bt.Utilidades;

public class utilidades {

    // STOCK
    public static final String TABLA_STOCK ="stock";
    public static final String CAMPO_ID_STOCK ="id";
    public static final String CAMPO_EPC_STOCK ="epc";
    public static final String CAMPO_TID_STOCK ="tid";
    public static final String CAMPO_USER_MEMORY_STOCK ="userMemory";
    public static final String CAMPO_DESCRIPCION_STOCK ="descripcion";
    public static final String CAMPO_LAST_SCAN_STOCK ="lastScanDate";
    public static final String CAMPO_FK_ROOM_STOCK ="room_id";

    // ROOM
    public static final String TABLA_ROOM= "room";
    public static final String CAMPO_ID_ROOM = "id";
    public static final String CAMPO_ROOM_NAME = "name";
    public static final String CAMPO_FID_BUILDING = "fid";

    // BUILDING
    public static final String TABLA_BUILDING= "building";
    public static final String CAMPO_ID_BUILDING = "id";
    public static final String CAMPO_BUILDING_NAME = "name";

    // LECTOR
    public static final String TABLA_LECTOR="lector";
    public static final String CAMPO_ID_lECTOR = "id";
    public static final String CAMPO_ALIAS_lECTOR = "alias";
    public static final String CAMPO_MARCA_lECTOR = "marca";
    public static final String CAMPO_MODELO_lECTOR = "modelo";
    public static final String CAMPO_DESCRIPCION_lECTOR = "descripcion";
    public static final String CAMPO_MAC_lECTOR = "mac_addr";

    // ASIGNACION LECTOR
    public static final String TABLA_ASIGNACION="asignacion_lector";
    public static final String CAMPO_ID_ASIGNACION = "id";
    public static final String CAMPO_FECHA_INI_ASIGNACION = "fecha_ini";
    public static final String CAMPO_FECHA_FIN_ASIGNACION = "fecha_fin";
    public static final String CAMPO_FK_USUARIO_ASIGNACION = "id_usuario";
    public static final String CAMPO_FK_LECTOR_ASIGNACION = "id_lector";

    // USUARIO
    public static final String TABLA_USUARIO = "user";
    public static final String CAMPO_ID_USER = "id";
    public static final String CAMPO_NOMBRE = "name";
    public static final String CAMPO_PASSWORD = "password";

    // SEARCH_LIST
    public static final String TABLA_SEARCH_LIST = "searchList";
    public static final String CAMPO_SEARCH_ID = "id";
    public static final String CAMPO_SEARCH_DESCRIPCION = "descripcion";
    public static final String CAMPO_SEARCH_EPC = "epc";
    public static final String CAMPO_SEARCH_ESTADO = "estado";

    /* ---CREAR TABLAS---- */

    // CREAR RFID
    public static final String CREAR_TABLA_STOCK= "CREATE TABLE " + TABLA_STOCK + "("
            + CAMPO_ID_STOCK + " INTEGER PRIMARY KEY, "
            + CAMPO_EPC_STOCK + " TEXT, "
            + CAMPO_TID_STOCK + " TEXT, "
            + CAMPO_USER_MEMORY_STOCK + " TEXT, "
            + CAMPO_DESCRIPCION_STOCK + " TEXT, "
            + CAMPO_LAST_SCAN_STOCK + " TEXT, "
            + CAMPO_FK_ROOM_STOCK + " INTEGER, "
            + "FOREIGN KEY ("+CAMPO_FK_ROOM_STOCK+") REFERENCES "+ TABLA_ROOM +" ("+CAMPO_ID_ROOM+"))" ;

    // CREAR ROOM
    public static final String CREAR_TABLA_ROOM = "CREATE TABLE " + TABLA_ROOM + "("
            + CAMPO_ID_ROOM +" INTEGER PRIMARY KEY, "
            + CAMPO_ROOM_NAME + " TEXT, "
            + CAMPO_FID_BUILDING + " INTEGER, "
            + "FOREIGN KEY ("+CAMPO_FID_BUILDING+") REFERENCES "+TABLA_BUILDING+" ("+CAMPO_ID_BUILDING+"))" ;

    // CREAR BUILDING
    public static final String CREAR_TABLA_BUILDING = "CREATE TABLE " + TABLA_BUILDING + "("
            + CAMPO_ID_BUILDING +" INTEGER PRIMARY KEY, "
            + CAMPO_BUILDING_NAME + " TEXT)" ;

    // CREAR LECTOR
    public static final String CREAR_TABLA_LECTOR="CREATE TABLE " + TABLA_LECTOR + "("
            + CAMPO_ID_lECTOR +" INTEGER PRIMARY KEY, "
            + CAMPO_ALIAS_lECTOR + " TEXT, "
            + CAMPO_MARCA_lECTOR + " TEXT, "
            + CAMPO_MODELO_lECTOR + " TEXT, "
            + CAMPO_DESCRIPCION_lECTOR + " TEXT, "
            + CAMPO_MAC_lECTOR+ " TEXT)" ;

    // CREAR ASIGNACION_LECTOR
    public static final String CREAR_TABLA_ASIGNACION_LECTOR="CREATE TABLE " + TABLA_ASIGNACION + "("
            + CAMPO_ID_ASIGNACION +" INTEGER PRIMARY KEY, "
            + CAMPO_FECHA_INI_ASIGNACION + " TEXT, "
            + CAMPO_FECHA_FIN_ASIGNACION + " TEXT, "
            + CAMPO_FK_USUARIO_ASIGNACION + " INTEGER, "
            + CAMPO_FK_LECTOR_ASIGNACION + " INTEGER, "
            + "FOREIGN KEY ("+CAMPO_FK_USUARIO_ASIGNACION+") REFERENCES "+TABLA_USUARIO+" ("+CAMPO_ID_USER+"),"
            + "FOREIGN KEY ("+CAMPO_FK_LECTOR_ASIGNACION+") REFERENCES "+TABLA_LECTOR+" ("+CAMPO_ID_lECTOR+"))" ;

    // CREAR USUARIO
    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + "("
            + CAMPO_ID_USER + " INTEGER PRIMARY KEY, "
            + CAMPO_NOMBRE + " TEXT, "
            + CAMPO_PASSWORD + " TEXT)";

    // CREAR SEARCH
    public static final String CREAR_TABLA_SEARCH_LIST = "CREATE TABLE " + TABLA_SEARCH_LIST + "("
            + CAMPO_SEARCH_ID+ " INTEGER PRIMARY KEY, "
            + CAMPO_SEARCH_DESCRIPCION + " TEXT, "
            + CAMPO_SEARCH_EPC + " TEXT, "
            + CAMPO_SEARCH_ESTADO + " TEXT)";
}
