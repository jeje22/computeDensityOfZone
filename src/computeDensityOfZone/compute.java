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
		List<POI> pois=new ArrayList<POI>();
		List<String> columns;
		try (Stream<String> stream = Files.lines(inputPath))
		{
			/*columns=Stream.of(stream.findFirst().get().split(separator)).map(x -> x.substring(1)).collect(Collectors.toList());
			int idIndex=columns.indexOf("id");
			int lonIndex=columns.indexOf("lon");
			int latIndex=columns.indexOf("lat");*/
			
			List<String[]> data=stream.skip(1).map(x -> x.split(separator)).collect(Collectors.toList());
			data.forEach(x->pois.add(new POI(x[0],Double.parseDouble(x[1]),Double.parseDouble(x[2]))));

			double minLat=6.5, minLon=-7;

			PoiManager manager=new PoiManager(pois);
			System.out.println("number of POI in area : "+manager.getPoiForArea(minLat,minLon));

			manager.findBiggestAreas(2).forEach(System.out::println);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
