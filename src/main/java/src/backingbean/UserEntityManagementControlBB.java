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
import src.inter.IServiceLocator;
import src.jsfcompslib.util.interfaces.IProcessable;

@Named
@SessionScoped
public class UserEntityManagementControlBB implements Serializable, IProcessable{
	
	@Inject
	private IServiceLocator serviceLocator;	
	private User user;
	
	
	private List<Grupo> listaGrupos;	

	private List<String> lista1;	
	private List<String> lista2;
	
	private String sel1;
	private String sel2;
	
	
	@Transactional
	@Override
	public String process() {		
		publish("inicia process()");
		getUser().setListaGrupos(createListaGrupos(getLista2()));
		user = getServiceLocator().getUserServices().update(getUser());
		getServiceLocator().getEntityManager().flush();
		String msg = "Actualizado usuario : " + user;
		publish(msg);
		return null;
	}
	
	private List<Grupo> createListaGrupos(List<String> listaNombres) {
		publish("inicia createListaGrupos() - size= " + listaNombres.size());
		List<Grupo> nuevaLista = new ArrayList<Grupo>();
		for(String nombre : listaNombres) {
			for(Grupo grupo : listaGrupos) {
				if(grupo.getName().matches(nombre)) {
					nuevaLista.add(grupo);
				}
			}
		}	
		publish("nuevaLista creada con size = " + nuevaLista.size());
		return nuevaLista;		
	}
	
	@Override
	public String process1() {
		
		if(!getLista2().contains(getSel1())) {
			getLista2().add(getSel1());
		}
		return null;
	}
	
	@Override
	public String process2() {		
		getLista2().remove(getSel2());
		return null;
	}
	
	@Override
	public String process3() {
		if(getUser().getId() != null) {
			user = getServiceLocator().getUserServices().read(getUser().getId());
			setLista2(null);
		}
		return null;
	}
	
	@Override
	public String process4() {
		if(getUser().getNick() != null) {
			user = getServiceLocator().getUserServices().createNamedQuery("byNick", "nick", getUser().getNick());
			setLista2(null);
		}
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
			user.setListaGrupos(new ArrayList<Grupo>());
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
			for(Grupo grupo : getUser().getListaGrupos()) {
				lista2.add(grupo.getName());
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

	
	
	
	/*
	public List<Grupo> getLista1() {
		if(lista1 == null) {
			lista1 = getServiceLocator().getGrupoServices().readAll();
		}
		return lista1;
	}

	public void setLista1(List<Grupo> lista1) {
		this.lista1 = lista1;
	}

	public List<Grupo> getLista2() {
		if(lista2 == null) {
			if(user == null) {
				lista2 = new ArrayList<Grupo>();
			}
			else {
				lista2 = getUser().getListaGrupos();
			}
			
		}
		return lista2;
	}

	public void setLista2(List<Grupo> lista2) {
		this.lista2 = lista2;
	}

	public Grupo getSel1() {
		return sel1;
	}

	public void setSel1(Grupo sel1) {
		this.sel1 = sel1;
	}

	public Grupo getSel2() {
		return sel2;
	}

	public void setSel2(Grupo sel2) {
		this.sel2 = sel2;
	}
	*/
	
	
	

}
