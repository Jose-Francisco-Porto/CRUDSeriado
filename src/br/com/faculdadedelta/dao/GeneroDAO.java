package br.com.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.faculdadedelta.modelo.Genero;
import br.com.faculdadedelta.util.Conexao;

public class GeneroDAO {

	public void incluir(Genero genero) throws ClassNotFoundException, SQLException{
		Connection conn =  Conexao.conectarNoBancoDeDados();
		String sql = "INSERT INTO genero (descricao) VALUES (?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, genero.getDescricao().trim());
		
		ps.executeUpdate();

		Conexao.fecharConexao(conn, ps, null);
	}
	public void alterar(Genero genero) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "UPDATE genero SET descricao = ? WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, genero.getDescricao().trim());
		ps.setLong(2, genero.getId());
		
		ps.executeUpdate();
		
		Conexao.fecharConexao(conn, ps, null);
	}
	
	public void excluir(Genero genero) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "DELETE FROM genero WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setLong(1, genero.getId());
		
		ps.executeUpdate();
		
		Conexao.fecharConexao(conn, ps, null);
		
	}
	public Genero pesquisarPorId(Long id) 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT id, descricao FROM genero WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		
		ResultSet rs = ps.executeQuery();
		Genero retorno = new Genero();
		if (rs.next()) {
			retorno = popularGenero(rs);
		}
		Conexao.fecharConexao(conn, ps, rs);
		
		return retorno;
	}
	public List<Genero> listar() 
			throws ClassNotFoundException, SQLException {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "SELECT id, descricao FROM genero";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		List<Genero> listaRetorno = new ArrayList<>();
		
		while(rs.next()) {
			listaRetorno.add(popularGenero(rs));
		}
		
		Conexao.fecharConexao(conn, ps, rs);
		
		return listaRetorno;
	}
	
	private Genero popularGenero(ResultSet rs) throws SQLException {
		
		Genero genero = new Genero();
		genero.setId(rs.getLong("id"));
		genero.setDescricao(rs.getString("descricao").trim());
		
		
		
		return genero;
	}
}