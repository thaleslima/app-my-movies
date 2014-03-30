package br.com.sistema.ws;

import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import br.com.sistema.data.FilmeDAO;
import br.com.sistema.entity.Filme;


@Path("/services")
public class Filmes {

	
	//@Produces("text/plain")'
	
	@GET
	@Path("/retornaTodos/{visto}/{ordenar}/{id}")
	@Produces("application/json")
	public List<Filme> retornaTodosByID(@PathParam("visto") String visto, @PathParam("ordenar") String ordenar, @PathParam("id") String id) {
		
		List<Filme> filmes = new ArrayList<Filme>();
		String where = "";
		List<Object> whereArgs = new ArrayList<Object>();
		
		try {
			FilmeDAO userDAO = new FilmeDAO();
			
			if(visto.contains("S") || visto.contains("N"))
			{
				where += "f.visto = ? and";
				whereArgs.add(visto);
			}
			
			//userDAO.columnsSelect("new Filme(f.id,f.titulo,f.visto,g.descricao,g.sinopse)");
			userDAO.columnsSelect("f");
			userDAO.table("Filme as f join f.genero as g");
			
			if(ordenar.contains("genero"))
			{
				userDAO.orderBy("g.descricao, f.titulo");
			}
			else if(ordenar.contains("titulo"))
			{
				userDAO.orderBy("f.titulo");
			}
			else
			{
				userDAO.orderBy("f.id desc");
			}
			
			where += "(f.titulo like ? or g.descricao like ? or f.tipoAudio like ?)";
			whereArgs.add("%" +id + "%");
			whereArgs.add(id + "%");
			whereArgs.add(id + "%");
			
			filmes = userDAO.select(where.toString(), whereArgs.toArray());

		} catch (Exception e) {
			return new ArrayList<Filme>();
		}	
		return filmes;
	}
	
	@GET
	@Path("/retornaTodos/{visto}/{ordenar}")
	@Produces("application/json")
	public List<Filme> retornaTodos(@PathParam("visto") String visto, @PathParam("ordenar") String ordenar) {
		
		List<Filme> filmes = new ArrayList<Filme>();
		String where = "";
		List<Object> whereArgs = new ArrayList<Object>();
		
		try {
			
			FilmeDAO userDAO = new FilmeDAO();
			
			if(visto.contains("S") || visto.contains("N"))
			{
				where += "f.visto = ?";
				whereArgs.add(visto);
			}
			
			//userDAO.columnsSelect("new Filme(f.id,f.titulo,f.visto,g.descricao)");
			userDAO.columnsSelect("f");
			userDAO.table("Filme as f join f.genero as g");
			
			if(ordenar.contains("genero"))
			{
				userDAO.orderBy("g.descricao, f.titulo");
			}
			else if(ordenar.contains("titulo"))
			{
				userDAO.orderBy("f.titulo");
			}
			else
			{
				userDAO.orderBy("f.id desc");
			}
			
			filmes = userDAO.select(where, whereArgs.toArray());

		} catch (Exception e) {
			return new ArrayList<Filme>();
		}	
		return filmes;
	}
	
	@GET
	@Path("/getFilme/{id}")
	@Produces("application/json")
	public Filme getFilme(@PathParam("id") int id)
	{
		Filme filme = new Filme();
		
		try {
			FilmeDAO filmeDAO = new FilmeDAO();
			List<Filme> filmes = filmeDAO.select("id = ?", id);
			
			if (filmes.size() > 0)
			{
				filme = filmes.get(0);
			}
			
		} catch (Exception e) {
			return filme;
		}
		
		return filme;
	}
	
	
	@GET
	@Path("/atualizaVisto/{id}/{value}")
	@Produces("text/plain")
	public String atualizaVisto(@PathParam("id") int id, @PathParam("value") String value) {
		try {
			if(value.equals("N") || value.equals("S"))
			{
				FilmeDAO filmeDAO = new FilmeDAO();
				
				filmeDAO.fieldUpdate("visto", value);
				filmeDAO.update("id = ?", id);	
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "OK";
	}
	
	/*	
	@PUT
	@Path("/AtualizaVisto/{id}")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String atualizaVisto(@PathParam("id") int id, String value)
	{
		try {
			if(value == "N" || value == "S")
			{
				FilmeDAO filmeDAO = new FilmeDAO();
				
				filmeDAO.fieldUpdate("visto", value);
				filmeDAO.update("id = ?", id);	
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "OK";
	}
	

	@POST
	@Path("/AddUsuario")
	@Consumes("application/json")
	@Produces("text/plain")
	public String adicionaUsuario(Usuario usuario)
	{
		try {
			UsuarioDAO userDAO = new UsuarioDAO();
			
			userDAO.insert(usuario);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "OK";
	}
	
	
	@GET
	@Path("/GetUsuario/{id}")
	@Produces("application/json")
	public Usuario getUsuario(@PathParam("id") Long id)
	{
		Usuario usuario = new Usuario();
		
		try {
			UsuarioDAO userDAO = new UsuarioDAO();
			usuario = userDAO.consultaEntidade(Usuario.class,id);
		} catch (Exception e) {
			return usuario;
		}
		
		return usuario;
	}
	
	
	@PUT
	@Path("/AtualizaUsuario")
	@Consumes("application/json")
	@Produces("text/plain")
	public String atualizaUsuario(Usuario usuario)
	{
		try {
			UsuarioDAO userDAO = new UsuarioDAO();
			userDAO.update(usuario);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		return "OK";
	}
	
	@Path("/DeletaUsuario/{id}")
	@DELETE
	@Produces("application/json")
	public String deletaUsuario(@PathParam("id") long id)
	{
		
		try {
			UsuarioDAO userDAO = new UsuarioDAO();
			Usuario usuario = new Usuario();
			
			userDAO.delete(usuario);
		} catch (Exception e) {
			return null;
		}
		
		return "OK";
	}
	
	
	@GET
	@Path("/metodo2/{M1}")
	@Produces("text/plain")
	public String getCotacaoEuroToReal(@PathParam("M1") String m1){
		
		return m1;
	}
	
	
	@GET
	@Path("/metodo3")
	@Produces("application/json")
	public Usuario showHelloWorld3() {
		
		List<Usuario> users = new ArrayList<Usuario>();
		
		try {
			
			UsuarioDAO userDAO = new UsuarioDAO();
			users = userDAO.consultaDados();

		} catch (Exception e) 
		}
				
		return users.get(0);
	}
	*/
}
