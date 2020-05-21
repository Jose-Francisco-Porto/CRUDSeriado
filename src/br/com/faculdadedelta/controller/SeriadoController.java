package br.com.faculdadedelta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.faculdadedelta.dao.SerieDAO;
import br.com.faculdadedelta.modelo.Genero;
import br.com.faculdadedelta.modelo.Seriado;
import br.com.faculdadedelta.modelo.Status;

@ManagedBean
@SessionScoped
public class SeriadoController {

	private Seriado seriado = new Seriado();
	private SerieDAO dao = new SerieDAO();
	private Genero generoSelecionado = new Genero();
	private Status statusSelecionado = new Status();

	public Seriado getSeriado() {
		return seriado;
	}

	public void setSeriado(Seriado seriado) {
		this.seriado = seriado;
	}

	public Genero getGeneroSelecionado() {
		return generoSelecionado;
	}

	public void setGeneroSelecionado(Genero generoSelecionado) {
		this.generoSelecionado = generoSelecionado;
	}

	public Status getStatusSelecionado() {
		return statusSelecionado;
	}

	public void setStatusSelecionado(Status statusSelecionado) {
		this.statusSelecionado = statusSelecionado;
	}

	public void limparCampos() {
		seriado = new Seriado();
		generoSelecionado = new Genero();
		statusSelecionado = new Status();
	}

	private void exibirMensagem(String mensagem) {
		FacesMessage msg = new FacesMessage(mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String salvar() {
		try {
			if (seriado.getId() == null) {
				seriado.setGenero(generoSelecionado);
				seriado.setStatus(statusSelecionado);
				dao.incluir(seriado);
				exibirMensagem("Inclusão realizada com sucesso!");
				limparCampos();
			} else {
				seriado.setGenero(generoSelecionado);
				seriado.setStatus(statusSelecionado);
				dao.alterar(seriado);
				exibirMensagem("Alteração realizada com sucesso!");
				limparCampos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, " + "tente novamente mais tarde! " + e.getMessage());
		}
		return "cadastroSeriado.xhtml";
	}

	public String editar() {
		generoSelecionado = seriado.getGenero();
		statusSelecionado = seriado.getStatus();
		return "cadastroSeriado.xhtml";
	}

	public String excluir() {
		try {
			dao.excluir(seriado);
			exibirMensagem("Exclusão realizada com sucesso!");
			limparCampos();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, " + "tente novamente mais tarde! " + e.getMessage());
		}
		return "listaSeriado.xhtml";
	}

	public List<Seriado> getLista() {
		List<Seriado> listaRetorno = new ArrayList<>();
		try {
			listaRetorno = dao.listar();
		} catch (Exception e) {
			e.printStackTrace();
			exibirMensagem("Erro ao realizar a operação, " + "tente novamente mais tarde! " + e.getMessage());
		}
		return listaRetorno;
	}

}
