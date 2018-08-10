package br.com.sistema.util;

import br.com.sistema.control.ObrigacaoMB;
import br.com.sistema.model.Prazo;
import br.com.sistema.model.Usuario;
import br.com.sistema.repository.PrazoRepository;
import br.com.sistema.repository.UsuarioRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author ericsson
 */
@WebFilter(servletNames = {"Faces Servlet"})
public class JPAUtil implements Filter {

    private EntityManagerFactory factory;
    private List<Prazo> listaPrazo = new ArrayList<>();
    private int cont = 0;
    Timer timer = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.factory = Persistence
                .createEntityManagerFactory("enviosANS");
    }

    @Override
    public void destroy() {
        this.factory.close();
        
        if (timer != null) {
            timer.cancel();
        }
        System.out.println(" -- TIMER FINALIZADO FIM SESSÃO --");
    }

    /* Instancia o Entity Manager*/
    public EntityManager getManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // CHEGADA
        EntityManager manager = this.factory.createEntityManager();
        request.setAttribute("EntityManager", manager);

        EntityManager em = (EntityManager) request.getAttribute("EntityManager");

        /* Verifica a cada 24 horas se tem Obrigação chegando ao prazo */
        timerEmail();

        manager.getTransaction().begin();
        // CHEGADA

        // FACES SERVLET
        chain.doFilter(request, response);
        // FACES SERVLET
        // SAIDA
        try {
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
        // SAIDA
    }

    /* Verifica a cada 24 horas se tem Obrigação chegando ao prazo */
    public void timerEmail() {

        timer = new Timer();

        java.util.Date agora = Calendar.getInstance().getTime();
        System.out.println(" AGORA É : " + agora);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 8); // 08:00:00 no servidor de desenvolvimento 
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.AM_PM, Calendar.AM); // Caso não for horario com 24 horas
        java.util.Date horaAgendada = c.getTime();
        System.out.println(" HORA AGENDADA :" + horaAgendada);
        System.out.println(" JÁ EXECUTADO :" + agora.after(horaAgendada)); // true or false 

        cont++;
        if (cont == 1) {
            if (!agora.after(horaAgendada)) { // se hora agendada ainda não chegou então não executa o método senão executa.
                long delay = horaAgendada.getTime() - agora.getTime();
                long period = 86400000;

                System.out.println(" NO IF :" + delay);

                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {

                        cont = 1;
                        if (cont == 1) {
                            java.util.Date hj = Calendar.getInstance().getTime();
                            System.out.println("TASK MANAGER executa diariamente verificaPrazo() hoje é: " + hj);
                            verificaPrazo();
                        }
                        cont++;
                    }
                }, delay, period);
            }
        }

    }

    /* Verifica Prazo de Envio*/
    public void verificaPrazo() {
        System.out.println("Entrou método verificaPrazo()");
        if (this.listaPrazo == null || this.listaPrazo.isEmpty()) {
            obtemListaObrigacoes();
        }
        for (Prazo prazo : this.listaPrazo) {

            String ano = prazo.getPrazoLimite().substring(6);
            String mes = prazo.getPrazoLimite().substring(3, 5);
            String dia = prazo.getPrazoLimite().substring(0, 2);

            DateTime dataHoje = new DateTime();
            DateTime dataAntes = new DateTime(Integer.valueOf(ano), Integer.valueOf(mes), Integer.valueOf(dia), 0, 0, 0, 0);
            Days d = Days.daysBetween(dataHoje, dataAntes);
            int days = d.getDays();

            if (days == 5 && (prazo.getDataEnvio() == null || prazo.getDataEnvio().equals(""))) {
                try {
                    enviaEmail(prazo);
                } catch (EmailException | MalformedURLException | MessagingException ex) {
                    Logger.getLogger(ObrigacaoMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    /* Enviar email*/
    public void enviaEmail(Prazo prazo) throws EmailException, EmailException, MalformedURLException, AddressException, MessagingException {
        Usuario user = new Usuario();
        SimpleEmail email = new SimpleEmail();
        email.setHostName("email855");
        email.setSslSmtpPort("587");
        try {
            if (user.getEmailResp() != null) {
                StringBuilder msg = new StringBuilder();
                msg.append(" Prazo de Envio da Obrigação ANS está chegando. ");
                msg.append("\n\n");
                msg.append("Obrigação: ").append(prazo.getNomeObrigacao().toUpperCase());
                msg.append("\n\n");
                msg.append("Prazo de Envio: ").append(prazo.getPrazoLimite());
                msg.append("\nResponsável: ").append(prazo.getResponsavel());
                msg.append("\n\n");
                msg.append("Link para o Envio: http://srvad02:6060/enviosANS");
                msg.append("\n\n");
                msg.append("Email Automático. Não responder.");

                email.setFrom("Unicop <no-reply@unimedcop.coop.br>");
                email.setDebug(true);

                email.setSubject("Prazo de Envio Obrigação ANS - " + prazo.getNomeObrigacao());
                email.setMsg(msg.toString());
                email.addTo("ericsson@unimedcop.coop.br");

                email.send();
            }
        } catch (EmailException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Lista todas as Obrigações*/
    public List<Prazo> obtemListaObrigacoes() {
        EntityManager manager = this.factory.createEntityManager();

        manager.getTransaction().begin();
        PrazoRepository repository = new PrazoRepository(manager);

        if (this.listaPrazo == null || this.listaPrazo.isEmpty()) {
            this.listaPrazo = repository.getLista();
        }
        return this.listaPrazo;
    }

    /* Busca o Usuário para setar o email*/
    public Usuario obtemUsuario(String responsavel) {
        EntityManager manager = this.factory.createEntityManager();

        manager.getTransaction().begin();
        UsuarioRepository repository = new UsuarioRepository(manager);

        return repository.procurarPorResponsavel(responsavel);
    }

}
