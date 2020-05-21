package br.com.faculdadedelta.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.faculdadedelta.modelo.Genero;

import br.com.faculdadedelta.modelo.Seriado;
import br.com.faculdadedelta.modelo.Status;
import br.com.faculdadedelta.util.Conexao;
import javafx.scene.chart.XYChart.Series;

public class SerieDAO {

	public void incluir(Seriado seriado) throws Exception {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "INSERT INTO series (id_genero, id_status, nome, comentario, nota_avaliacao) VALUES (?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, seriado.getGenero().getId());
			ps.setLong(2, seriado.getStatus().getId());
			ps.setString(3, seriado.getNome().trim());
			ps.setString(4, seriado.getComentario().trim());
			ps.setDouble(5, seriado.getNota());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}

	public void alterar(Seriado seriado) throws Exception {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "UPDATE series SET id_genero = ?, id_status = ?, nome= ?, comentario = ?, nota_avaliacao = ? "
				+ "WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, seriado.getGenero().getId());
			ps.setLong(2, seriado.getStatus().getId());
			ps.setString(3, seriado.getNome().trim());
			ps.setString(4, seriado.getComentario().trim());
			ps.setDouble(5, seriado.getNota());
			ps.setLong(6, seriado.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}
	
	public Seriado pesquisarSeriePorNome(String nome) throws Exception{
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = null;
		Seriado retorno = null;
		try {
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if (rs.next()) {
				retorno = new Seriado();
				retorno.setNome(nome);
				
			}
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		return retorno;
	}

	public void excluir(Seriado seriado) throws Exception {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "DELETE FROM series WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setLong(1, seriado.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			Conexao.fecharConexao(conn, ps, null);
		}
	}

	public List<Seriado> listar() throws Exception {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT s.id AS idSeriado, s.nome AS nomeSeriado, s.comentario AS comentarioSeriado, s.nota_avaliacao AS notaSeriado,"
				+ "g.id AS idGenero, g.descricao AS descricaoGenero,"
				+ "st.id AS idStatus, st.descricao AS descricaoStatus "
				+ "FROM series s "
				+ "INNER JOIN genero g ON  s.id_genero = g.id "
				+ "INNER JOIN status st ON s.id_status = st.id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Seriado> listaRetorno = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Seriado seriado = new Seriado();
				seriado.setId(rs.getLong("idSeriado"));
				seriado.setNome(rs.getString("nomeSeriado"));
				seriado.setComentario(rs.getString("comentarioSeriado"));
				seriado.setNota(rs.getDouble("notaSeriado"));

				Genero genero = new Genero();
				genero.setId(rs.getLong("idGenero"));
				genero.setDescricao(rs.getString("descricaoGenero").trim());

				Status status = new Status();
				status.setId(rs.getLong("idStatus"));
				status.setDescricao(rs.getString("descricaoStatus").trim());

				seriado.setGenero(genero);
				seriado.setStatus(status);
				listaRetorno.add(seriado);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			Conexao.fecharConexao(conn, ps, rs);
		}
		return listaRetorno;
	}
}
