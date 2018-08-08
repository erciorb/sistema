package br.com.sistema.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ericsson
 */
@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUsuario", nullable = false, unique = true)
    private Long idUsuario;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "login", nullable = false, unique = false)
    private String login;

    @Column(name = "senha", nullable = false, unique = false)
    private String senha;
    
    @Column(name = "emailResp", nullable = false, unique = false)
    private String  emailResp;

    @Column(name = "ultimoAcesso", nullable = true, unique = false)
    private String ultimoAcesso;
    
    private String tipoUsuario;

    public Usuario() {
    }

    public Usuario(String nome, String login, String senha, String ultimoAcesso) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.ultimoAcesso = ultimoAcesso;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmailResp() {
        return emailResp;
    }

    public void setEmailResp(String emailResp) {
        this.emailResp = emailResp;
    }

    public String getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(String ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

}
