package br.com.sistema.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ericsson
 */
@Entity
@Table(name="Obrigacao")
public class Obrigacao implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObrigacao;
    
    private String nomeObrigacao;

    public Long getIdObrigacao() {
        return idObrigacao;
    }

    public void setIdObrigacao(Long idObrigacao) {
        this.idObrigacao = idObrigacao;
    }

    public String getNomeObrigacao() {
        return nomeObrigacao;
    }

    public void setNomeObrigacao(String nomeObrigacao) {
        this.nomeObrigacao = nomeObrigacao;
    }
    
    
    
}
