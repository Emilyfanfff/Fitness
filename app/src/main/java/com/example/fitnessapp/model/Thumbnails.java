package com.example.fitnessapp.model;

public class Thumbnails {
    private Standard standard;

    private Default d;

    private High high;

    private Maxres maxres;

    private Medium medium;

    public Standard getStandard ()
    {
        return standard;
    }

    public void setStandard (Standard standard)
    {
        this.standard = standard;
    }

    public Default getDefault ()
    {
        return d;
    }

    public void setDefault (Default d)
    {
        this.d = d;
    }

    public High getHigh ()
    {
        return high;
    }

    public void setHigh (High high)
    {
        this.high = high;
    }

    public Maxres getMaxres ()
    {
        return maxres;
    }

    public void setMaxres (Maxres maxres)
    {
        this.maxres = maxres;
    }

    public Medium getMedium ()
    {
        return medium;
    }

    public void setMedium (Medium medium)
    {
        this.medium = medium;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [standard = "+standard+", default = "+d+", high = "+high+", maxres = "+maxres+", medium = "+medium+"]";
    }
}
