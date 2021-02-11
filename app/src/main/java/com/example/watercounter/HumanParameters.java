package com.example.watercounter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class HumanParameters implements Serializable {
    public Boolean Sex;
    public Integer Height;
    public Integer Weight;
    public HumanParameters()
    {
        Sex = false;
        Height = 170;
        Weight = 80;
    }
    public HumanParameters(Boolean Sex, Integer Height, Integer Weight)
    {
        this.Sex = Sex;
        this.Height = Height;
        this.Weight = Weight;
    }
    public HumanParameters(byte[] bytes)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            HumanParameters hp = (HumanParameters)o;
            this.Sex = hp.Sex;
            this.Weight = hp.Weight;
            this.Height = hp.Height;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
    }
    public byte[] getBytes()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
            }
        }
        return bos.toByteArray();
    }
}
