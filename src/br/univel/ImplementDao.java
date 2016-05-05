package br.univel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ImplementDao implements Dao<Cliente, Integer> {

	private PreparedStatement ps;
	private ResultSet resultado;
	private ImplementSql ex = new ImplementSql();
	private Connection con = ex.abrirconexcao();
	private List<Cliente> lista;

	@Override
	public void salvar(Cliente t) {
		try {
			ps = ex.getSqlInsert(con, t);
			ps.executeUpdate();
			ps.close();
			con.close();
			System.out.println("ID: \t\t\t" + t.getId());
			System.out.println("NOME: \t\t\t" + t.getNomeCliente());
			System.out.println("ENDERECO: \t\t" + t.getEnd());
			System.out.println("TELEFONE: \t\t" + t.getTelefone());
			System.out.println("ESTADO CIVIL: \t\t" + t.getEstadocivil());
			System.out.println();
			System.out.println("Cadastro Salvo!\n");
			System.out.println("------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Cliente buscar(Integer k) {
		 try {
	            Cliente c = new Cliente();
	            ps = ex.getSqlSelectById(con, c, k);
	            resultado = ps.executeQuery();
	            resultado.next();

	            EstadoCivil estcivil = EstadoCivil.values()[resultado.getInt("CadEstadoCivil")];
	            c = (new Cliente(resultado.getInt("Cadid"), resultado.getString("CadNome"),
                     resultado.getString("CadEnd"), resultado.getString("CadTelefone"), estcivil));

	            ps.close();
	            resultado.close();
	            con.close();
	            return c;

	        } catch (SQLException e) {
	            e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public void atualizar(Cliente t) {
		try {
            ps = ex.getSqlUpdateById(con, t, t.getId());
            ps.executeUpdate();
            ps.close();
            con.close();
            System.out.println("ID: \t\t\t" + t.getId());
	        System.out.println("NOME: \t\t\t" + t.getNomeCliente());
	        System.out.println("ENDERECO: \t\t" + t.getEnd());
	        System.out.println("TELEFONE: \t\t" + t.getTelefone());
	        System.out.println("ESTADO CIVIL: \t\t" + t.getEstadocivil());
            System.out.println("\nCadastro atualizado!");
	        System.out.println("------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void excluir(Integer k) {
		  try {
	            Cliente c = new Cliente();
	            ps = ex.getSqlDeleteById(con, c, k);
	            ps.executeUpdate();
	            ps.close();
	            con.close();
	            System.out.println("\nCadastro excluido!");
		        System.out.println("------------------------------------------------");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	}

	@Override
	public List<Cliente> listarTodos() {
        try {
            Cliente cl = new Cliente();
            lista = new LinkedList<Cliente>();
            ps = ex.getSqlSelectAll(con, cl);
            resultado = ps.executeQuery();

            while (resultado.next()) {
                EstadoCivil estcivil = EstadoCivil.values()[resultado.getInt("CadEstadoCivil")];
                
                lista.add(new Cliente(resultado.getInt("Cadid"), resultado.getString("CadNome"),
                        resultado.getString("CadEnd"), resultado.getString("CadTelefone"), estcivil));
            }

            ps.close();
            resultado.close();

            if (lista != null) {
                return lista;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
   
}
	}


