package br.com.mesttra.model;

import java.time.LocalDate;

public class EmprestimoModel {
    private Integer id;
    private Integer idAluno;
    private Integer idUsuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;

    public EmprestimoModel() {
    }

    public EmprestimoModel(Integer idAluno, Integer idUsuario, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
        this.idAluno = idAluno;
        this.idUsuario = idUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public EmprestimoModel(Integer id, Integer idAluno, Integer idUsuario, LocalDate dataEmprestimo,
            LocalDate dataDevolucaoPrevista) {
        this.id = id;
        this.idAluno = idAluno;
        this.idUsuario = idUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    @Override
    public String toString() {
        return "EmprestimoModel [id=" + id + ", idAluno=" + idAluno + ", idUsuario=" + idUsuario + ", dataEmprestimo="
                + dataEmprestimo + ", dataDevolucaoPrevista=" + dataDevolucaoPrevista + "]";
    }

}
