package br.com.mesttra.model;

public class AutorModel {
    private Integer id;
    private String autor;
    private String pseudonimo;
    private String nacionalidade;
    private String enderecoWeb;
    private String email;
    private String telefone;

    public AutorModel() {}

    public AutorModel(String autor, String pseudonimo, String nacionalidade, String enderecoWeb, 
                    String email, String telefone) {
        this.autor = autor;
        this.pseudonimo = pseudonimo;
        this.nacionalidade = nacionalidade;
        this.enderecoWeb = enderecoWeb;
        this.email = email;
        this.telefone = telefone;
    }

    public AutorModel(Integer id, String autor, String pseudonimo, String nacionalidade, 
                    String enderecoWeb, String email, String telefone) {
        this.id = id;
        this.autor = autor;
        this.pseudonimo = pseudonimo;
        this.nacionalidade = nacionalidade;
        this.enderecoWeb = enderecoWeb;
        this.email = email;
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getPseudonimo() {
        return pseudonimo;
    }

    public void setPseudonimo(String pseudonimo) {
        this.pseudonimo = pseudonimo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getEnderecoWeb() {
        return enderecoWeb;
    }

    public void setEnderecoWeb(String enderecoWeb) {
        this.enderecoWeb = enderecoWeb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "AutorModel{" +
                "id=" + id +
                ", autor='" + autor + '\'' +
                ", pseudonimo='" + pseudonimo + '\'' +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", enderecoWeb='" + enderecoWeb + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }   

    
}
