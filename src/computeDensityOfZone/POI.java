package computeDensityOfZone;

public class POI {

	private String id;
	private double lon;
	private double lat;
	
	public POI(String id, double lon, double lat)
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
		return this.id+" : lon "+this.lon+", lat "+this.lat;
	}

	public void printAll()
	{
		System.out.println("[min_lat: "+PoiManager.roundToHalfDown(this.getLat())+" ; max_lat: "
				+PoiManager.roundToHalfUp(this.getLat())+"; min_lon: "
				+PoiManager.roundToHalfDown(this.getLon())+" ; max_lon: "
				+PoiManager.roundToHalfUp(this.getLon())+"]");
	}
}
