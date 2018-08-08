package br.com.sistema.repository;

import br.com.sistema.model.Historico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ericsson
 */
public class HistoricoRepository {

    private final EntityManager manager;

    public HistoricoRepository(EntityManager manager) {
        this.manager = manager;
    }

    /* Adiciona um novo Historico */
    public void adiciona(Historico historico) {
        this.manager.persist(historico);
    }

    /* Remove o Historico */
    public void remove(Long id) {
        Historico historico = this.procuraPorIdHistorico(id);
        this.manager.remove(historico);
    }

    /* Atualiza o Historico */
    public Historico atualiza(Historico historico) {
        return this.manager.merge(historico);
    }

    /* Procurar um Historico */
    public Historico procuraPorIdHistorico(Long idHistorico) {
        return this.manager.find(Historico.class, idHistorico);
    }

    /* Procurar um Historico */
    public Historico procurarPorObrigacao(String nomeObrigacao) {
        Historico historico = new Historico();
        TypedQuery<Historico> q = this.manager.createQuery("select h FROM Historico h where h.nomeObrigacao = :nomeObrigacao", Historico.class);
        q.setParameter("nomeObrigacao", nomeObrigacao);

        try {
            List<Historico> listaHistorico = q.getResultList();
            if (listaHistorico.isEmpty()) {
                return null;
            } else {
                return listaHistorico.get(0);
            }

        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Lista os Historicos por Obrigação */
    @SuppressWarnings("unchecked")
    public List<Historico> listaPorObrigacao(String nomeObrigacao) {
        TypedQuery<Historico> q = this.manager.createQuery("select h FROM Historico h where h.nomeObrigacao = :nomeObrigacao", Historico.class);
        q.setParameter("nomeObrigacao", nomeObrigacao);

        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Procura um Historico por Data do prazo Limite */
    public Historico procurarPorPrazoLimite(String prazo, String nomeObrigacao) {
        TypedQuery<Historico> q = this.manager.createQuery("select h FROM Historico h where h.prazo = :prazo and h.nomeObrigacao = :nomeObrigacao", Historico.class);
        q.setParameter("prazo", prazo);
        q.setParameter("nomeObrigacao", nomeObrigacao);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Lista todas os Historicos */
    @SuppressWarnings("unchecked")
    public List<Historico> getLista() {
        Query query = this.manager.createQuery("SELECT h FROM Historico h");
        return query.getResultList();
    }
}
