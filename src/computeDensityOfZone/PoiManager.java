package computeDensityOfZone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.awt.Point;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DistanceType
{
	public enum PositionType {
		NE,
		SE,
		NW,
		SW
	}

	private double distance;
	private PositionType positionType;

	public DistanceType(double distance, PositionType positionType)
	{
		this.distance=distance;
		this.positionType=positionType;
	}

	public double getDistance()
	{
		return this.distance;
	}

	public PositionType getPositionType()
	{
		return this.positionType;
	}
}

class Area
{
	private double minLat;
	private double maxLat;
	private double minLon;
	private double maxLon;

	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public double getMinLon() {
		return minLon;
	}

	public void setMinLon(double minLon) {
		this.minLon = minLon;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}

	public Area(POI poi)
	{
		this.minLat=poi.getLat();
		this.maxLat=poi.getLat();
		this.minLon=poi.getLon();
		this.maxLon=poi.getLon();
	}

	public String toString()
	{
		return "[min_lat: "+this.getMinLat()+"; max_lat: "+this.getMaxLat()+"; min_lon: "+this.getMinLon()+"; max_lon: "+this.getMaxLon()+"]";
	}

	private class AreaLength
	{
		private double latRange;
		private double lonRange;

		public AreaLength(Area area1, Area area2)
		{
			this.latRange=Math.abs(area1.getMinLat());
			this.latRange=this.lonRange=0;
		}
	}

	public DistanceType getDistance(Area area)
	{
		double distance=Math.pow(area.getMinLon()-this.getMaxLon(),2)+Math.pow(area.getMinLat()-this.getMaxLat(),2);
		DistanceType.PositionType positionType=DistanceType.PositionType.NE;

		double newDistance=Math.pow(area.getMinLon()-this.getMaxLon(),2)+Math.pow(this.getMinLat()-area.getMaxLat(),2);
		if(newDistance<distance)
		{
			distance=newDistance;
			positionType=DistanceType.PositionType.SE;
		}

		newDistance=Math.pow(this.getMinLon()-area.getMaxLon(),2)+Math.pow(area.getMinLat()-this.getMaxLat(),2);
		if(newDistance<distance)
		{
			distance=newDistance;
			positionType=DistanceType.PositionType.NW;
		}

		newDistance=Math.pow(this.getMinLon()-area.getMaxLon(),2)+Math.pow(this.getMinLat()-area.getMaxLat(),2);
		if(newDistance<distance)
		{
			distance=newDistance;
			positionType=DistanceType.PositionType.SW;
		}

		return new DistanceType(Math.sqrt(distance),positionType);
	}
}

public class PoiManager {
	
	private List<POI> pois;
	final double step=0.5;
	Point minBoundary;
	Point maxBoundary;
	
	public PoiManager(List<POI> pois)
	{
		this.pois=pois;
		this.minBoundary= new Point(-90,-180);
		this.maxBoundary=new Point(90,180);
	}

	private double roundToHalfDown(double number)
	{
		return Math.floor(number*2.0)/2.0;
	}
	private double roundTOHalfUp(double number)
	{
		return roundToHalfDown(number)+0.5;
	}

	int getPoiForArea(double minLat, double minLon)
	{
		double maxLat=minLat+step;
		double maxLon=minLon+step;

		int res=0;

		// TODO : should sort pois and only start when parameters are near and then break;
		for(POI poi : pois)
		{
			if(poi.getLat()>minLat && poi.getLat()<maxLat
				&& poi.getLon()>minLon && poi.getLon()<maxLon)
			{
				res++;
			}
		}

		return res;
	}

	List<Area> findBiggestAreas(int N)
	{
		List<Area> res;

		List<Area> temp=new ArrayList<Area>();
		
		//pois.sort(Comparator.comparing(POI::getLon));
		for(POI poi : pois)
		{
			if(temp.size()<N) // we need to have N areas to be able to compare
			{
				temp.add(new Area(poi));
			}
			else // we have N areas but 2 of them might need to merge because the new one if further away
			{
				// lookup for the closest Area
				DistanceType minDistance=null;
				Area closestArea=null;

				Area lastArea=null;
				DistanceType closestDistance=null;

				for(Area area : temp)
				{
					DistanceType tempDistance=area.getDistance(new Area(poi));
					if(minDistance==null || tempDistance.getDistance()<minDistance.getDistance())
					{
						minDistance=tempDistance;
						closestArea=area;
					}

					if(lastArea!=null)
					{
						DistanceType distanceBetweenAreas=area.getDistance(lastArea);
						if(closestDistance==null || distanceBetweenAreas.getDistance()<closestDistance.getDistance())
						{
							closestDistance=distanceBetweenAreas;
							lastArea=area;
						}
					}
					else
					{
						lastArea=area;
					}
				}

				// need to check whether 2 areas should merge and the new POI should create a new Area
				if(closestDistance.getDistance()<minDistance.getDistance())
				{
					switch(closestDistance.getPositionType())
					{
						case NE:
							closestArea.setMaxLon(lastArea.getMaxLon());
							closestArea.setMaxLat(lastArea.getMaxLat());
							break;
						case SE:
							closestArea.setMaxLon(lastArea.getMaxLon());
							closestArea.setMinLat(lastArea.getMinLat());
							break;
						case NW:
							closestArea.setMinLon(lastArea.getMinLon());
							closestArea.setMaxLat(lastArea.getMaxLat());
							break;
						case SW:
							closestArea.setMinLon(lastArea.getMinLon());
							closestArea.setMinLat(lastArea.getMinLat());
							break;
					}
				}
				else
				{
					switch(minDistance.getPositionType())
					{
						case NE:
							closestArea.setMaxLon(poi.getLon());
							closestArea.setMaxLat(poi.getLat());
							break;
						case SE:
							closestArea.setMaxLon(poi.getLon());
							closestArea.setMinLat(poi.getLat());
							break;
						case NW:
							closestArea.setMinLon(poi.getLon());
							closestArea.setMaxLat(poi.getLat());
							break;
						case SW:
							closestArea.setMinLon(poi.getLon());
							closestArea.setMinLat(poi.getLat());
							break;
					}
				}
			}
		}
		
		return temp;
	}

	public List<POI> getPois()
	{
		return pois;
	}

	public void setPois(List<POI> pois)
	{
		this.pois = pois;
	}
}
