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
public class Permissoes implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPermissao", nullable = false, unique = true)
    private long idPermissao;
    
    private long idObrigacao;
    
    private String nomeObrigacao;
    private String nomeUsuario;

    public long getIdPermissao() {
        return idPermissao;
    }

    public void setIdPermissao(long idPermissao) {
        this.idPermissao = idPermissao;
    }

    public long getIdObrigacao() {
        return idObrigacao;
    }

    public void setIdObrigacao(long idObrigacao) {
        this.idObrigacao = idObrigacao;
    }

    public String getNomeObrigacao() {
        return nomeObrigacao;
    }

    public void setNomeObrigacao(String nomeObrigacao) {
        this.nomeObrigacao = nomeObrigacao;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

}
