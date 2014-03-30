package br.com.sistema.util;

import java.util.Comparator;

import br.com.sistema.entity.Filme;

public class FilmeOrderByGeneroASC implements Comparator<Filme> {
	public int compare(Filme o1, Filme o2) {
		return o1.getGenero().getDescricao().compareTo(o2.getGenero().getDescricao());
	}
}
