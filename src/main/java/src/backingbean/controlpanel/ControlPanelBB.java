package src.backingbean.controlpanel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ControlPanelBB {
	
	public String actionUsersAndRoles() {
		return "users";
	}
	
	public String actionShop() {
		return "shop";
//		return "http://localhost:8080/RS3/index.xhtml?faces-redirect=true";
	}
	
	public String actionLoadProducts() {
		return "loadproducts";
	}
	
	public String actionShipments() {
		return "shipments";
	}
	
	public String actionMaintenanceTasks() {
		return "maintenancetasks";
	}
	
	public String actionReports() {
		return "reports";
	}

}
