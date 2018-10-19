package computeDensityOfZone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.Point;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PoiManager
{

    final static String separator = "\t";

    public static double roundToHalfDown(double value)
    {
        return Math.floor(value * 2) / 2;
    }

    public static double roundToHalfUp(double value)
    {
        return roundToHalfDown(value) + 0.5;
    }

    private class PoiWithWeight extends Poi
    {
        public int getWeight()
        {
            return weight;
        }

        public void addWeight()
        {
            this.weight++;
        }

        private int weight;

        public PoiWithWeight(Poi poi)
        {
            super(poi.getId(), poi.getLon(), poi.getLat());
            this.weight = 1;
        }
    }

    private List<Poi> pois;
    Point minBoundary;
    Point maxBoundary;

    public PoiManager(List<Poi> pois)
    {
        this.pois = pois;
        this.minBoundary = new Point(-90, -180);
        this.maxBoundary = new Point(90, 180);
    }

    int getPoiForArea(double minLat, double minLon)
    {
        double maxLat = minLat + Poi.step;
        double maxLon = minLon + Poi.step;

        int res = 0;

        for (Poi poi : pois)
        {
            if (poi.getLat() > minLat && poi.getLat() < maxLat
                    && poi.getLon() > minLon && poi.getLon() < maxLon)
            {
                res++;
            }
        } //10 794 100

        // too slow 27 000 500 : parallelstream is even slower ~ 100 000 000
        //res=(int)pois.stream().filter(x->x.getLat()>minLat && x.getLat()<maxLat && x.getLon()>minLon && x.getLon()<maxLon).count();

        return res;
    }

    List<Poi> findBiggestAreas(int N)
    {
        List<PoiWithWeight> temp = new ArrayList<>();

        for (Poi poi : pois)
        {
            boolean foundSameZone = false;
            for (PoiWithWeight poiWithWeight : temp)
            {
                if (roundToHalfDown(poi.getLat()) == roundToHalfDown(poiWithWeight.getLat())
                        && roundToHalfDown(poi.getLon()) == roundToHalfDown(poiWithWeight.getLon()))
                {
                    poiWithWeight.addWeight();
                    foundSameZone = true;
                }
            }

            if (!foundSameZone)
            {
                temp.add(new PoiWithWeight(poi));
            }
        }

        // get elements where its weight is the highest and then limit to N elements
        return temp.stream().sorted(Comparator.comparingInt(PoiWithWeight::getWeight).reversed()).limit(N).map(x -> x.ToArea()).collect(Collectors.toList());
    }

    static PoiManager Create(Path inputPath)
    {
        PoiManager manager = null;
        try (Stream<String> stream = Files.lines(inputPath))
        {
            List<Poi> pois = new ArrayList<>();
            // skip 1 to skip the column information
            // I didn't implement a way to check the order of the columns meaning it must always have the same order to work
            stream.skip(1).map(x -> x.split(separator))
                    .forEach(x -> pois.add(new Poi(x[0], Double.parseDouble(x[1]), Double.parseDouble(x[2]))));

            manager = new PoiManager(pois);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return manager;
    }
}
