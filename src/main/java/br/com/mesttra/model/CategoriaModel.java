package br.com.mesttra.model;

public class CategoriaModel {
    private Integer id;
    private String categoria;
    private String descricao;

    public CategoriaModel() {}

    public CategoriaModel(Integer id, String categoria, String descricao) {
        this.id = id;
        this.categoria = categoria;
        this.descricao = descricao;
    }

    public CategoriaModel(String categoria, String descricao) {
        this.categoria = categoria;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "CategoriaModel [id=" + id + ", categoria=" + categoria + ", descricao=" + descricao + "]";
    }
    
    
}
