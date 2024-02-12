package com.example.fitnessapp.model;

public class ContentDetails {
    private String duration;

    private String licensedContent;

    private String caption;

    private String definition;

    private String contentRating;

    private String projection;

    private String dimension;

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getLicensedContent ()
    {
        return licensedContent;
    }

    public void setLicensedContent (String licensedContent)
    {
        this.licensedContent = licensedContent;
    }

    public String getCaption ()
    {
        return caption;
    }

    public void setCaption (String caption)
    {
        this.caption = caption;
    }

    public String getDefinition ()
    {
        return definition;
    }

    public void setDefinition (String definition)
    {
        this.definition = definition;
    }

    public String getContentRating ()
    {
        return contentRating;
    }

    public void setContentRating (String contentRating)
    {
        this.contentRating = contentRating;
    }

    public String getProjection ()
    {
        return projection;
    }

    public void setProjection (String projection)
    {
        this.projection = projection;
    }

    public String getDimension ()
    {
        return dimension;
    }

    public void setDimension (String dimension)
    {
        this.dimension = dimension;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [duration = "+duration+", licensedContent = "+licensedContent+", caption = "+caption+", definition = "+definition+", contentRating = "+contentRating+", projection = "+projection+", dimension = "+dimension+"]";
    }
}
