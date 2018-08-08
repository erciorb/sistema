package br.com.sistema.repository;

import br.com.sistema.model.Prazo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ericsson
 */
public class PrazoRepository {

    private final EntityManager manager;

    public PrazoRepository(EntityManager manager) {
        this.manager = manager;
    }

    /* Adiciona um novo Prazo */
    public void adiciona(Prazo prazo) {
        this.manager.persist(prazo);
    }

    /* Remove o Prazo */
    public void remove(Long id) {
        Prazo prazo = this.procuraPorIdPrazo(id);
        this.manager.remove(prazo);
    }

    /* Atualiza o Prazo */
    public Prazo atualiza(Prazo prazo) {
        return this.manager.merge(prazo);
    }

    /* Procurar um Prazo */
    public Prazo procuraPorIdPrazo(Long idPrazo) {
        return this.manager.find(Prazo.class, idPrazo);
    }

    /* Procurar um Prazo */
    public Prazo procurarPorObrigacao(String nomeObrigacao) {
        Prazo prazo = new Prazo();
        TypedQuery<Prazo> q = this.manager.createQuery("select p FROM Prazo p where p.nomeObrigacao = :nomeObrigacao", Prazo.class);
        q.setParameter("nomeObrigacao", nomeObrigacao);

        try {
            List<Prazo> listaPrazos = q.getResultList();
            if (listaPrazos.isEmpty()) {
                return null;
            } else {
                return listaPrazos.get(0);
            }

        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Lista os Prazo por Obrigação */
    @SuppressWarnings("unchecked")
    public List<Prazo> listaPorObrigacao(String nomeObrigacao) {
        TypedQuery<Prazo> q = this.manager.createQuery("select p FROM Prazo p where p.nomeObrigacao = :nomeObrigacao", Prazo.class);
        q.setParameter("nomeObrigacao", nomeObrigacao);

        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Procura um Prazo por Data do prazo Limite */
    public Prazo procurarPorPrazoLimite(String prazoLimite, String nomeObrigacao) {
        TypedQuery<Prazo> q = this.manager.createQuery("select p FROM Prazo p where p.prazoLimite = :prazoLimite and p.nomeObrigacao = :nomeObrigacao", Prazo.class);
        q.setParameter("prazoLimite", prazoLimite);
        q.setParameter("nomeObrigacao", nomeObrigacao);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* Lista todas os Prazos */
    @SuppressWarnings("unchecked")
    public List<Prazo> getLista() {
        Query query = this.manager.createQuery("SELECT p FROM Prazo p");
        return query.getResultList();
    }
}
