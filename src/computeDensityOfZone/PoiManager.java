package computeDensityOfZone;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.Point;
import java.util.stream.Collectors;

public class PoiManager {

	public static double roundToHalfDown(double value)
	{
		return Math.floor(value*2)/2;
	}

	public static double roundToHalfUp(double value)
	{
		return roundToHalfDown(value)+0.5;
	}

	private class PoiWithWeight extends POI{
		public int getWeight() {
			return weight;
		}

		public void addWeight() {
			this.weight++;
		}

		private int weight;

		public PoiWithWeight(POI poi)
		{
			super(poi.getId(),poi.getLon(),poi.getLat());
			this.weight=1;
		}

		public POI getPoi()
		{
			return null;//super(this.getId(),this.getLon(),this.getLat());
		}
	}
	
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

	List<POI> findBiggestAreas(int N)
	{
		List<POI> res=new ArrayList<>();

		List<PoiWithWeight> temp=new ArrayList<>();

		for(POI poi : pois)
		{
			if(temp.size()==0)
			{
				temp.add(new PoiWithWeight(poi));
			}
			else
			{
				boolean foundSameZone=false;
				for(PoiWithWeight poiWithWeight : temp)
				{
					if(roundToHalfDown(poi.getLat())==roundToHalfDown(poiWithWeight.getLat())
						&& roundToHalfDown(poi.getLon())==roundToHalfDown(poiWithWeight.getLon()))
					{
						poiWithWeight.addWeight();
						foundSameZone=true;
					}
				}

				if(!foundSameZone)
				{
					temp.add(new PoiWithWeight(poi));
				}
			}
		}

		return temp.stream().sorted(Comparator.comparingInt(PoiWithWeight::getWeight).reversed()).limit(2).collect(Collectors.toList());
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
