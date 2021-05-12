package com.example.uhf_bt.entidades;

public class RFIDTagList {
    private String id;
    private String EPC;
    private String TID;
    private String UserMemory;
    private String AntennaName;
    private String PeakRSSI;
    private String DateTime;
    private String ReaderName;
    private String StartEvent;
    private String Count;
    private String TagEvent;
    private String Direction;


    public RFIDTagList(String id, String EPC, String TID,String UserMemory, String AntennaName,String PeakRSSI, String DateTime, String ReaderName, String StartEvent, String Count, String TagEvent,String Direction) {
        this.id = id;
        this.EPC = EPC;
        this.TID=TID;
        this.UserMemory=UserMemory;
        this.AntennaName=AntennaName;
        this.PeakRSSI=PeakRSSI;
        this.DateTime=DateTime;
        this.ReaderName=ReaderName;
        this.StartEvent=StartEvent;
        this.Count=Count;
        this.TagEvent=TagEvent;
        this.Direction=Direction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEPC() {
        return EPC;
    }

    public void setEPC(String EPC) {
        this.EPC = EPC;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getUserMemory() {
        return UserMemory;
    }

    public void setUserMemory(String userMemory) {
        UserMemory = userMemory;
    }

    public String getAntennaName() {
        return AntennaName;
    }

    public void setAntennaName(String antennaName) {
        AntennaName = antennaName;
    }

    public String getPeakRSSI() {
        return PeakRSSI;
    }

    public void setPeakRSSI(String peakRSSI) {
        PeakRSSI = peakRSSI;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getReaderName() {
        return ReaderName;
    }

    public void setReaderName(String readerName) {
        ReaderName = readerName;
    }

    public String getStartEvent() {
        return StartEvent;
    }

    public void setStartEvent(String startEvent) {
        StartEvent = startEvent;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getTagEvent() {
        return TagEvent;
    }

    public void setTagEvent(String tagEvent) {
        TagEvent = tagEvent;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }
    public RFIDTagList(){

    }
}
