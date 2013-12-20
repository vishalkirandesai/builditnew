package biz.buildit.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.Test;

import biz.buildit.beans.PropertiesHolder;
import biz.buildit.rest.PlantHireRequestResource;
import biz.buildit.util.Approval;
import biz.buildit.util.PlantHireRequestResourceAssembler;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class PlantHireRequestTest {
	
	PlantHireRequest plantHireRequest;
	Plant plantCatalogue;
	SiteEngineer siteEngineer;
	WorksEngineer worksEngineer;
	PropertiesHolder propertiesHolder;
	String url;
	
	public PlantHireRequestTest() throws FileNotFoundException, IOException{
		
		propertiesHolder = PropertiesHolder.getInstance();
		url = propertiesHolder.getApplicationURL();
		
		plantCatalogue = new Plant();
		plantCatalogue.setPrice(50);
		plantCatalogue.setName("Yamaha");
		plantCatalogue.setDescription("440x-Full power");
		
		siteEngineer = new SiteEngineer();
		siteEngineer.setName("John Carlisle");
		
		worksEngineer = new WorksEngineer();
		worksEngineer.setName("David Southam");
		
		plantHireRequest = new PlantHireRequest();
		plantHireRequest.setSiteId(15);
		plantHireRequest.setStartDate(new Date(1385596800000L));
		plantHireRequest.setEndDate(new Date(1386115200000L));
		plantHireRequest.setPlant(plantCatalogue);
		plantHireRequest.setSiteEng(siteEngineer);
		plantHireRequest.setWEng(worksEngineer);
		plantHireRequest.setComments("bazinga!");
		plantHireRequest.setApproval(Approval.PENDINGAPPROVAL);
		
Client client = new Client();
		
		WebResource webResource = client.resource(url+"/planthirerequest");
		
		ClientResponse clientResponse = 
				webResource.type(MediaType.APPLICATION_XML)
							.accept(MediaType.APPLICATION_XML)
							.post(ClientResponse.class,PlantHireRequestResourceAssembler.getPlantHireRequestResource(plantHireRequest));
		
		
	}
	
	@Test
	public void testCreatePlantHireRequest(){
		
		Client client = new Client();
		
		WebResource webResource = client.resource(url+"/planthirerequest");
		
		ClientResponse clientResponse = 
				webResource.type(MediaType.APPLICATION_XML)
							.accept(MediaType.APPLICATION_XML)
							.post(ClientResponse.class,PlantHireRequestResourceAssembler.getPlantHireRequestResource(plantHireRequest));
		
		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), clientResponse.getStatus());

	}
	
	@Test
	public void testUpdatePlantHireRequest(){
		
Client client = new Client();
		
		WebResource webResource = client.resource(url+"/planthirerequest");
		
		ClientResponse clientResponse = 
				webResource.type(MediaType.APPLICATION_XML)
							.accept(MediaType.APPLICATION_XML)
							.post(ClientResponse.class,PlantHireRequestResourceAssembler.getPlantHireRequestResource(plantHireRequest));
		
		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), clientResponse.getStatus());
        
		
		
		WebResource webResource2 = client.resource(clientResponse.getLocation());  //url+"/request/planthirerequest/"+plantHireRequest.getId());
		
		plantHireRequest.setExtensionDate(new Date(1386288000000L));
		
		ClientResponse clientResponse2 = 
				webResource2.type(MediaType.APPLICATION_XML)
							.accept(MediaType.APPLICATION_XML)
							.put(ClientResponse.class,PlantHireRequestResourceAssembler.getPlantHireRequestResource(plantHireRequest));
		
		Assert.assertTrue(Response.Status.OK.getStatusCode() == clientResponse2.getStatus());
	}
	
	@Test
	public void testDeletePlantHireRequest(){
		
		Client client = new Client();
		
		WebResource webResource = client.resource(url+"/request/planthirerequest");
		
		ClientResponse clientResponse = 
				webResource.type(MediaType.APPLICATION_XML)
							.accept(MediaType.APPLICATION_XML)
							.post(ClientResponse.class,PlantHireRequestResourceAssembler.getPlantHireRequestResource(plantHireRequest));
		
		Assert.assertTrue(clientResponse.getStatus() == Response.Status.CREATED.getStatusCode());
		
		WebResource webResource1 = client.resource(clientResponse.getLocation());
		System.out.println(clientResponse.getLocation());
		ClientResponse clientResponse2 = 
				webResource1.type(MediaType.APPLICATION_XML)
							.accept(MediaType.APPLICATION_XML)
							.delete(ClientResponse.class);
		
		Assert.assertTrue(clientResponse2.getStatus() == Response.Status.OK.getStatusCode());
	}
	
	@Test
	public void testPlantHireRequestStatus() throws IOException{
		Client client = new Client();
		
		WebResource webResource = client.resource(url+"/request/planthirerequest");
		
		ClientResponse clientResponse = 
				webResource.type(MediaType.APPLICATION_XML)
							.accept(MediaType.APPLICATION_XML)
							.post(ClientResponse.class,PlantHireRequestResourceAssembler.getPlantHireRequestResource(plantHireRequest));
		
		//Assert.assertTrue(clientResponse.getStatus() == Response.Status.CREATED.getStatusCode());
		
		WebResource webResource2 = client.resource(clientResponse.getLocation());
		
		
		
		ClientResponse clientResponse2 = webResource2.type(MediaType.APPLICATION_XML)
													.accept(MediaType.APPLICATION_XML)
													.get(ClientResponse.class);
		
		Assert.assertEquals(Approval.PENDINGAPPROVAL.getStatusCode(),clientResponse2.getEntity(PlantHireRequestResource.class).getApproval());
	}
}
