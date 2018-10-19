package computeDensityOfZone;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class compute
{

    public static void main(String[] argv)
    {
        Path inputPath = Paths.get("data/input.tsv");

        // checking arg parameters
        if (argv.length > 0)
        {
            Path argPath = Paths.get(argv[0]);

            if (Files.exists(argPath))
            {
                inputPath = argPath;
            }
            else
            {
                System.err.println("file " + argv[0] + " doesn't exist, fallback to default");
            }
        }
        else
        {
            System.out.println("no argument given, using default input value");
        }

        // reading file
        double minLat = 6.5, minLon = -7;

        PoiManager manager = PoiManager.Create(inputPath);
        if (manager == null)
        {
            System.err.println("error while getting Pois");
        }
        else
        {
            System.out.println("number of POI in area : " + manager.getPoiForArea(minLat, minLon));

            manager.findBiggestAreas(2).forEach(Poi::printAll);
        }
    }
}
