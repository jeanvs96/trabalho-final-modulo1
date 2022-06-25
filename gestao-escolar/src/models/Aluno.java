package models;

public class Aluno extends Pessoa{
    private Integer matricula;
    private Curso curso;
    private static Integer controleMatricula = 1000;

    public Aluno(String nome) {
        super(nome);
    }

    public Aluno(String nome, String telefone, String email, Endereco endereco, Integer tipo, Curso curso) {
        super(nome, telefone, email, endereco);
        this.matricula = ++controleMatricula;
        this.curso = curso;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}