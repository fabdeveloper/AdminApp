package src.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import src.entity.DeliveryDetailsStatusType;
import src.entity.Grupo;
import src.entity.User;
import src.entity.UserProfile;
import src.inter.IServiceLocator;
import src.jsfcompslib.util.interfaces.IProcessable;

@Named
@SessionScoped
public class UserEntityManagementControlBB implements Serializable, IProcessable{	

	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private IServiceLocator serviceLocator;	
	private User user;
	
	
	private List<Grupo> listaGrupos;	

	private List<String> lista1;	
	private List<String> lista2;
	
	private String sel1;
	private String sel2;
	
	
	private void updateUserToDB() {
		publish("updateUserToDB() - intento actualizar usuario = " + getUser());
		user = getServiceLocator().getUserServices().merge(getUser());
		getServiceLocator().getEntityManager().flush();
		setLista2(null);	
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
	
	
	private void loadUserById() {
		
		if(getUser().getId() != null) {
			user = getServiceLocator().getUserServices().read(getUser().getId());
			setLista2(null);
		}
		
	}
	
	private void loadUserByNick() {
		
		if(getUser().getNick() != null) {
			user = getServiceLocator().getUserServices().createNamedQuery("byNick", "nick", getUser().getNick());
			setLista2(null);
		}		
	}
	
	
	
	@Transactional
	@Override
	public String process() {	// actualiza el user en DB	

		updateUserToDB();
		
		publish("Actualizado usuario : " + getUser());
		return null;
	}	
	
	@Override
	public String process1() {
		
		if(!getLista2().contains(getSel1())) {
			getLista2().add(getSel1()); // agrega en la lista2
			
			addGroupToUser(getGrupoFromName(getSel1()));
		}
		return null;
	}
	
	@Override
	public String process2() {		// elimina un grupo de la lista de grupos del usuario
		
		removeGroupFromUser(getGrupoFromName(getSel2()));		
		return null;
	}
	
	@Override
	public String process3() { // load user by ID
		
		loadUserById();
		return null;
	}
	
	@Override
	public String process4() { // load user by NICK
		
		loadUserByNick();
		return null;
	}

	
	
	private void publish(String msg) {
		System.out.println(msg);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
	}

	public IServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(IServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public User getUser() {
		if(user == null) {
			user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
