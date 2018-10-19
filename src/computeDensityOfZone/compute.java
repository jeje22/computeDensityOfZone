package computeDensityOfZone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class compute {

	private final static String separator="\t";

	private static PoiManager GetPoiManagerWithPois()
	{
		Path inputPath= Paths.get("data/input.tsv");

		List<Poi> pois=new ArrayList<>();
		PoiManager manager=null;
		try (Stream<String> stream = Files.lines(inputPath)) {
			// skip 1 to skip the column information
			// I didn't implement a way to check the order of the columns meaning it must always have the same order to work
			stream.skip(1).map(x -> x.split(separator))
					.forEach(x -> pois.add(new Poi(x[0], Double.parseDouble(x[1]), Double.parseDouble(x[2]))));

			manager = new PoiManager(pois);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return manager;
	}

	public static void main(String[] argv)
	{
		Path inputPath=Paths.get("data/input.tsv");
		
		// checking arg parameters
		if(argv.length>0)
		{
			Path argPath=Paths.get(argv[0]);
			
			if(Files.exists(argPath))
			{
				inputPath=argPath;
			}
			else
			{
				System.err.println("file "+argv[0]+" doesn't exist, fallback to default");
			}
		}
		else
		{
			System.out.println("no argument given, using default input value");
		}
		
		// reading file
		double minLat=6.5, minLon=-7;

		PoiManager manager=GetPoiManagerWithPois();
		System.out.println("number of POI in area : "+manager.getPoiForArea(minLat,minLon));

		manager.findBiggestAreas(2).forEach(Poi::printAll);
	}
}
