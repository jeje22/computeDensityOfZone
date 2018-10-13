package computeDensityOfZone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class compute {

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
		
		try (Stream<String> stream = Files.lines(inputPath))
		{
			stream.forEach(System.out::println);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
