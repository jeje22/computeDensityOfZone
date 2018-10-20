package computeDensityOfZone.service;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/poi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PoiService {
	
	private java.nio.file.Path GetInputPath()
	{
		return Paths.get("C:\\Users\\jerom\\Desktop\\Prog\\Perso\\Happn\\computeDensityOfZone\\data\\input.tsv");
	}

	@GET
	@Path("/getNumber")
	public Response GetNumberInArea(@QueryParam("lon") @DefaultValue("2.35") double lon, 
            @QueryParam("lat") @DefaultValue("48.85") double lat) {
		Map<String,Object> map = new HashMap<>();

		PoiManager manager = PoiManager.Create(GetInputPath());
		if(manager!=null)
		{
			map.put("resultat", manager.getPoiForArea(lat, lon));
			return Response.ok(map).build();
		}
		else
		{
			map.put("error", "error while getting PoiManager");
			return Response.status(500).entity(map).build();
		}
	}
	
	@GET
	@Path("/getBiggestAreas")
	public Response GetBiggestAreas()
	{
		Map<String,Object> map = new HashMap<>();

		PoiManager manager = PoiManager.Create(GetInputPath());
		if(manager!=null)
		{
			return Response.ok(manager.findBiggestAreas(2)).build();
		}
		else
		{
			map.put("error", "error while getting PoiManager");
			return Response.ok(map).build();
		}
		
	}
}
