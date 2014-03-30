package br.com.sistema.teste;

import java.util.List;

import br.com.sistema.data.FilmeDAO;
import br.com.sistema.entity.Filme;

public class Principal {

	
	public static void main(String[] args) {
	
		FilmeDAO filmeDao = new FilmeDAO();
		
		try {
			
			/*
			Session session = HibernateUtil.getSession();
			session.beginTransaction();
			filmeDao = new FilmeDAO(session);
			GeneroDAO generoDAO = new GeneroDAO(session);
			Genero genero = generoDAO.consultaEntidade(Genero.class, 19);
			Filme filme = new Filme();
			filme.setTitulo("TEste2");
			
			if(genero == null)
			{
				filme.getGenero().setDescricao("Teste3sssssss33");
			}
			else
			{
				filme.setGenero(genero);
				filme.getGenero().setDescricao("Tesaaaas33");
			}
			filmeDao.insert(filme);
			filmeDao.delete("id = ?", 19);
			session.getTransaction().commit();
			
			select c.id from Grupo c inner join c.usuarios u where u.id = ?
			
			
			//query = "select f From Filme as f join f.genero as g order by g.descricao asc";
			
			filmeDao.columnsSelect("f");
			filmeDao.table("Filme as f join f.genero as g");
			filmeDao.orderBy("g.descricao asc ");
			List<Filme> filmes =  filmeDao.select("f.titulo like ? or g.descricao like ? or f.tipoAudio like ?", "Se%", "Se%", "Se%");
			
			//Collections.sort(filmes, new FilmeOrderByGeneroASC());
			
			*/
			//filmeDao.table("Filme as f join f.genero as g");
			//filmeDao.columnsSelect("new Filme(f.titulo, g.descricao)");
			//List<Filme> filmes =  filmeDao.select();
			
			filmeDao.columnsSelect("f");
			filmeDao.table("Filme as f join f.genero as g");
			filmeDao.orderBy("g.descricao asc ");
			List<Filme> filmes =  filmeDao.select("f.titulo like ? or g.descricao like ? or f.tipoAudio like ?", "Se%", "Se%", "Se%");
			
		
			for (Filme filme : filmes) {
				System.out.println(filme.getSinopse() + " " +filme.getTitulo() + "  " +filme.getGenero().getDescricao());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
