package br.com.sistema.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.hibernate.Session;

import br.com.sistema.core.BaseDAO;
import br.com.sistema.core.HibernateUtil;
import br.com.sistema.util.UtilFaces;


public abstract class BaseCadastro<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	private BaseDAO<T> baseDAO;
	private T entidade;
	private T entidadePes;
	private DataModel<T> dmEntidade;
	private List<T> listEntidades;
	private String tipo;
	private int tabIndex;
	private boolean exportarDados;
	private boolean relatorio;
	private InsertUpdate insertUpdate;
	private String orderBy;
	
	enum InsertUpdate {
		Insert, Update, Delete
	};
	
	enum Report {
		PDF, DOCX, XLSX, PPTX, ODT
	};

	public T getEntidade() {
		return entidade;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public T getEntidadePes() {
		return entidadePes;
	}

	public BaseDAO<T> getBase()
	{
		return baseDAO;
	}
	
	public BaseDAO<T> setBase(BaseDAO<T> baseDAO)
	{
		return this.baseDAO = baseDAO;
	}
	
	public void setEntidadePes(T entidadePes) {
		this.entidadePes = entidadePes;
	}

	public DataModel<T> getDmEntidade() {
		return dmEntidade;
	}

	public void setDmEntidade(DataModel<T> dmEntidade) {
		this.dmEntidade = dmEntidade;
	}

	public List<T> getListEntidades() {
		return listEntidades;
	}

	public void setListEntidades(List<T> listEntidades) {
		this.listEntidades = listEntidades;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public boolean isExportarDados() {
		return exportarDados;
	}

	public void setExportarDados(boolean exportarDados) {
		this.exportarDados = exportarDados;
	}

	public boolean isRelatorio() {
		return relatorio;
	}

	public void setRelatorio(boolean relatorio) {
		this.relatorio = relatorio;
	}

	public InsertUpdate getInsertUpdate() {
		return insertUpdate;
	}

	public void setInsertUpdate(InsertUpdate insertUpdate) {
		this.insertUpdate = insertUpdate;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void limpar()
	{
		iniciaCampos();
	}
	
	public BaseCadastro()
	{
		iniciaCampos();
	}
	
	protected void iniciaCampos() {
		entidade = getNewEntidade();
		entidadePes = getNewEntidade();
		dmEntidade = null;		
		tabIndex = 0;
		exportarDados = true;
		relatorio = true;
		insertUpdate = InsertUpdate.Insert;
		tipo = UtilFaces.msgI18n("cadastro.tipo.inserir");
	}
	
	public void rowToDelete() {
		entidade = dmEntidade.getRowData();
	}
	
	public void confirmarEntidade() {
		
		Session session = null;
		
		try {
			session = HibernateUtil.getSession();
			
			session.beginTransaction();
			baseDAO = newBaseDAO(session);
			
			if (insertUpdate == InsertUpdate.Insert) {
				baseDAO.insert(getEntidadeInsertUpdate());
				UtilFaces.addSuccessMessage(UtilFaces.msgI18n("cadastro.inserirSucesso"));
				iniciaCampos();
			} else {
				baseDAO.update(getEntidadeInsertUpdate());
				UtilFaces.addSuccessMessage(UtilFaces.msgI18n("cadastro.atualizarSucesso"));
			}
			
			session.getTransaction().commit();
			
			if (insertUpdate == InsertUpdate.Update) {
				alualizaListDataModel(getEntidadeInsertUpdate(), insertUpdate);
			}
			
		} catch (Exception ex) {
			if(session != null)
				session.getTransaction().rollback();
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	public void removerEntidade() {
		try {
			baseDAO = newBaseDAO();
			baseDAO.delete(getEntidade());
			alualizaListDataModel(getEntidade(), InsertUpdate.Delete);
			UtilFaces.addSuccessMessage(UtilFaces.msgI18n("cadastro.excluirSucesso"));
		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	public void inserirEntidade()
	{	
		iniciaCampos();	
	}
	
	public void editarEntidade(){
		try
		{
			baseDAO = newBaseDAO();
			entidade = getDmEntidade().getRowData();		
			entidade = baseDAO.consultaEntidade(entidade.getClass(), retornaChave());
			insertUpdate = InsertUpdate.Update;
			tipo = UtilFaces.msgI18n("cadastro.tipo.atualizar");
			tabIndex = 0;
		}
		catch(Exception ex)
		{
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	public void pesquisarEntidades() {
		try {
			
			baseDAO = newBaseDAO();
			baseDAO.orderBy(orderBy());
			
			pesquizarDados();
			
			dmEntidade = newListDataModel(listEntidades);
			if (listEntidades.size() > 0) {
				relatorio = false;
				exportarDados = false;
			}
			
		} catch (Exception ex) {
			UtilFaces.addErrorMessage(ex.getMessage());
		}
	}
	
	protected void pesquizarDados() throws Exception {
		listEntidades = baseDAO.select(preencheWhere(), preencheWhereArgs().toArray());
	}
	
	protected abstract T getNewEntidade();
	protected abstract T getEntidadeInsertUpdate();
	protected abstract String orderBy();
	protected abstract String preencheWhere();
	protected abstract List<Object> preencheWhereArgs();
	protected abstract ListDataModel<T> newListDataModel(List<T> entidade);
	protected abstract void alualizaListDataModel(T entidade, InsertUpdate tipo);
	protected abstract Serializable retornaChave();
	protected abstract BaseDAO<T> newBaseDAO();
	protected abstract BaseDAO<T> newBaseDAO(Session session);
	public abstract void toPdf();
	public abstract void toDocx();
	public abstract void toXlsx();
	public abstract void toPptx();
	public abstract void toOdt();
}
