package br.com.sistema.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import org.hibernate.Session;

import br.com.sistema.core.BaseDAO;
import br.com.sistema.data.FilmeDAO;
import br.com.sistema.data.GeneroDAO;
import br.com.sistema.entity.Filme;
import br.com.sistema.entity.Genero;
import br.com.sistema.util.UtilFaces;

@ManagedBean
@ViewScoped
public class FilmeBean extends BaseCadastro<Filme> {
	private static final long serialVersionUID = 1L;
	private List<Genero> generos = null;
	
	
	@Override
	protected Filme getNewEntidade() {
		return new Filme();
	}

	@Override
	protected Filme getEntidadeInsertUpdate() {
		GeneroDAO generoDAO = new GeneroDAO(getBase().getSession());
		Genero genero = generoDAO.consultaEntidade(Genero.class, getEntidade().getGenero().getId());
		getEntidade().setGenero(genero);
		getEntidade().setData(new Date(System.currentTimeMillis()));
		return getEntidade();
	}

	@Override
	protected ListDataModel<Filme> newListDataModel(List<Filme> entidade) {
		return new ListDataModel<Filme>(entidade);
	}

	@Override
	protected Serializable retornaChave() {
		return getEntidade().getId();
	}

	@Override
	protected BaseDAO<Filme> newBaseDAO() {
		return new FilmeDAO();
	}

	@Override
	protected BaseDAO<Filme> newBaseDAO(Session session) {
		return new FilmeDAO(session);
	}
	
	public void visto()
	{
		try
		{
			FilmeDAO filmeDAO = (FilmeDAO) newBaseDAO();
			setEntidade(getDmEntidade().getRowData());		
			
			filmeDAO.fieldUpdate("visto", "S");
			filmeDAO.update("id = ?", getEntidade().getId());
			
			getEntidade().setVisto("S");
			alualizaListDataModel(getEntidade(), InsertUpdate.Update);
			
		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	private void carregaGeneros()
	{
		try
		{
			if(generos == null)
			{
//				generos = new ArrayList<Genero>();
//				GeneroDAO generoDAO = new GeneroDAO();
//				generoDAO.orderBy("descricao");
//				generos = generoDAO.select();
				generos = new ArrayList<Genero>();
			}
		}
		 catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	public List<Genero> getGeneros()
	{
		carregaGeneros();
		return generos;
	}

	@Override
	public void toPdf() {
	}

	@Override
	public void toDocx() {
	}

	@Override
	public void toXlsx() {
	}

	@Override
	public void toPptx() {
	}

	@Override
	public void toOdt() {
		
	}

	@Override
	protected String preencheWhere() {
		return "(f.titulo like ? or g.descricao like ? or f.tipoAudio like ?)";
	}

	@Override
	protected List<Object> preencheWhereArgs() {
		List<Object> whereArgs = new ArrayList<Object>();
		whereArgs.add(getEntidadePes().getTitulo() + "%");
		whereArgs.add(getEntidadePes().getTitulo() + "%");
		whereArgs.add(getEntidadePes().getTitulo() + "%");
		return whereArgs;
	}

	@Override
	protected String orderBy() {
		return " id asc";
	}
	
	@Override
	protected void alualizaListDataModel(Filme entidade, InsertUpdate tipo) {
		
		List<Filme> list = new ArrayList<Filme>();
		Iterator<Filme> it =  getDmEntidade().iterator();
		Filme filme = null;
		 
		while(it.hasNext()) {
            filme = it.next();
            	
            if(tipo == InsertUpdate.Update)
            {
            	if(filme.getId() == entidade.getId())
            		list.add(entidade);
            	else
            		list.add(filme);
            }
        }
		setDmEntidade(newListDataModel(list));
	}
	
	@Override
	protected void pesquizarDados() throws Exception {
		getBase().columnsSelect("new Filme(f.id,f.titulo,f.ano,f.audioOriginal,f.tipoAudio,str(f.data),f.visto,g.descricao)");
		getBase().table("Filme as f join f.genero as g");
		getBase().orderBy("f.id asc");
		super.pesquizarDados();
	}
}
