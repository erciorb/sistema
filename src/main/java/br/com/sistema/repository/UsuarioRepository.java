package br.com.sistema.repository;

import br.com.sistema.model.Usuario;
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
public class UsuarioRepository {

    private final EntityManager manager;

    public UsuarioRepository(EntityManager manager) {
        this.manager = manager;
    }

    /* Adiciona um novo Prazo */
    public void adiciona(Usuario usuario) {
        this.manager.persist(usuario);
    }

    /* Remove o Prazo */
    public void remove(Long id) {
        Usuario usuario = this.procuraPorIdUsuario(id);
        this.manager.remove(usuario);
    }

    /* Atualiza o Prazo */
    public void atualiza(Usuario usuario) throws IOException{
        this.manager.merge(usuario);
        
        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Usuario: ", usuario.getNome() + " Alterado com sucesso!!"));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("editarUsuario.xhtml");
    }

    /* Procurar um Prazo */
    public Usuario procuraPorIdUsuario(Long idUsuario) {
        return this.manager.find(Usuario.class, idUsuario);
    }


    /* Lista todas os Usuario */
    @SuppressWarnings("unchecked")
    public List<Usuario> getLista() {
        Query query = this.manager.createQuery("SELECT u FROM Usuario u where u.nome <> :adm order by u.nome asc");
        query.setParameter("adm", "Administrador");
        return query.getResultList();
    }
    
    /* Procura um Usuario pelo Login */
    public Usuario procuraPorLogin(String login) {
        TypedQuery<Usuario> q = this.manager.createQuery("select u FROM Usuario u where u.login = :login", Usuario.class);
        q.setParameter("login", login);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /* Procura um Usuario pelo Nome Completo */
    public Usuario procuraPorNomeUsuario(String nomeUsuario) {
        TypedQuery<Usuario> q = this.manager.createQuery("select u FROM Usuario u where u.nome = :nomeUsuario", Usuario.class);
        q.setParameter("nomeUsuario", nomeUsuario);

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario procurarPorResponsavel(String responsavel) {
      TypedQuery<Usuario> q = this.manager.createQuery("select u FROM Usuario u where u.nome like :responsavel", Usuario.class);
        q.setParameter("responsavel", "%"+responsavel+"%");

        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }  
    }
  
}
