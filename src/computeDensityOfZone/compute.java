package computeDensityOfZone;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

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
            System.out.println("Please enter the following informations to get the number of POI in the area :");
            System.out.println("latitude : ");
            Scanner sc = new Scanner(System.in);
            minLat = sc.nextDouble();
            System.out.println("longitude : ");
            minLon = sc.nextDouble();
            System.out.println("number of POI in area : " + manager.getPoiForArea(minLat, minLon));

            int N = 2;
            System.out.println("Please enter the number of areas for which you want to find the most populated areas: ");
            N = sc.nextInt();

            manager.findBiggestAreas(N).forEach(Poi::printAll);
        }
    }
}
