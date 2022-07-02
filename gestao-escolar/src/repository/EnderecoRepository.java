package repository;

import models.Endereco;

import java.sql.*;
import java.util.List;

public class EnderecoRepository implements Repositorio<Integer, Endereco>{
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT VEMSER_JEAN.SEQ_ENDERECO.nextval mysequence FROM DUAL";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery((sql));

            if (res.next()) {
                return  res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        }
    }

    @Override
    public Endereco adicionar(Endereco endereco) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            endereco.setIdEndereco(proximoID);

            String sql ="INSERT INTO ENDERECO (ID_ENDERECO, LOGRADOURO, NUMERO, COMPLEMENTO, CIDADE, ESTADO, CEP) \n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, endereco.getIdEndereco());
            statement.setString(2, endereco.getLogradouro());
            statement.setInt(3, endereco.getNumero());
            statement.setString(4, endereco.getComplemento());
            statement.setString(5, endereco.getCidade());
            statement.setString(6, endereco.getEstado());
            statement.setString(7, endereco.getCep());

            int res = statement.executeUpdate();
            return endereco;
        }catch (SQLException e) {
            throw new SQLException(e.getCause());
        }finally {
            try {
                if (con != null) {
                    con.close();
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws SQLException {
        return false;
    }

    @Override
    public boolean editar(Integer id, Endereco endereco) throws SQLException {
        return false;
    }

    @Override
    public List<Endereco> listar() throws SQLException {
        return null;
    }
}
