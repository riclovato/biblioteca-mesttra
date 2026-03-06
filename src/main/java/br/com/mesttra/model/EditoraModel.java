package br.com.mesttra.model;

public class EditoraModel {
    private Integer id;
    private String editora;
    private String cnpj;
    private String email;
    private String telefone;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String endereco;
    private String nacionalidade;
    private String enderecoWeb;
    
    public EditoraModel() {}

    
    
    public EditoraModel(Integer id, String editora, String cnpj, String email, String telefone, String cep,
            String estado, String cidade, String bairro, String endereco, String nacionalidade, String enderecoWeb) {
        this.id = id;
        this.editora = editora;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.endereco = endereco;
        this.nacionalidade = nacionalidade;
        this.enderecoWeb = enderecoWeb;
    }



    public EditoraModel(String editora, String cnpj, String email, String telefone, String cep, String estado,
            String bairro, String endereco, String nacionalidade, String enderecoWeb) {
        this.editora = editora;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.cep = cep;
        this.estado = estado;
        this.bairro = bairro;
        this.endereco = endereco;
        this.nacionalidade = nacionalidade;
        this.enderecoWeb = enderecoWeb;
    }



    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }



    public String getEditora() {
        return editora;
    }



    public void setEditora(String editora) {
        this.editora = editora;
    }



    public String getCnpj() {
        return cnpj;
    }



    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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



    public String getCep() {
        return cep;
    }



    public void setCep(String cep) {
        this.cep = cep;
    }



    public String getEstado() {
        return estado;
    }



    public void setEstado(String estado) {
        this.estado = estado;
    }



    public String getCidade() {
        return cidade;
    }



    public void setCidade(String cidade) {
        this.cidade = cidade;
    }



    public String getBairro() {
        return bairro;
    }



    public void setBairro(String bairro) {
        this.bairro = bairro;
    }



    public String getEndereco() {
        return endereco;
    }



    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EditoraModel{");
        sb.append("id=").append(id);
        sb.append(", editora=").append(editora);
        sb.append(", cnpj=").append(cnpj);
        sb.append(", email=").append(email);
        sb.append(", telefone=").append(telefone);
        sb.append(", cep=").append(cep);
        sb.append(", estado=").append(estado);
        sb.append(", cidade=").append(cidade);
        sb.append(", bairro=").append(bairro);
        sb.append(", endereco=").append(endereco);
        sb.append(", nacionalidade=").append(nacionalidade);
        sb.append(", enderecoWeb=").append(enderecoWeb);
        sb.append('}');
        return sb.toString();
    }
    
    
}
