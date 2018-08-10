package br.com.sistema.repository;

import br.com.sistema.model.Permissoes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ericsson
 */
public class PermissoesRepository {

    private final EntityManager manager;

    public PermissoesRepository(EntityManager manager) {
        this.manager = manager;
    }

    /* Adiciona uma nova Permissão */
    public void adiciona(Permissoes permissoes) {
        this.manager.persist(permissoes);
    }

    /* Remove a Permissao */
    public void remove(Long id) {
        Permissoes permissoes = this.procuraPorIdPermissoes(id);
        this.manager.remove(permissoes);
    }

    /* Atualiza o Prazo */
    public Permissoes atualiza(Permissoes permissoes) {
        return this.manager.merge(permissoes);
    }

    /* Procurar um Prazo */
    public Permissoes procuraPorIdPermissoes(Long idPermissoes) {
        return this.manager.find(Permissoes.class, idPermissoes);
    }


    /* Lista todas as Permissoes */
    @SuppressWarnings("unchecked")
    public List<Permissoes> getLista() {
        Query query = this.manager.createQuery("SELECT p FROM Permissoes p order by p.nomeObrigacao asc");
        return query.getResultList();
    }
    
     /* Lista todas as Permissoes pelo nome do Usuário */
    @SuppressWarnings("unchecked")
    public List<Permissoes> getListaPorNomeUsuario(String nomeUsuario) {
        TypedQuery<Permissoes> q = this.manager.createQuery("SELECT p FROM Permissoes p where p.nomeUsuario = :nomeUsuario order by p.nomeObrigacao asc", Permissoes.class);
        q.setParameter("nomeUsuario", nomeUsuario);

        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
    
     /* Lista todas as Permissoes pelo nome da Obrigacao */
    @SuppressWarnings("unchecked")
    public List<Permissoes> getListaPorNomeObrigacao(String nomeObrigacao) {
        TypedQuery<Permissoes> q = this.manager.createQuery("SELECT p FROM Permissoes p where p.nomeObrigacao = :nomeObrigacao order by p.nomeObrigacao asc", Permissoes.class);
        q.setParameter("nomeObrigacao", nomeObrigacao);

        try {
            return q.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
     
}
