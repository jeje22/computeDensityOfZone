package computeDensityOfZone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;



public class computeTest {

    private PoiManager GetPoiManagerWithPois()
    {
        Path inputPath= Paths.get("data/input.tsv");

        List<Poi> pois=new ArrayList<>();
        PoiManager manager=null;
        try (Stream<String> stream = Files.lines(inputPath)) {
            // skip 1 to skip the column information
            // I didn't implement a way to check the order of the columns meaning it must always have the same order to work
            stream.skip(1).map(x -> x.split("\t"))
                    .forEach(x -> pois.add(new Poi(x[0], Double.parseDouble(x[1]), Double.parseDouble(x[2]))));

            manager = new PoiManager(pois);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return manager;
    }

    @Test
    public void GetNumberOfPoiInArea()
    {
        PoiManager manager=GetPoiManagerWithPois();

        double minLat = 6.5, minLon = -7;
        Assertions.assertNotNull(manager);
        Assertions.assertEquals(2,  manager.getPoiForArea(minLat,minLon));
    }

    @Test
    public void FindTwoBiggestAreas()
    {
        PoiManager manager=GetPoiManagerWithPois();

        Assertions.assertNotNull(manager);

        List<Poi> myListOfPois=new ArrayList<>();
        myListOfPois.add(new Poi("first",38, -2.5));
        myListOfPois.add(new Poi("second",-7,6.5));
        Assertions.assertEquals(myListOfPois, manager.findBiggestAreas(2));
    }
}
