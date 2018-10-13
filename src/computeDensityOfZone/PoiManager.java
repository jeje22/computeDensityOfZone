package computeDensityOfZone;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.Point;

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
		List<POI> res=new ArrayList<POI>();
		
		//pois.sort(Comparator.comparing(POI::getLon));
		
		
		return res;
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
