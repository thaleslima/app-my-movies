package br.com.sistema.entity;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Filme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String titulo = "";
	private int ano;
	private String audioOriginal = "";
	private String tipoAudio = "";
	
	@Column(length=4000)
	private String sinopse = "";
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="idGenero")
	private Genero genero;
	
	private Date data; 
	private String visto = "";
	
	private String nomeArquivo = "";
	
	public Filme()
	{
		
	}
	
	public Filme(int id, String titulo, int ano, String audioOriginal, String tipoAudio, String data, String visto, String descricaoGenero)
	{
		this.id = id;
		this.titulo = titulo;
		this.ano = ano;
		this.audioOriginal = audioOriginal;
		this.tipoAudio = tipoAudio;
		this.visto = visto;
		this.getGenero().setDescricao(descricaoGenero);
		
		try
		{
			this.data = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(data).getTime());
		}
		catch(Exception ex)
		{}
	}
	
	public Filme(int id, String titulo, String visto, String descricaoGenero)
	{
		this.id = id;
		this.titulo = titulo;
		this.visto = visto;
		this.getGenero().setDescricao(descricaoGenero);
	}
	
	public String getVisto() {
		return visto;
	}
	public void setVisto(String visto) {
		this.visto = visto;
	}
	public int getId() {
		return id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public String getAudioOriginal() {
		return audioOriginal;
	}
	public void setAudioOriginal(String audioOriginal) {
		this.audioOriginal = audioOriginal;
	}
	public String getTipoAudio() {
		return tipoAudio;
	}
	public void setTipoAudio(String tipoAudio) {
		this.tipoAudio = tipoAudio;
	}
	public String getSinopse() {
		return sinopse;
	}
	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}
	public Genero getGenero() {
		
		if(genero == null)
		{
			genero = new Genero();
		}
		
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
}
