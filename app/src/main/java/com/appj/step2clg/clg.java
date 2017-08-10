package com.appj.step2clg;

import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by Parag Patil on 21-04-2017.
 */

public class clg {
    public String clg_name,clg_address,clg_logo,clg_image,clg_vision,clg_url,clg_location;
    public Integer cs,it,ec,electronics,edt,civil,mech,electrical,instrumentation,industrial;

    public String getclg_name(){
        return this.clg_name;
    }
    public String getclg_address(){
        return this.clg_address;
    }
    public String getclg_logo(){
        return this.clg_logo;
    }
    public String getclg_image(){
        return this.clg_image;
    }
    public String getclg_vision(){
        return this.clg_vision;
    }
    public String getclg_url(){return this.clg_url;}
    public String getclg_location(){return this.clg_location;}

    public Integer getcs(){return this.cs;}
    public Integer getit(){return this.it;}
    public Integer getec(){
        return this.ec;
    }
    public Integer getelectronics(){
        return this.electronics;
    }
    public Integer getedt(){
        return this.edt;
    }
    public Integer getcivil(){
        return this.civil;
    }
    public Integer getmech(){return this.mech;}
    public Integer getelectrical(){
        return this.electrical;
    }
    public Integer getinstrumentation(){
        return this.instrumentation;
    }
    public Integer getindustrial(){
        return this.industrial;
    }

    public clg()
    {

    }
    public clg(String clg_name,String clg_address,String clg_logo,String clg_image,String clg_vision,String clg_url,Integer cs,Integer ec,Integer electronics,Integer it,Integer edt,Integer civil,Integer mech,Integer industrial,Integer instrumentation,Integer electrical,String clg_location)
    {
        this.clg_name=clg_name;
        this.clg_address=clg_address;
        this.clg_logo=clg_logo;
        this.clg_image=clg_image;
        this.clg_vision=clg_vision;
        this.cs=cs;
        this.it=it;
        this.ec=ec;
        this.electronics=electronics;
        this.civil=civil;
        this.mech=mech;
        this.industrial=industrial;
        this.instrumentation=instrumentation;
        this.electrical=electrical;
        this.edt=edt;
        this.clg_url=clg_url;
        this.clg_location=clg_location;

    }
}
