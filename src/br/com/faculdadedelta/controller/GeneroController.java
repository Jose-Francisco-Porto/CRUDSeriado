package br.com.faculdadedelta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.faculdadedelta.dao.GeneroDAO;
import br.com.faculdadedelta.modelo.Genero;

@ManagedBean
@SessionScoped
public class GeneroController {
	
	private Genero genero = new Genero();
	private GeneroDAO dao = new GeneroDAO();
	
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public void limparCampos() {
		genero = new Genero();
		
	}
	private void exibirMensagem(String mensagem) {
		FacesMessage msg = new FacesMessage(mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String salvar() {
		try {
			if (genero.getId() == null) {
				dao.incluir(genero);
				exibirMensagem("Inclusão realizada com sucesso!");
				limparCampos();
			} else {
				dao.alterar(genero);
				exibirMensagem("Alteração realizada com sucesso!");
				limparCampos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, " + "tente novamente mais tarde! " + e.getMessage());
		}
		return "cadastroGenero.xhtml";
	}

	public String editar() {
		return "cadastroGenero.xhtml";
	}

	public String excluir() {
		try {
			dao.excluir(genero);
			exibirMensagem("Exclusão realizada com sucesso!");
			limparCampos();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, " + "tente novamente mais tarde! " + e.getMessage());
		}
		return "listaGenero.xhtml";
	}

	public List<Genero> getLista() {
		List<Genero> listaRetorno = new ArrayList<>();
		try {
			listaRetorno = dao.listar();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, " + "tente novamente mais tarde! " + e.getMessage());
		}
		return listaRetorno;
	}
	
}

