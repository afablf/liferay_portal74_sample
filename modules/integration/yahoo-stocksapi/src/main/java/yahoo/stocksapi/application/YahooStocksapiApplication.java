package yahoo.stocksapi.application;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.omg.CORBA.RepositoryIdHelper;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author me
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/market",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Market.Rest"
	},
	service = Application.class
)
public class YahooStocksapiApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}
	
	@GET
	@Path("/trending/{region}")
	@Produces("text/plain")
	public Response trendStocks(@PathParam("region") String region) {
		try {
			return Response.ok(region).build();
		} catch (Exception e) {
			return Response.status(
					Response.Status.BAD_REQUEST.getStatusCode()
				).entity(
					e.getMessage()
				).build();
		}
	}
	
	private static final Log _log = LogFactoryUtil.getLog(YahooStocksapiApplication.class);

}