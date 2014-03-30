package br.com.sistema.data;

import org.hibernate.Session;

import br.com.sistema.core.BaseDAO;
import br.com.sistema.entity.Filme;

public class FilmeDAO extends BaseDAO<Filme> {

	public FilmeDAO()
	{
		
	}
	
	public FilmeDAO(Session session) {
		super(session);
	}
	
	@Override
	protected String nomeTabela() {
		return "Filme";
	}

}
