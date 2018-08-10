package br.com.sistema.repository;

import br.com.sistema.model.Obrigacao;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ericsson
 */
public class ObrigacaoRepository {

    private final EntityManager manager;

    public ObrigacaoRepository(EntityManager manager) {
        this.manager = manager;
    }

    /* Adiciona uma nova Obrigação */
    public void adiciona(Obrigacao obrigacao) {
        this.manager.persist(obrigacao);
    }

    /* Remove a Obrigação */
    public void remove(Long id) throws IOException {
        Obrigacao obrigacao = this.procura(id);
        String nomeObrigacao = obrigacao.getNomeObrigacao();
        this.manager.remove(obrigacao);
        
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Obrigação ", nomeObrigacao + " Excluída com sucesso!!"));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().getExternalContext().redirect("editarObrigacao.xhtml");
    }

    /* Atualiza a Obrigação */
    public Obrigacao atualiza(Obrigacao obrigacao) {
        return this.manager.merge(obrigacao);
    }

    /* Procurar uma Obrigação */
    public Obrigacao procura(Long id) {
        return this.manager.find(Obrigacao.class, id);
    }

    /* Lista todas as Obrigações */
    @SuppressWarnings("unchecked")
    public List<Obrigacao> getLista() {
        Query query = this.manager.createQuery("SELECT o FROM Obrigacao o order by o.nomeObrigacao asc");
        return query.getResultList();
    }

    /* Procurar uma Obrigação */
    public Obrigacao procurarPorObrigacao(String nomeObrigacao) {
        TypedQuery<Obrigacao> q = this.manager.createQuery("select o FROM Obrigacao o where o.nomeObrigacao = :nomeObrigacao", Obrigacao.class);
        q.setParameter("nomeObrigacao", nomeObrigacao);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

}
