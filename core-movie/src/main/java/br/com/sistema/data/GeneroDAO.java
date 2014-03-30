package br.com.sistema.data;

import org.hibernate.Session;

import br.com.sistema.core.BaseDAO;
import br.com.sistema.entity.Genero;

public class GeneroDAO extends BaseDAO<Genero>{
	
	public GeneroDAO()
	{
		
	}
	
	public GeneroDAO(Session session) {
		super(session);
	}
	
	@Override
	protected String nomeTabela() {
		return "Genero";
	}

}
