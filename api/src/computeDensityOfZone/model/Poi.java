package computeDensityOfZone.model;

import computeDensityOfZone.service.PoiManager;

public class Poi
{

    public final static double step = 0.5;
    private String id;
    private double lon;
    private double lat;

    public Poi(String id, double lon, double lat)
    {
        this.setId(id);
        this.setLon(lon);
        this.setLat(lat);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public double getLon()
    {
        return lon;
    }

    public void setLon(double lon)
    {
        this.lon = lon;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public String toString()
    {
        return this.id + " : lon " + this.lon + ", lat " + this.lat;
    }

    public void printAll()
    {
        System.out.println("[min_lat: " + PoiManager.roundToHalfDown(this.getLat()) + " ; max_lat: "
                + PoiManager.roundToHalfUp(this.getLat()) + "; min_lon: "
                + PoiManager.roundToHalfDown(this.getLon()) + " ; max_lon: "
                + PoiManager.roundToHalfUp(this.getLon()) + "]");
    }

    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }
        if (!(o instanceof Poi))
        {
            return false;
        }
        Poi poi = (Poi) o;
        return poi.getLat() == this.getLat() && poi.getLon() == this.getLon();
    }

    public int hashCode()
    {
        int result = Double.hashCode(lat);
        result = 31 * result + Double.hashCode(lon);
        return result;
    }

    /**
     * round edges of this element
     * Warning : this changes your element
     *
     * @return
     */
    public Poi ToArea()
    {
        this.lat = PoiManager.roundToHalfDown(this.lat);
        this.lon = PoiManager.roundToHalfDown(this.lon);
        return this;
    }
}
