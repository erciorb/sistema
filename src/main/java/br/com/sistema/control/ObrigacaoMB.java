package br.com.sistema.control;

import br.com.sistema.model.Historico;
import br.com.sistema.model.Obrigacao;
import br.com.sistema.model.Permissoes;
import br.com.sistema.model.Prazo;
import br.com.sistema.model.Usuario;
import br.com.sistema.repository.HistoricoRepository;
import br.com.sistema.repository.ObrigacaoRepository;
import br.com.sistema.repository.PermissoesRepository;
import br.com.sistema.repository.PrazoRepository;
import br.com.sistema.repository.UsuarioRepository;
import br.com.sistema.util.ConverterSenhaMD5;
import java.awt.Font;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author ericsson
 */
@ManagedBean
@ViewScoped
public class ObrigacaoMB {

    private Obrigacao obrigacao = new Obrigacao();
    private Prazo prazo = new Prazo();
    private Usuario usuario = new Usuario();
    private Usuario usuarioEditar = new Usuario();
    ConverterSenhaMD5 md5 = new ConverterSenhaMD5();

    private String msgErro;
    private String nomeUsuario;
    private String login;
    private String senha;
    private String ultimoAcesso;
    private Long idUser;
    private String novoNome;
    private String novoLogin;
    private String novaSenha;
    private String novoEmailResp;
    private String tipoUsuario;

    private String nomeObrigacao = "";
    private String obrigacaoSelecionada = "";
    private String prazoSelecionado = "";
    private String usuarioSelecionado = "";

    private List<Obrigacao> listaObrigacoes = new ArrayList<>();
    private List<String> listaObrigacoesExistente = new ArrayList<>();
    private List<Prazo> listaPrazo = new ArrayList<>();
    private List<Usuario> listaUsuario = new ArrayList<>();
    private List<String> listaResponsaveis = new ArrayList<>();
    private List<Permissoes> listaPermissoes = new ArrayList<>();
    private List<String> listaObrigacoesSelecionadas = new ArrayList<>();
    private List<String> listaResponsaveisSelecionados = new ArrayList<>();

    private boolean novaObrig = Boolean.FALSE;
    private boolean usuarioEncontrado = Boolean.FALSE;

    @PreDestroy()
    public void releaseConnection() {

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("telaLogin.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ObrigacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ObrigacaoMB() {
        init();
    }

    @PostConstruct
    protected void init() {
        inicializarCampos();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            this.usuario = (Usuario) session.getAttribute("usuario");
        }

    }

    public void inicializarCampos() {
        this.listaResponsaveis = new ArrayList<>();
        this.listaUsuario = getListaUsuario();
        this.listaPermissoes = getListaPermissoes();
        this.listaPrazo = obtemListaObrigacoes();
        this.listaObrigacoesExistente = getListaObrigacoesExistente();
        this.prazo = new Prazo();
        this.obrigacao = new Obrigacao();
        this.nomeObrigacao = "";
    }

