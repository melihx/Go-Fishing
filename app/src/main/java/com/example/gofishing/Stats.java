package com.example.gofishing;

public class Stats {
    protected String ID, Name, DamName, DamPhoneNumber, FishVariety, FishNumber;
    public Stats(){
        ID=""; Name=""; DamName=""; DamPhoneNumber = ""; FishVariety=""; FishNumber = "";
    }
    public Stats(String ID, String Name, String DamName, String DamPhoneNumber, String FishVariety, String FishNumber){
        this.ID = ID; this.Name=Name; this.DamName=DamName; this.DamPhoneNumber = DamPhoneNumber;
        this.FishVariety = FishVariety; this.FishNumber = FishNumber;

    }
    public String getID(){
        return ID;
    }
    public String getName(){
        return Name;
    }
    public String getDamName(){
        return DamName;
    }
    public String getDamPhoneNumber(){return DamPhoneNumber;}
    public String getFishVariety(){
        return FishVariety;
    }
    public String getFishNumber(){ return FishNumber; }
}
