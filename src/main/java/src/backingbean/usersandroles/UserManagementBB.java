package src.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import src.entity.Grupo;
import src.entity.User;
import src.entity.UserProfile;
import src.inter.IServiceLocator;

@Named
@SessionScoped
public class UserManagementBB implements Serializable {


	private static final long serialVersionUID = 15L;

	@Inject 
	private IServiceLocator serviceLocator;
	
	private User user;
	
	private String nickSearch = "NO_INICIALIZADO";
	private Integer idSearch = 0;
	
	// Grupo - doubleListaControl
	private List<Grupo> listaGrupos;	
	private List<String> lista1;	
	private List<String> lista2;	
	private String sel1;
	private String sel2;
	
	private void publish(String msg) {
		System.out.println("msg");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
	}
	
	public String searchById() {
		publish("searchById() - id = " + getIdSearch());

		setUser(getServiceLocator().getUserServices().read(getIdSearch()));		
		setLista2(null);	

		return "users";
	}
	
	public String searchByNick() {
		publish("searchByNick() - nick = " + getNickSearch());
		setUser(getServiceLocator().getUserServices().createNamedQuery("byNick", "nick", getNickSearch()));	
		setLista2(null);	

		publish("cargado user = " + getUser());
		
		return "users";
	}
	
	@Transactional
	public String createNew() {
		publish("createNew() - user = " + getUser());

		getServiceLocator().getUserServices().persist(getUser());	
		getServiceLocator().getEntityManager().flush();
		setLista2(null);	
		return "users";

	}
	
	@Transactional
	public String updateUser() {
		publish("updateUser() - user = " + getUser());

		getServiceLocator().getUserServices().merge(getUser());	
		getServiceLocator().getEntityManager().flush();
		setLista2(null);	
		return "users";

	}
	
	public String addElementToList2() {
		publish("addElementToList2() - elem = " + getSel1());

		if(!getLista2().contains(getSel1())) {
			getLista2().add(getSel1()); // agrega en la lista2
			
			addGroupToUser(getGrupoFromName(getSel1()));
		}
		return null;
	}
	
	public String removeElementFromList2() {
		publish("removeElementFromList2() - elem = " + getSel2());

		removeGroupFromUser(getGrupoFromName(getSel2()));		

		return null;
	}
	
	
	private void addGroupToUser(Grupo grupo) {
		
		getUser().addGrupo(grupo);		
		setLista2(null);
	}
	
	private void removeGroupFromUser(Grupo grupo) {
		
		getUser().removeGrupo(grupo);	
		setLista2(null);
	}
	
	private Grupo getGrupoFromName(String name) {
		Grupo result = null;
		for(Grupo grupo : getListaGrupos()) {
			if(grupo.getName().matches(name)) {
				result = grupo;
			}
		}
		return result;
	}
	

	public IServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(IServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public User getUser() {
		if(user == null) {
			user = getServiceLocator().getUserServices().getTransferObject();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNickSearch() {
		return nickSearch;
	}

	public void setNickSearch(String nickSearch) {
		this.nickSearch = nickSearch;
	}

	public Integer getIdSearch() {
		return idSearch;
	}

	public void setIdSearch(Integer idSearch) {
		this.idSearch = idSearch;
	}

	public List<Grupo> getListaGrupos() {
		if(listaGrupos == null) {
			listaGrupos = getServiceLocator().getGrupoServices().readAll();
		}
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<String> getLista1() {
		if(lista1 == null) {
			lista1 = new ArrayList<String>();
			for(Grupo grupo : getListaGrupos()) {
				lista1.add(grupo.getName());
			}
		}
		return lista1;
	}

	public void setLista1(List<String> lista1) {
		this.lista1 = lista1;
	}

	public List<String> getLista2() {
		if(lista2 == null) {
			lista2 = new ArrayList<String>();
			for(UserProfile profile : getUser().getProfiles()) {
				
				lista2.add(profile.getGrupo().getName());
			}
		}
		return lista2;
	}

	public void setLista2(List<String> lista2) {
		this.lista2 = lista2;
	}

	public String getSel1() {
		return sel1;
	}

	public void setSel1(String sel1) {
		this.sel1 = sel1;
	}

	public String getSel2() {
		return sel2;
	}

	public void setSel2(String sel2) {
		this.sel2 = sel2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