    /* Login */
    public String logIn() throws UnsupportedEncodingException {
        String senhaMD5 = "";
        String retorno = "";
        Usuario user = new Usuario();
        for (Usuario usuario : this.listaUsuario) {
            senhaMD5 = this.md5.convertStringToMd5(this.senha);

            if (this.nomeUsuario.equalsIgnoreCase(usuario.getLogin()) && senhaMD5.equalsIgnoreCase(usuario.getSenha())) {
                if (usuario.getUltimoAcesso() == null) {
                    String dataAtualFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                            .format(System.currentTimeMillis());
                    user.setUltimoAcesso(dataAtualFormatada);
                    this.ultimoAcesso = user.getUltimoAcesso();
                } else {
                    this.ultimoAcesso = usuario.getUltimoAcesso();
                    user.setUltimoAcesso(this.ultimoAcesso);
                }
                user = usuario;

                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                if (session != null) {
                    EntityManager manager = this.getManager();
                    UsuarioRepository usuarioRepository = new UsuarioRepository(manager);

                    session.setAttribute("usuario", user);
                    session.setAttribute("ultimoAcesso", this.ultimoAcesso);
                    this.usuario = (Usuario) session.getAttribute("usuario");

                    String dataAtualFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                            .format(System.currentTimeMillis());

                    user.setUltimoAcesso(dataAtualFormatada);

                    //Gravar Historico
                    Historico historico = new Historico();
                    String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                            .format(System.currentTimeMillis());
                    historico.setDataAlteracao(dataAtualHistorico);
                    historico.setNomeUsuario(this.usuario.getNome());
                    historico.setTipo("Login");
                    gravarHistorico(historico);
                }
                retorno = "/index?faces-redirect=true";
                break;
            } else if (msgErro == null) {
                msgErro = "Usuário ou Senha Inválidos";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErro, "Login Inválido"));
                retorno = null;
            } else {
                retorno = null;
            }
        }

        return retorno;
    }

    /* Verifica o usuario para alterar a Senha*/
    public String verificaLoginNovaSenha(FlowEvent event) throws IOException {
        verificarUsuario();
        if (usuarioEncontrado) {
            return event.getNewStep();
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage("Usuário não encontrado"));

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("alterarSenha.xhtml");
        }
        return "";
    }

    /* Verifica o Usuário para Alterar a Senha*/
    public void verificarUsuario() {
        EntityManager manager = this.getManager();
        UsuarioRepository usuarioRepository = new UsuarioRepository(manager);
        Usuario user = new Usuario();
        user = usuarioRepository.procuraPorLogin(this.login);

        if (user != null) {
            this.novoNome = user.getNome();
            this.novoLogin = user.getLogin();
            this.novaSenha = "";
            this.idUser = user.getIdUsuario();
            this.tipoUsuario = user.getTipoUsuario();
            this.usuarioEncontrado = Boolean.TRUE;
        } else {
            this.usuarioEncontrado = Boolean.FALSE;
        }

    }

    /* Método para Alterar a senha do usuário na tela de Login através do Link: "Esqueci a senha" */
    public void alterarNovaSenha() throws IOException {
        EntityManager manager = this.getManager();
        UsuarioRepository usuarioRepository = new UsuarioRepository(manager);

        if (!isUsuarioEncontrado()) {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage("Usuário Não Encontrado"));

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
        } else {
            String senhaMD5 = "";
            senhaMD5 = this.md5.convertStringToMd5(this.novaSenha);
            Usuario user = new Usuario();
            user.setIdUsuario(this.idUser);
            user.setLogin(this.novoLogin);
            user.setNome(this.novoNome);
            user.setSenha(senhaMD5);
            user.setTipoUsuario(this.tipoUsuario);

            String dataAtualFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    .format(System.currentTimeMillis());

            user.setUltimoAcesso(dataAtualFormatada);

            usuarioRepository.atualiza(user);

            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage("Senha Alterada com Sucesso!!!"));

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("telaLogin.xhtml");

            //Gravar Historico
            Historico historico = new Historico();
            String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    .format(System.currentTimeMillis());
            historico.setDataAlteracao(dataAtualHistorico);
            historico.setNomeUsuario(this.usuario.getNome());
            historico.setTipo("Alteração de Senha do usuário: " + this.novoNome);
            gravarHistorico(historico);

            inicializarCampos();
        }
    }

    /* Adiciona um novo Usuário*/
    public String adicionaUsuario() {
        EntityManager manager = this.getManager();
        UsuarioRepository usuarioRepository = new UsuarioRepository(manager);
        PermissoesRepository permissoesRepository = new PermissoesRepository(manager);
        ObrigacaoRepository obrigacaoRepository = new ObrigacaoRepository(manager);
        List<Obrigacao> listaObrigacao = new ArrayList();

        String senhaMD5 = "";
        try {
            senhaMD5 = this.md5.convertStringToMd5(this.novaSenha);

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ObrigacaoMB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        Usuario user = new Usuario();
        user.setLogin(this.novoLogin);
        user.setNome(this.novoNome);
        user.setSenha(senhaMD5);
        user.setEmailResp(this.novoEmailResp);

        String tipoUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("TipoUsuario");

        if (tipoUsuario.equalsIgnoreCase("Admin (Acesso Total)")) {
            user.setTipoUsuario("A");
        } else {
            user.setTipoUsuario("U");
        }

        String dataAtualFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());

        user.setUltimoAcesso(dataAtualFormatada);

        usuarioRepository.adiciona(user);

        /* Lista todas as Obrigações Selecionadas */
        for (String obrigacao : this.listaObrigacoesSelecionadas) {
            listaObrigacao.add(obrigacaoRepository.procurarPorObrigacao(obrigacao));
        }

        /* Salva as permissões do usário com as suas Obrigações */
        for (Obrigacao ob : listaObrigacao) {
            Permissoes permissoes = new Permissoes();
            permissoes.setIdObrigacao(ob.getIdObrigacao());
            permissoes.setNomeObrigacao(ob.getNomeObrigacao());
            permissoes.setNomeUsuario(user.getNome());

            permissoesRepository.adiciona(permissoes);
        }

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Usuário: ", user.getNome() + " Adicionado com sucesso!!"));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);

        //Gravar Historico
        Historico historico = new Historico();
        String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());
        historico.setDataAlteracao(dataAtualHistorico);
        historico.setNomeUsuario(this.usuario.getNome());
        historico.setTipo("Adicionado Usuário: " + user.getNome());
        gravarHistorico(historico);

        inicializarCampos();

        return "cadastrarUsuario.xhtml?faces-redirect=true";
    }

    /* Adiciona uma nova Obrigação*/
    public String adicionaObrigacao() {
        this.obrigacaoSelecionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nomeObrigacao");

        Boolean admin = Boolean.FALSE;

        EntityManager manager = this.getManager();
        ObrigacaoRepository obrigacaoRepository = new ObrigacaoRepository(manager);
        PermissoesRepository permissoesRepository = new PermissoesRepository(manager);

        this.obrigacao.setNomeObrigacao(this.obrigacaoSelecionada);
        obrigacaoRepository.adiciona(this.obrigacao);

        for (String responsaveis : this.listaResponsaveisSelecionados) {
            Permissoes permissoes = new Permissoes();
            permissoes.setIdObrigacao(this.obrigacao.getIdObrigacao());
            permissoes.setNomeObrigacao(this.obrigacao.getNomeObrigacao());
            permissoes.setNomeUsuario(responsaveis);

            permissoesRepository.adiciona(permissoes);
            if (responsaveis.equalsIgnoreCase("Administrador")) {
                admin = Boolean.TRUE;
            }
        }

        if (!admin) {
            Permissoes permissoes = new Permissoes();
            permissoes.setIdObrigacao(this.obrigacao.getIdObrigacao());
            permissoes.setNomeObrigacao(this.obrigacao.getNomeObrigacao());
            permissoes.setNomeUsuario("Administrador");
            permissoesRepository.adiciona(permissoes);
        }

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Obrigação: ", this.obrigacao.getNomeObrigacao() + " Adicionada com sucesso!!"));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);

        //Gravar Historico
        Historico historico = new Historico();
        String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());
        historico.setDataAlteracao(dataAtualHistorico);
        historico.setNomeUsuario(this.usuario.getNome());
        historico.setTipo("Adicionado Obrigação");
        historico.setObrigacao(this.obrigacao.getNomeObrigacao());
        gravarHistorico(historico);

        inicializarCampos();

        return "cadastrarObrigacao.xhtml?faces-redirect=true";

    }

    /* Adiciona um novo Prazo*/
    public String adicionaPrazo() {
        EntityManager manager = this.getManager();
        ObrigacaoRepository obrigacaoRepository = new ObrigacaoRepository(manager);
        PrazoRepository prazoRepository = new PrazoRepository(manager);

        this.obrigacao = obrigacaoRepository.procurarPorObrigacao(this.prazo.getNomeObrigacao());

        this.prazo.setIdObrigacao(this.obrigacao.getIdObrigacao());

        prazoRepository.adiciona(this.prazo);

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Prazo da Obrigação: ", this.prazo.getNomeObrigacao() + " Adicionada com sucesso!!"));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);

        //Gravar Historico
        Historico historico = new Historico();
        String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());
        historico.setDataAlteracao(dataAtualHistorico);
        historico.setNomeUsuario(this.usuario.getNome());
        historico.setTipo("Adicionado Prazo de Envio");
        historico.setObrigacao(this.obrigacao.getNomeObrigacao());
        historico.setPrazo(this.prazo.getPrazoLimite());
        historico.setObservacao(this.prazo.getObservacoes());
        gravarHistorico(historico);

        inicializarCampos();

        return "cadastrarObrigacao.xhtml?faces-redirect=true";

    }

    /* Editar a Obrigação*/
    public String editarObrigacao() {
        EntityManager manager = this.getManager();
        ObrigacaoRepository obrigacaoRepository = new ObrigacaoRepository(manager);
        PrazoRepository prazoRepository = new PrazoRepository(manager);
        PermissoesRepository permissoesRepository = new PermissoesRepository(manager);
        List<Prazo> listaPorObrigacao = prazoRepository.listaPorObrigacao(this.obrigacaoSelecionada);

        this.obrigacao = obrigacaoRepository.procurarPorObrigacao(this.obrigacaoSelecionada);
        this.obrigacao.setNomeObrigacao(this.nomeObrigacao);

        obrigacaoRepository.atualiza(this.obrigacao);

        /* Atualiza os Prazos com as alterações da Obrigação */
        for (Prazo prazo : listaPorObrigacao) {
            prazo.setNomeObrigacao(this.nomeObrigacao);
            prazo.setFundamentacao(this.prazo.getFundamentacao());
            prazo.setSetor(this.prazo.getSetor());
            prazo.setResponsavel(this.prazo.getResponsavel());
            prazo.setPenalidade(this.prazo.getPenalidade());

            prazoRepository.atualiza(prazo);
        }

        List<Permissoes> listaPermissoes = permissoesRepository.getListaPorNomeObrigacao(this.prazo.getNomeObrigacao());

        /* Salva as permissões do usuário com as suas Obrigações alteradas */
        for (Permissoes permissao : listaPermissoes) {
            permissao.setNomeObrigacao(this.nomeObrigacao);

            permissoesRepository.atualiza(permissao);
        }

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Obrigação ", this.nomeObrigacao + " Alterada com sucesso!!"));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);

        //Gravar Historico
        Historico historico = new Historico();
        String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());
        historico.setDataAlteracao(dataAtualHistorico);
        historico.setNomeUsuario(this.usuario.getNome());
        historico.setTipo("Obrigação Editada");
        historico.setObrigacao(this.obrigacao.getNomeObrigacao());
        gravarHistorico(historico);

        inicializarCampos();

        return "editarObrigacao.xhtml?faces-redirect=true";

    }


    /* Lista todos os Usuarios */
    public List<Usuario> getListaUsuario() {
        if (this.listaUsuario == null || this.listaUsuario.isEmpty()) {
            EntityManager manager = this.getManager();
            UsuarioRepository repository = new UsuarioRepository(manager);
            this.listaUsuario = repository.getLista();
        }

        for (Usuario user : this.listaUsuario) {
            this.listaResponsaveis.add(user.getNome());
        }

        return this.listaUsuario;
    }

    /* Lista todos os Prazos de acordo com a obrigação selecionada no combo */
    public List<Prazo> obtemListaPrazo() {
        this.obrigacaoSelecionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Obrigaçoes");
        EntityManager manager = this.getManager();
        PrazoRepository repository = new PrazoRepository(manager);
        this.listaPrazo = repository.listaPorObrigacao(this.obrigacaoSelecionada);

        return this.listaPrazo;
    }

    /* Lista todas as Obrigações de acordo com o Usuário Logado*/
    public List<Prazo> obtemListaObrigacoes() {
        List<Prazo> listaPrazo = new ArrayList();
        EntityManager manager = this.getManager();
        PrazoRepository repository = new PrazoRepository(manager);

        if (this.listaPrazo == null || this.listaPrazo.isEmpty()) {
            this.listaPrazo = repository.getLista();
        }
        if (this.listaObrigacoesExistente == null || this.listaObrigacoesExistente.isEmpty()) {
            this.listaObrigacoesExistente = getListaObrigacoesExistente();
        }
        for (String nomeObrigacao : this.listaObrigacoesExistente) {
            for (Prazo prazo : this.listaPrazo) {
                if (nomeObrigacao.equalsIgnoreCase(prazo.getNomeObrigacao())) {
                    listaPrazo.add(prazo);
                }
            }
        }

        return listaPrazo;
    }

    public void tabelaPrazoEnvio() {
        this.prazoSelecionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Prazos");
        EntityManager manager = this.getManager();
        PrazoRepository prazoRepository = new PrazoRepository(manager);

        this.prazo = prazoRepository.procurarPorPrazoLimite(this.prazoSelecionado, this.obrigacaoSelecionada);

    }


    /* Envia a Obrigacao */
    public String enviarObrigacao() throws IOException {
        EntityManager manager = this.getManager();
        PrazoRepository repository = new PrazoRepository(manager);

        repository.atualiza(this.prazo);

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Obrigação ", this.prazo.getNomeObrigacao() + " Alterada com sucesso!!"));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);

        FacesContext ctx = FacesContext.getCurrentInstance();

        //Gravar Historico
        Historico historico = new Historico();
        String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());
        historico.setDataAlteracao(dataAtualHistorico);
        historico.setNomeUsuario(this.usuario.getNome());
        historico.setObrigacao(this.prazo.getNomeObrigacao());
        historico.setPrazo(this.prazo.getPrazoLimite());
        historico.setObservacao(this.prazo.getObservacoes());

        inicializarCampos();

        if (ctx.getViewRoot().getViewId().equals("/enviarObrigacao.xhtml")) {
            historico.setTipo("Obrigação Enviada");
            gravarHistorico(historico);
            return "enviarObrigacao.xhtml?faces-redirect=true";
        } else {
            historico.setTipo("Obrigação Editada");
            gravarHistorico(historico);
            return "editarObrigacao.xhtml?faces-redirect=true";
        }

    }

    /* Exclui um Prazo*/
    public String excluirPrazo() {
        EntityManager manager = this.getManager();
        PrazoRepository repository = new PrazoRepository(manager);

        String prazoExcluido = this.prazo.getPrazoLimite();
        String obsPrazoExcluido = this.prazo.getObservacoes();

        repository.remove(this.prazo.getIdPrazo());

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage("Prazo ", this.prazo.getPrazoLimite() + " Excluída com sucesso!!"));

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getFlash().setKeepMessages(true);

        //Gravar Historico
        Historico historico = new Historico();
        String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());
        historico.setDataAlteracao(dataAtualHistorico);
        historico.setNomeUsuario(this.usuario.getNome());
        historico.setTipo("Prazo Excluído");
        historico.setObrigacao(this.obrigacao.getNomeObrigacao());
        historico.setPrazo(prazoExcluido);
        historico.setObservacao(obsPrazoExcluido);
        gravarHistorico(historico);

        inicializarCampos();

        return ".xhtml?faces-redirect=true";
    }

    /* Exclui uma Obrigação */
    public void excluirObrigacao() {
        EntityManager manager = this.getManager();
        ObrigacaoRepository repository = new ObrigacaoRepository(manager);

        Obrigacao obrigacao = repository.procurarPorObrigacao(this.nomeObrigacao);
        try {
            repository.remove(obrigacao.getIdObrigacao());

        } catch (IOException ex) {
            Logger.getLogger(ObrigacaoMB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //Gravar Historico
        Historico historico = new Historico();
        String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());
        historico.setDataAlteracao(dataAtualHistorico);
        historico.setNomeUsuario(this.usuario.getNome());
        historico.setTipo("Obrigação Excluída");
        historico.setObrigacao(this.nomeObrigacao);
        gravarHistorico(historico);

        inicializarCampos();
    }

    public void obtemNomeObrigacaoSelecionada() {
        this.obrigacaoSelecionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ObrigaçoesEdit");
        this.nomeObrigacao = this.obrigacaoSelecionada;

        EntityManager manager = this.getManager();
        PrazoRepository prazoRepository = new PrazoRepository(manager);

        this.prazo = prazoRepository.procurarPorObrigacao(this.nomeObrigacao);

    }

    /* Obrigações Existente */
    public List<String> getListaObrigacoesExistente() {
        this.listaObrigacoesExistente = new ArrayList();

        if (this.listaPermissoes.isEmpty() || this.listaPermissoes == null) {
            getListaPermissoes();
        }

        if (this.usuario != null && this.usuario.getNome() != null) {
            /* Percorre todas as Permissões e salva o nome da Obrigação para o combo de acordo com o usuario */
            for (Permissoes permissoes : this.listaPermissoes) {

                if (this.usuario.getNome().equalsIgnoreCase(permissoes.getNomeUsuario())) {
                    listaObrigacoesExistente.add(permissoes.getNomeObrigacao());
                }
            }
        }
        return this.listaObrigacoesExistente;
    }


    /* Lista todas as Obrigações */
    public List<Obrigacao> getListaObrigacoes() {
        if (this.listaObrigacoes == null || this.listaObrigacoes.isEmpty()) {
            EntityManager manager = this.getManager();
            ObrigacaoRepository repository = new ObrigacaoRepository(manager);
            this.listaObrigacoes = repository.getLista();
        }
        return this.listaObrigacoes;
    }

    /* Lista todas as Permissoes */
    public List<Permissoes> getListaPermissoes() {
        if (this.listaPermissoes == null || this.listaPermissoes.isEmpty()) {
            EntityManager manager = this.getManager();
            PermissoesRepository repository = new PermissoesRepository(manager);
            this.listaPermissoes = repository.getLista();
        }
        return this.listaPermissoes;
    }


    /* Instancia o Entity Manager*/
    public EntityManager getManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    /* Adiciona a Obrigação Existente nos campos*/
    public void novaLinhaObrigacao() {
        EntityManager manager = this.getManager();
        PrazoRepository prazoRepository = new PrazoRepository(manager);
        this.obrigacaoSelecionada = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Obrigaçoes");

        Prazo p = prazoRepository.procurarPorObrigacao(this.obrigacaoSelecionada);

        if (p == null) {
            this.prazo = new Prazo();
            this.prazo.setNomeObrigacao(this.obrigacaoSelecionada);
        } else {
            this.prazo.setIdObrigacao(p.getIdObrigacao());
            this.prazo.setNomeObrigacao(p.getNomeObrigacao());
            this.prazo.setCompetencia("");
            this.prazo.setDataEnvio("");
            this.prazo.setFundamentacao(p.getFundamentacao());
            this.prazo.setObservacoes("");
            this.prazo.setPenalidade(p.getPenalidade());
            this.prazo.setPrazoLimite("");
            this.prazo.setResponsavel(p.getResponsavel());
            this.prazo.setSetor(p.getSetor());
        }

    }

    /* Exibe o Usuario selecionado para Edição*/
    public void exibeUsuario() {
        EntityManager manager = this.getManager();
        UsuarioRepository usuarioRepository = new UsuarioRepository(manager);
        this.usuarioSelecionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("Usuarios");

        this.usuarioEditar = usuarioRepository.procuraPorNomeUsuario(this.usuarioSelecionado);
    }

    /* Edita o Usuario selecionado*/
    public void editarUsuario() throws UnsupportedEncodingException, IOException {
        EntityManager manager = this.getManager();
        UsuarioRepository usuarioRepository = new UsuarioRepository(manager);
        PermissoesRepository permissoesRepository = new PermissoesRepository(manager);
        ObrigacaoRepository obrigacaoRepository = new ObrigacaoRepository(manager);
        List<Obrigacao> listaObrigacao = new ArrayList();

        List<Permissoes> listaPermissoes = permissoesRepository.getListaPorNomeUsuario(this.usuarioSelecionado);
        /* Remove as Permissões do Usuário para adicionar as novas */
        for (Permissoes permissoes : listaPermissoes) {
            permissoesRepository.remove(permissoes.getIdPermissao());
        }

        /* Lista todas as Obrigações Selecionadas */
        for (String obrigacao : this.listaObrigacoesSelecionadas) {
            listaObrigacao.add(obrigacaoRepository.procurarPorObrigacao(obrigacao));
        }

        /* Salva as permissões do usuário com as suas Obrigações */
        for (Obrigacao ob : listaObrigacao) {
            Permissoes permissoes = new Permissoes();
            permissoes.setIdObrigacao(ob.getIdObrigacao());
            permissoes.setNomeObrigacao(ob.getNomeObrigacao());
            permissoes.setNomeUsuario(this.usuarioEditar.getNome());

            permissoesRepository.adiciona(permissoes);
        }

        String senhaMD5 = this.md5.convertStringToMd5(this.usuarioEditar.getSenha());

        this.usuarioEditar.setSenha(senhaMD5);

        String TipoUsuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("TipoUsuario");
        if (TipoUsuario.equalsIgnoreCase("Admin (Acesso Total)")) {
            this.usuarioEditar.setTipoUsuario("A");
        } else {
            this.usuarioEditar.setTipoUsuario("U");
        }

        usuarioRepository.atualiza(this.usuarioEditar);

        //Gravar Historico
        Historico historico = new Historico();
        String dataAtualHistorico = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(System.currentTimeMillis());
        historico.setDataAlteracao(dataAtualHistorico);
        historico.setNomeUsuario(this.usuario.getNome());
        historico.setTipo("Usuário: " + this.usuarioSelecionado + " Editado");
        gravarHistorico(historico);

        inicializarCampos();
    }

    /* Grava o Histórico */
    public void gravarHistorico(Historico historico) {
        EntityManager manager = this.getManager();
        HistoricoRepository historicoRepository = new HistoricoRepository(manager);

        historicoRepository.adiciona(historico);
    }

    public void exibirPrazosModalEnviar() {
        System.out.println(nomeObrigacao);
    }

    //Limpar o formulário de cadastro de Obrigações
    public void limparCadObri() {
        PrimeFaces.current().resetInputs("formCadObri");
    }

    /* Getter and Setters */
    public Obrigacao getObrigacao() {
        return obrigacao;
    }

    public void setObrigacao(Obrigacao obrigacao) {
        this.obrigacao = obrigacao;
    }

    public Prazo getPrazo() {
        return prazo;
    }

    public void setPrazo(Prazo prazo) {
        this.prazo = prazo;
    }

    public String getNomeObrigacao() {
        return nomeObrigacao;
    }

    public void setNomeObrigacao(String nomeObrigacao) {
        this.nomeObrigacao = nomeObrigacao;
    }

    public boolean isNovaObrig() {
        return novaObrig;
    }

    public void setNovaObrig(boolean novaObrig) {
        this.novaObrig = novaObrig;
    }

    public String getObrigacaoSelecionada() {
        return obrigacaoSelecionada;
    }

    public void setObrigacaoSelecionada(String obrigacaoSelecionada) {
        this.obrigacaoSelecionada = obrigacaoSelecionada;
    }

    public String getPrazoSelecionado() {
        return prazoSelecionado;
    }

    public void setPrazoSelecionado(String prazoSelecionado) {
        this.prazoSelecionado = prazoSelecionado;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
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

    public String getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(String ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Prazo> getListaPrazo() {
        return listaPrazo;
    }

    public void setListaPrazo(List<Prazo> listaPrazo) {
        this.listaPrazo = listaPrazo;
    }

    public boolean isUsuarioEncontrado() {
        return usuarioEncontrado;
    }

    public void setUsuarioEncontrado(boolean usuarioEncontrado) {
        this.usuarioEncontrado = usuarioEncontrado;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNovoNome() {
        return novoNome;
    }

    public void setNovoNome(String novoNome) {
        this.novoNome = novoNome;
    }

    public String getNovoLogin() {
        return novoLogin;
    }

    public void setNovoLogin(String novoLogin) {
        this.novoLogin = novoLogin;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }

    public List<String> getListaObrigacoesSelecionadas() {
        return listaObrigacoesSelecionadas;
    }

    public void setListaObrigacoesSelecionadas(List<String> listaObrigacoesSelecionadas) {
        this.listaObrigacoesSelecionadas = listaObrigacoesSelecionadas;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<String> getListaResponsaveis() {
        return listaResponsaveis;
    }

    public void setListaResponsaveis(List<String> listaResponsaveis) {
        this.listaResponsaveis = listaResponsaveis;
    }

    public List<String> getListaResponsaveisSelecionados() {
        return listaResponsaveisSelecionados;
    }

    public void setListaResponsaveisSelecionados(List<String> listaResponsaveisSelecionados) {
        this.listaResponsaveisSelecionados = listaResponsaveisSelecionados;
    }

    public String getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(String usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public Usuario getUsuarioEditar() {
        return usuarioEditar;
    }

    public void setUsuarioEditar(Usuario usuarioEditar) {
        this.usuarioEditar = usuarioEditar;
    }

    public String getNovoEmailResp() {
        return novoEmailResp;
    }

    public void setNovoEmailResp(String novoEmailResp) {
        this.novoEmailResp = novoEmailResp;
    }

}
