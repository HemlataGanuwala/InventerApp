package com.example.skyserver.skycableweb.model;

public class Product2 {
    private String Mob;
    private String Setupbox;
    private String Cid;

    public Product2(String mob, String setupbox, String cid)
    {
        this.Mob = mob;
        this.Setupbox = setupbox;
        this.Cid = cid;
    }

    public String getMob() {
        return Mob;
    }

    public void setMob(String mob) {
        this.Mob = mob;
    }


    public String getSetupbox() {
        return Setupbox;
    }

    public void setSetupbox(String setupbox) {
        this.Setupbox = setupbox;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        this.Cid = cid;
    }
}
