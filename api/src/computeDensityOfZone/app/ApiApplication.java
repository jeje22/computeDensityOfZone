package computeDensityOfZone.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import computeDensityOfZone.service.PoiService;

public class ApiApplication extends Application {
	
	private Set<Object> singletons = new HashSet<Object>();

	public ApiApplication() {
		singletons.add(new PoiService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
