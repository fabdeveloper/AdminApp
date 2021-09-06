package src.backingbean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import src.entity.Grupo;
import src.entity.User;
import src.inter.IServiceLocator;


@Named
@SessionScoped
public class UserConfViewBB implements Serializable {
	
	@Inject
	private IServiceLocator serviceLocator;
	
	private List<Grupo> listaGrupos;
	
	
	
	public User getUserById(Integer user_id) {
		
		return null;
	}
	
	public User getUserByNick(String nick) {
		
		return null;
	}

	public IServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(IServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
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
	

	
	

}
