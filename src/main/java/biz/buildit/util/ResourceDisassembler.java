package biz.buildit.util;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import biz.buildit.exception.InvalidDatesException;
import biz.buildit.main.Plant;
import biz.buildit.main.PlantHireRequest;
import biz.buildit.main.SiteEngineer;
import biz.buildit.main.WorksEngineer;
import biz.buildit.repository.PlantRepository;
import biz.buildit.rest.PlantHireRequestResource;
import biz.buildit.rest.PlantResource;
import biz.buildit.rest.PlantResourceList;
import biz.buildit.rest.SiteEngineerResource;
import biz.buildit.rest.WorksEngineerResource;

public class ResourceDisassembler {
	
	@Autowired static PlantRepository plantRepository;
	static List<Plant> listPlant;
	
//	public static SiteEngineer getSiteEngineer(SiteEngineerResource siteEngineerResource){
//		SiteEngineer siteEngineer = SiteEngineer.findSiteEngineer(siteEngineerResource.getId());
//		return siteEngineer;
//	}
//
//	public static WorksEngineer getWorksEngineer(WorksEngineerResource worksEngineerResource){
//		WorksEngineer worksEngineer = WorksEngineer.findWorksEngineer(worksEngineerResource.getId());
//		return worksEngineer;
//	}
	
	public static Plant getPlant(PlantResource plantResource){
		Plant plant = new Plant();
		plant.setPId(plantResource.getPId());
		plant.setName(plantResource.getName());
		plant.setPrice(Float.valueOf(String.valueOf(plantResource.getPrice())));
		plant.setDescription(plantResource.getDescription());
		return plant;
	}
	
	public static void createPlant(PlantResource plantResource){
		for(Plant plant:listPlant){
			if(plant.getPId() == plantResource.getPId()){
				plant.setName(plantResource.getName());
				plant.setPrice(Float.valueOf(String.valueOf(plantResource.getPrice())));
				plant.setDescription(plantResource.getDescription());
				return;
			}
		}
		Plant plant = getPlant(plantResource);
		plant.persist();
	}
	
	public static void refreshPlantList(PlantResourceList plantResourceList){
		List<PlantResource> list = plantResourceList.getPlantResources();
		listPlant = Plant.findAllPlants();
		for(PlantResource plantResource:list){
			createPlant(plantResource);
		}
	}

	public static PlantHireRequest getPlantHireRequest(PlantHireRequestResource plantHireRequestResource) throws InvalidDatesException{
		PlantHireRequest plantHireRequest = new PlantHireRequest();
		//plantHireRequest.setSiteEng(getSiteEngineer(plantHireRequestResource.getSiteEngineerResource()));
		plantHireRequest.setSiteId(plantHireRequestResource.getSiteId());
		plantHireRequest.setPlant(getPlant(plantHireRequestResource.getPlantResource()));
		if(plantHireRequestResource.getStartDate() > plantHireRequestResource.getEndDate())
			throw new InvalidDatesException("Time machine is not invented yet");
		plantHireRequest.setStartDate(new Date(plantHireRequestResource.getStartDate()));
		if(plantHireRequestResource.getEndDate() != null)
			plantHireRequest.setEndDate(new Date(plantHireRequestResource.getEndDate()));
		plantHireRequest.setExtensionDate(new Date(plantHireRequestResource.getExtensionDate()));
		//plantHireRequest.setWEng(getWorksEngineer(plantHireRequestResource.getWEngineerResource()));
		plantHireRequest.setApproval(plantHireRequest.getApproval());
		plantHireRequest.setComments(plantHireRequestResource.getComments());
		return plantHireRequest;
	}
	
	public static PlantHireRequest modifyPlantHireRequest(PlantHireRequest plantHireRequest,PlantHireRequestResource plantHireRequestResource){
		//plantHireRequest.setSiteEng(getSiteEngineer(plantHireRequestResource.getSiteEngineerResource()));
		plantHireRequest.setSiteId(plantHireRequestResource.getSiteId());
		plantHireRequest.setPlant(getPlant(plantHireRequestResource.getPlantResource()));
		plantHireRequest.setStartDate(new Date(plantHireRequestResource.getStartDate()));
		plantHireRequest.setEndDate(new Date(plantHireRequestResource.getEndDate()));
		if(plantHireRequestResource.getExtensionDate() != null)
			plantHireRequest.setExtensionDate(new Date(plantHireRequestResource.getExtensionDate()));
		//plantHireRequest.setWEng(getWorksEngineer(plantHireRequestResource.getWEngineerResource()));
		plantHireRequest.setApproval(Approval.getApproval(plantHireRequestResource.getApproval()));
		plantHireRequest.setComments(plantHireRequestResource.getComments());
		return plantHireRequest;
	}
}
