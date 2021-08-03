package src.backingbean;

import javax.persistence.EntityManager;

import org.junit.Before;
import static org.mockito.Mockito.*;


import src.entity.Grupo;
import src.entity.User;
import src.entityservices.GrupoEntityServices;
import src.entityservices.IEntityServices;
import src.entityservices.UserServices;
import src.inter.IServiceLocator;
import src.service.ServiceLocator;

public class UserManagementBBTest {
	
	private UserManagementBB instancia;
	private IServiceLocator serviceLocator;
	private IEntityServices<User> userServices;
	private IEntityServices<Grupo> grupoServices;
	private EntityManager entityManager;
	
	
	
	@Before
	public void init() {
		
		userServices = mockUserServices();
		grupoServices = mockGrupoServices();
		entityManager = mockEntityManager();
		
		instancia = new UserManagementBB();
		serviceLocator = mockServiceLocator();
		
		instancia.setServiceLocator(serviceLocator);
		
		
	}
	
	
	private IServiceLocator mockServiceLocator() {
		IServiceLocator sl = mock(ServiceLocator.class);
		when(sl.getUserServices()).thenReturn(userServices);
		when(sl.getGrupoServices()).thenReturn(grupoServices);		
		when(sl.getEntityManager()).thenReturn(entityManager);		
		
		return sl;
	}
	
	private IEntityServices<User> mockUserServices(){
		IEntityServices<User> us = mock(UserServices.class);
		
		return us;
	}
	
	private IEntityServices<Grupo> mockGrupoServices(){
		IEntityServices<Grupo> gs = mock(GrupoEntityServices.class);
		
		return gs;
	}
	
	private EntityManager mockEntityManager() {
		EntityManager em = mock(EntityManager.class);
		
		return em;
	}

}
