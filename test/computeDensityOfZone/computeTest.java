package computeDensityOfZone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class computeTest
{

    @Test
    public void GetNumberOfPoiInArea()
    {
        PoiManager manager = PoiManager.Create(Paths.get("data/input.tsv"));

        double minLat = 6.5, minLon = -7;
        Assertions.assertNotNull(manager);
        Assertions.assertEquals(2, manager.getPoiForArea(minLat, minLon));
    }

    @Test
    public void FindTwoBiggestAreas()
    {
        PoiManager manager = PoiManager.Create(Paths.get("data/input.tsv"));

        Assertions.assertNotNull(manager);

        List<Poi> myListOfPois = new ArrayList<>();
        myListOfPois.add(new Poi("first", 38, -2.5));
        myListOfPois.add(new Poi("second", -7, 6.5));
        Assertions.assertEquals(myListOfPois, manager.findBiggestAreas(2));
    }
}
