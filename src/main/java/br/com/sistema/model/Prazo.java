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
@Table(name="Prazo")
public class Prazo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrazo;
    
    private Long idObrigacao;
    private String nomeObrigacao;
    private String fundamentacao;
    private String competencia;
    private String prazoLimite;
    private String dataEnvio;
    private String setor;
    private String responsavel;
    private String penalidade;
    private String observacoes;

    public Long getIdPrazo() {
        return idPrazo;
    }

    public void setIdPrazo(Long idPrazo) {
        this.idPrazo = idPrazo;
    }

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

    public String getFundamentacao() {
        return fundamentacao;
    }

    public void setFundamentacao(String fundamentacao) {
        this.fundamentacao = fundamentacao;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getPrazoLimite() {
        return prazoLimite;
    }

    public void setPrazoLimite(String prazoLimite) {
        this.prazoLimite = prazoLimite;
    }

    public String getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
 
    public String getPenalidade() {
        return penalidade;
    }

    public void setPenalidade(String penalidade) {
        this.penalidade = penalidade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }


}
