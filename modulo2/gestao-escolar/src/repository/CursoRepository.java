package repository;

import models.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoRepository implements Repositorio<Integer, Curso> {
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT VEMSER_JEAN.SEQ_CURSO.nextval mysequence FROM DUAL";
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery((sql));

            if (res.next()) {
                return res.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        }
    }

    @Override
    public Curso adicionar(Curso curso) throws SQLException {
        Connection con = null;
        int posicao = 0;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoID = this.getProximoId(con);
            curso.setIdCurso(proximoID);

            StringBuilder sql = new StringBuilder();

            sql.append("INSERT INTO CURSO (ID_CURSO, NOME)");
            sql.append("\n VALUES (?, ?)");

            PreparedStatement statement = con.prepareStatement(sql.toString());

            statement.setInt(1, curso.getIdCurso());
            statement.setString(2, curso.getNome());

            statement.executeUpdate();
            return curso;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean remover(Integer id) throws SQLException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM CURSO WHERE ID_CURSO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.execute();
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.getCause();
            }
        }
    }

    @Override
    public boolean editar(Integer id, Curso curso) throws SQLException {
        Connection con = null;
        try {
            StringBuilder sql = new StringBuilder();
            int index = 1;
            int res = 0;
            con = ConexaoBancoDeDados.getConnection();

            sql.append("UPDATE CURSO \n");

            if (curso.getNome() != null) {
                sql.append("SET NOME = ?");
            }

            sql.append(" WHERE ID_CURSO = ? ");

            PreparedStatement statement = con.prepareStatement(sql.toString());

            if (curso.getNome() != null) {
                statement.setString(1, curso.getNome());
            }

            statement.setInt(2, curso.getIdCurso());

            res = statement.executeUpdate();

            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Curso> listar() throws SQLException {
        List<Curso> cursos = new ArrayList<>();

        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CURSO";

            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                cursos.add(getCursoFromResultSet(res));
            }

            return cursos;
        } catch (SQLException e) {
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Curso getCursoFromResultSet(ResultSet res) throws SQLException {
        Curso curso = new Curso();
        curso.setIdCurso(res.getInt("ID_CURSO"));
        curso.setNome(res.getString("NOME"));
        return curso;
    }

    public Boolean conferirIdCurso(Integer id) throws SQLException {
        Connection con = null;
        Boolean controle = false;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM CURSO WHERE ID_CURSO = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            controle = res.next();
            return controle;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
