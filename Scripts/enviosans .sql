-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 08-Ago-2018 às 13:57
-- Versão do servidor: 10.1.22-MariaDB
-- PHP Version: 7.0.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `enviosans`
--
USE `enviosans`;

-- --------------------------------------------------------

--
-- Estrutura da tabela `historico`
--

CREATE TABLE `historico` (
  `idHistorico` int(11) NOT NULL,
  `dataAlteracao` varchar(255) DEFAULT NULL,
  `dataEnvio` varchar(255) DEFAULT NULL,
  `nomeUsuario` varchar(255) DEFAULT NULL,
  `obrigacao` varchar(255) DEFAULT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `prazo` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estrutura da tabela `obrigacao`
--

CREATE TABLE `obrigacao` (
  `idObrigacao` bigint(20) NOT NULL,
  `nomeObrigacao` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `obrigacao`
--

INSERT INTO `obrigacao` (`idObrigacao`, `nomeObrigacao`) VALUES
(1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS'),
(2, 'Rea Ouvidoria - Representante'),
(3, 'Rea Ouvidoria - ANS'),
(4, 'Divulgação no site da operadora do Reajuste aplicável ao agrupamento de contratos coletivos'),
(5, 'Sistema de Informações de Produtos - SIP'),
(6, 'Relatório circunstanciado da auditoria independente de controles internos'),
(7, 'Formulário de Monitoramento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV cadastrado'),
(8, 'Formulário de Acompanhamento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV inscrito'),
(9, 'Sistema de Informações de Beneficiários - SIB'),
(10, 'Extrato Pormenorizado Reajuste Pessoa Juridica'),
(11, 'Demonstrações Financeiras - Envio à ANS'),
(12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial'),
(13, 'Relatório PPA sobre Provisão de Eventos a Liquidar'),
(14, 'Relatório de Procedimentos Previamente Acordados sobre as informações econômico-financeiras (Relatório PPA sobre DIOPS/ANS)'),
(15, 'Documento de Informações Periódicas - DIOPS'),
(16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS'),
(17, 'Reajuste de Plano Coletivo - RPC'),
(18, 'Portal de Informações do Beneficiário da Saúde Suplementar (PIN-SS), Utilização dos Serviços');

-- --------------------------------------------------------

--
-- Estrutura da tabela `permissoes`
--

CREATE TABLE `permissoes` (
  `idPermissao` bigint(20) NOT NULL,
  `idObrigacao` bigint(20) NOT NULL,
  `nomeObrigacao` varchar(255) DEFAULT NULL,
  `nomeUsuario` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `permissoes`
--

INSERT INTO `permissoes` (`idPermissao`, `idObrigacao`, `nomeObrigacao`, `nomeUsuario`) VALUES
(1, 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', 'Vitor Castilho Ciocca'),
(2, 2, 'Rea Ouvidoria - Representante', 'Vitor Castilho Ciocca'),
(3, 3, 'Rea Ouvidoria - ANS', 'Vitor Castilho Ciocca'),
(4, 4, 'Divulgação no site da operadora do Reajuste aplicável ao agrupamento de contratos coletivos', 'Vitor Castilho Ciocca'),
(5, 5, 'Sistema de Informações de Produtos - SIP', 'Vitor Castilho Ciocca'),
(6, 6, 'Relatório circunstanciado da auditoria independente de controles internos', 'Vitor Castilho Ciocca'),
(7, 7, 'Formulário de Monitoramento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV cadastrado', 'Vitor Castilho Ciocca'),
(8, 8, 'Formulário de Acompanhamento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV inscrito', 'Vitor Castilho Ciocca'),
(9, 9, 'Sistema de Informações de Beneficiários - SIB', 'Vitor Castilho Ciocca'),
(10, 10, 'Extrato Pormenorizado Reajuste Pessoa Juridica', 'Vitor Castilho Ciocca'),
(11, 11, 'Demonstrações Financeiras - Envio à ANS', 'Vitor Castilho Ciocca'),
(12, 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', 'Vitor Castilho Ciocca'),
(13, 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', 'Vitor Castilho Ciocca'),
(14, 14, 'Relatório de Procedimentos Previamente Acordados sobre as informações econômico-financeiras (Relatório PPA sobre DIOPS/ANS)', 'Vitor Castilho Ciocca'),
(15, 15, 'Documento de Informações Periódicas - DIOPS', 'Vitor Castilho Ciocca'),
(16, 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', 'Vitor Castilho Ciocca'),
(17, 17, 'Reajuste de Plano Coletivo - RPC', 'Vitor Castilho Ciocca'),
(18, 18, 'Portal de Informações do Beneficiário da Saúde Suplementar (PIN-SS), Utilização dos Serviços', 'Vitor Castilho Ciocca'),
(19, 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', 'Dr. Nilton Carlos Busch'),
(20, 2, 'Rea Ouvidoria - Representante', 'Dr. Nilton Carlos Busch'),
(21, 3, 'Rea Ouvidoria - ANS', 'Dr. Nilton Carlos Busch'),
(22, 4, 'Divulgação no site da operadora do Reajuste aplicável ao agrupamento de contratos coletivos', 'Dr. Nilton Carlos Busch'),
(23, 5, 'Sistema de Informações de Produtos - SIP', 'Dr. Nilton Carlos Busch'),
(24, 6, 'Relatório circunstanciado da auditoria independente de controles internos', 'Dr. Nilton Carlos Busch'),
(25, 7, 'Formulário de Monitoramento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV cadastrado', 'Dr. Nilton Carlos Busch'),
(26, 8, 'Formulário de Acompanhamento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV inscrito', 'Dr. Nilton Carlos Busch'),
(27, 9, 'Sistema de Informações de Beneficiários - SIB', 'Dr. Nilton Carlos Busch'),
(28, 10, 'Extrato Pormenorizado Reajuste Pessoa Juridica', 'Dr. Nilton Carlos Busch'),
(29, 11, 'Demonstrações Financeiras - Envio à ANS', 'Dr. Nilton Carlos Busch'),
(30, 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', 'Dr. Nilton Carlos Busch'),
(31, 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', 'Dr. Nilton Carlos Busch'),
(32, 14, 'Relatório de Procedimentos Previamente Acordados sobre as informações econômico-financeiras (Relatório PPA sobre DIOPS/ANS)', 'Dr. Nilton Carlos Busch'),
(33, 15, 'Documento de Informações Periódicas - DIOPS', 'Dr. Nilton Carlos Busch'),
(34, 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', 'Dr. Nilton Carlos Busch'),
(35, 17, 'Reajuste de Plano Coletivo - RPC', 'Dr. Nilton Carlos Busch'),
(36, 18, 'Portal de Informações do Beneficiário da Saúde Suplementar (PIN-SS), Utilização dos Serviços', 'Dr. Nilton Carlos Busch'),
(37, 6, 'Relatório circunstanciado da auditoria independente de controles internos', 'Patricia dos Santos Martins Sacardo'),
(38, 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', 'Patricia dos Santos Martins Sacardo'),
(39, 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', 'Patricia dos Santos Martins Sacardo'),
(40, 14, 'Relatório de Procedimentos Previamente Acordados sobre as informações econômico-financeiras (Relatório PPA sobre DIOPS/ANS)', 'Patricia dos Santos Martins Sacardo'),
(41, 15, 'Documento de Informações Periódicas - DIOPS', 'Patricia dos Santos Martins Sacardo'),
(42, 9, 'Sistema de Informações de Beneficiários - SIB', 'Marilia da Costa Camargo Pinto'),
(43, 9, 'Sistema de Informações de Beneficiários - SIB', 'Roger Chaves'),
(44, 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', 'Claudio Alberto Cameschi'),
(45, 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', 'Karina Terezinha Cabrera Ayub'),
(46, 2, 'Rea Ouvidoria - Representante', 'Daniela Moreira Tremontin'),
(47, 3, 'Rea Ouvidoria - ANS', 'Daniela Moreira Tremontin'),
(48, 18, 'Portal de Informações do Beneficiário da Saúde Suplementar (PIN-SS), Utilização dos Serviços', 'Bruno Weverton Macedo de Oliveira'),
(49, 7, 'Formulário de Monitoramento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV cadastrado', 'Eliana Rodrigues Giannine'),
(50, 8, 'Formulário de Acompanhamento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV inscrito', 'Eliana Rodrigues Giannine'),
(51, 4, 'Divulgação no site da operadora do Reajuste aplicável ao agrupamento de contratos coletivos', 'Rosemeire de Cassia Modolo Dias'),
(52, 5, 'Sistema de Informações de Produtos - SIP', 'Rosemeire de Cassia Modolo Dias'),
(53, 10, 'Extrato Pormenorizado Reajuste Pessoa Juridica', 'Rosemeire de Cassia Modolo Dias'),
(54, 17, 'Reajuste de Plano Coletivo - RPC', 'Rosemeire de Cassia Modolo Dias'),
(55, 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', 'Williane Ishikawa Barbosa Zulian'),
(56, 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', 'Fabiane Saab'),
(57, 2, 'Rea Ouvidoria - Representante', 'Fabiane Saab'),
(58, 3, 'Rea Ouvidoria - ANS', 'Fabiane Saab'),
(59, 4, 'Divulgação no site da operadora do Reajuste aplicável ao agrupamento de contratos coletivos', 'Fabiane Saab'),
(60, 5, 'Sistema de Informações de Produtos - SIP', 'Fabiane Saab'),
(61, 6, 'Relatório circunstanciado da auditoria independente de controles internos', 'Fabiane Saab'),
(62, 7, 'Formulário de Monitoramento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV cadastrado', 'Fabiane Saab'),
(63, 8, 'Formulário de Acompanhamento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV inscrito', 'Fabiane Saab'),
(64, 9, 'Sistema de Informações de Beneficiários - SIB', 'Fabiane Saab'),
(65, 10, 'Extrato Pormenorizado Reajuste Pessoa Juridica', 'Fabiane Saab'),
(66, 11, 'Demonstrações Financeiras - Envio à ANS', 'Fabiane Saab'),
(67, 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', 'Fabiane Saab'),
(68, 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', 'Fabiane Saab'),
(69, 14, 'Relatório de Procedimentos Previamente Acordados sobre as informações econômico-financeiras (Relatório PPA sobre DIOPS/ANS)', 'Fabiane Saab'),
(70, 15, 'Documento de Informações Periódicas - DIOPS', 'Fabiane Saab'),
(71, 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', 'Fabiane Saab'),
(72, 17, 'Reajuste de Plano Coletivo - RPC', 'Fabiane Saab'),
(73, 18, 'Portal de Informações do Beneficiário da Saúde Suplementar (PIN-SS), Utilização dos Serviços', 'Fabiane Saab'),
(74, 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', 'Administrador'),
(75, 2, 'Rea Ouvidoria - Representante', 'Administrador'),
(76, 3, 'Rea Ouvidoria - ANS', 'Administrador'),
(77, 4, 'Divulgação no site da operadora do Reajuste aplicável ao agrupamento de contratos coletivos', 'Administrador'),
(78, 5, 'Sistema de Informações de Produtos - SIP', 'Administrador'),
(79, 6, 'Relatório circunstanciado da auditoria independente de controles internos', 'Administrador'),
(80, 7, 'Formulário de Monitoramento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV cadastrado', 'Administrador'),
(81, 8, 'Formulário de Acompanhamento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV inscrito', 'Administrador'),
(82, 9, 'Sistema de Informações de Beneficiários - SIB', 'Administrador'),
(83, 10, 'Extrato Pormenorizado Reajuste Pessoa Juridica', 'Administrador'),
(84, 11, 'Demonstrações Financeiras - Envio à ANS', 'Administrador'),
(85, 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', 'Administrador'),
(86, 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', 'Administrador'),
(87, 14, 'Relatório de Procedimentos Previamente Acordados sobre as informações econômico-financeiras (Relatório PPA sobre DIOPS/ANS)', 'Administrador'),
(88, 15, 'Documento de Informações Periódicas - DIOPS', 'Administrador'),
(89, 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', 'Administrador'),
(90, 17, 'Reajuste de Plano Coletivo - RPC', 'Administrador'),
(91, 18, 'Portal de Informações do Beneficiário da Saúde Suplementar (PIN-SS), Utilização dos Serviços', 'Administrador');

-- --------------------------------------------------------

--
-- Estrutura da tabela `prazo`
--

CREATE TABLE `prazo` (
  `idPrazo` bigint(20) NOT NULL,
  `competencia` varchar(255) DEFAULT NULL,
  `dataEnvio` varchar(255) DEFAULT NULL,
  `fundamentacao` varchar(255) DEFAULT NULL,
  `idObrigacao` bigint(20) DEFAULT NULL,
  `nomeObrigacao` varchar(255) DEFAULT NULL,
  `observacoes` varchar(255) DEFAULT NULL,
  `penalidade` varchar(255) DEFAULT NULL,
  `prazoLimite` varchar(255) DEFAULT NULL,
  `responsavel` varchar(255) DEFAULT NULL,
  `setor` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `prazo`
--

INSERT INTO `prazo` (`idPrazo`, `competencia`, `dataEnvio`, `fundamentacao`, `idObrigacao`, `nomeObrigacao`, `observacoes`, `penalidade`, `prazoLimite`, `responsavel`, `setor`) VALUES
(1, '201802', '01/03/2018', 'artigo 5º, da Resolução Normativa nº 89, de 15 de fevereiro de 2005', 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', 'Recolhido valor de 252,95', 'Juros de 1% ao mês e multa de mora de 10%', '09/03/2018', 'Cláudio', 'Financeiro'),
(2, '201805', '', 'artigo 5º, da Resolução Normativa nº 89, de 15 de fevereiro de 2005', 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', '', 'Juros de 1% ao mês e multa de mora de 10%', '10/06/2018', 'Cláudio', 'Financeiro'),
(3, '201808', '', 'artigo 5º, da Resolução Normativa nº 89, de 15 de fevereiro de 2005', 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', '', 'Juros de 1% ao mês e multa de mora de 10%', '10/09/2018', 'Cláudio', 'Financeiro'),
(4, '201811', '', 'artigo 5º, da Resolução Normativa nº 89, de 15 de fevereiro de 2005', 1, 'Taxa de Saúde Suplementar por Plano de Assistência à Saúde - TPS', '', 'Juros de 1% ao mês e multa de mora de 10%', '10/04/2019', 'Cláudio', 'Financeiro'),
(5, '201712', '16/01/2018', 'artigo 11, da Instrução Normativa DICOL nº 2, de 18 de dezembro de 2014', 2, 'Rea Ouvidoria - Representante', '', 'Multa base de R$ 40.000,00, conforme artigo 40, da Resolução Normativa nº 124, de 30 de março de 2006', '30/03/2018', 'Daniela Moreira', 'Ouvidoria'),
(6, '201712', '15/01/2018', 'artigo 11, da Instrução Normativa DICOL nº 2, de 18 de dezembro de 2014', 3, 'Rea Ouvidoria - ANS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '15/04/2018', 'Daniela Moreira', 'Ouvidoria'),
(7, '201804', '17/04/2018', 'artigo 8º, da Resolução Normativa nº 309, de 24 de outubro de 2012', 4, 'Divulgação no site da operadora do Reajuste aplicável ao agrupamento de contratos coletivos', 'Informações referentes ao período maio até abril do ano subsequente', 'Multa base de R$ 30.000,00, conforme artigo 40, da Resolução Normativa nº 124, de 30 de março de 2006', '02/05/2018', 'Meire', 'Revisão de Contas'),
(8, '1º. Trimestre de 2018', '19/04/2018', 'artigo 6º, inciso I, da Resolução Normativa nº 205, de 8 de outubro de 2009', 5, 'Sistema de Informações de Produtos - SIP', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '31/05/2018', 'Meire', 'Revisão de Contas'),
(9, '2º. Trimestre de 2018', '03/08/2018', 'artigo 6º, inciso I, da Resolução Normativa nº 205, de 8 de outubro de 2009', 5, 'Sistema de Informações de Produtos - SIP', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '30/06/2018', 'Meire', 'Revisão de Contas'),
(10, '3º. Trimestre de 2018', '', 'artigo 6º, inciso I, da Resolução Normativa nº 205, de 8 de outubro de 2009', 5, 'Sistema de Informações de Produtos - SIP', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '30/11/2018', 'Meire', 'Revisão de Contas'),
(11, '4º. Trimestre de 2018', '', 'artigo 6º, inciso I, da Resolução Normativa nº 205, de 8 de outubro de 2009', 5, 'Sistema de Informações de Produtos - SIP', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '28/02/2019', 'Meire', 'Revisão de Contas'),
(12, '201712', '14/05/2018', 'artigo 3º, §4º, da Resolução Normativa nº 173, de 10 de julho de 2008', 6, 'Relatório circunstanciado da auditoria independente de controles internos', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '15/05/2018', 'Patricia', 'Contabilidade'),
(13, '201812', '15/01/2018', 'artigo 7º, inciso II, da Instrução Normativa Conjunta DIOPE/DIPRO nº 7, de 23 de novembro de 2012', 7, 'Formulário de Monitoramento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV cadastrado', '', 'Reprovação do FC relacionado e consequente o descadastramento do programa', '01/04/2019', 'Eliana', 'Medicina Preventiva'),
(14, '201712', '09/03/2018', 'artigo 6°, §1° da Instrução Normativa DIPRO n° 35, de 19 de agosto de 2011', 8, 'Formulário de Acompanhamento de Programa de Promoção da Saúde e Prevenção de Riscos e Doenças - PROMOPREV inscrito', '', 'Multa base de R$ 20.000,00, conforme artigo 63-A, da Resolução Normativa n° 124, de 30 de março de 2006', '31/03/2018', 'Eliana', 'Medicina Preventiva'),
(15, '201801', '01/02/2018', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/02/2018', 'Marília', 'Cadastro'),
(16, '201802', '01/03/2018', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', 'Arquivo retificado em 08/03/2018', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/03/2018', 'Marília', 'Cadastro'),
(17, '201803', '02/04/2018', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', 'Arquivo retificado em 04/04/2018', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/04/2018', 'Marília', 'Cadastro'),
(18, '201804', '02/05/2018', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/05/2018', 'Marília', 'Cadastro'),
(19, '201805', '01/06/2018', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/06/2018', 'Marília', 'Cadastro'),
(20, '201806', '02/07/2018', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/07/2018', 'Marília', 'Cadastro'),
(21, '201807', '02/08/2018', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/08/2018', 'Marília', 'Cadastro'),
(22, '201808', '', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/09/2018', 'Marília', 'Cadastro'),
(23, '201809', '', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/10/2018', 'Marília', 'Cadastro'),
(24, '201810', '', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/11/2018', 'Marília', 'Cadastro'),
(25, '201811', '', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/12/2018', 'Marília', 'Cadastro'),
(26, '201812', '', 'artigo 7º, da Resolução Normativa nº 295, de 9 de maio de 2012', 9, 'Sistema de Informações de Beneficiários - SIB', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '05/01/2019', 'Marília', 'Cadastro'),
(27, '201806', '18/04/2018', 'artigo 14, da Resolução Normativa nº 389, de 26 de novembro de 2015', 10, 'Extrato Pormenorizado Reajuste Pessoa Juridica', '', 'Advertência e/ou multa base de R$ 25.000,00, conforme artigo 74, da Resolução Normativa nº 124, de 30 de março de 2006', '30/04/2018', 'Meire', 'Revisão de Contas'),
(28, '201812', '', 'artigo 14, da Resolução Normativa nº 389, de 26 de novembro de 2015', 10, 'Extrato Pormenorizado Reajuste Pessoa Juridica', '', 'Advertência e/ou multa base de R$ 25.000,00, conforme artigo 74, da Resolução Normativa nº 124, de 30 de março de 2006', '31/10/2018', 'Meire', 'Revisão de Contas'),
(29, '201712', '02/04/2018', 'item 6.3.8, do Anexo da Resolução Normativa nº 290, de 27 de fevereiro de 2012', 11, 'Demonstrações Financeiras - Envio à ANS', 'Encaminhado pelos Correios', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '15/04/2018', 'Vitor', 'Assessoria ANS'),
(30, '201803', '15/05/2018', 'artigo 19, inciso II, da Resolução Normativa nº 393, de 9 de dezembro de 2015', 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '15/05/2018', 'Patricia', 'Contabilidade'),
(31, '201806', '', 'artigo 19, inciso II, da Resolução Normativa nº 393, de 9 de dezembro de 2015', 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '15/08/2018', 'Patricia', 'Contabilidade'),
(32, '201809', '', 'artigo 19, inciso II, da Resolução Normativa nº 393, de 9 de dezembro de 2015', 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '15/11/2018', 'Patricia', 'Contabilidade'),
(33, '201812', '', 'artigo 19, inciso II, da Resolução Normativa nº 393, de 9 de dezembro de 2015', 12, 'Termo de Responsabilidade Atuarial (TRA) das Provisões Técnicas estimadas por metodologia atuarial', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '31/03/2019', 'Patricia', 'Contabilidade'),
(34, '201803', '15/05/2018', 'Artigo 3º, da Instrução Normativa DIOPE nº 45, de 15 de dezembro de 2010', 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '15/05/2018', 'Patricia', 'Contabilidade'),
(35, '201806', '', 'Artigo 3º, da Instrução Normativa DIOPE nº 45, de 15 de dezembro de 2010', 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '15/08/2018', 'Patricia', 'Contabilidade'),
(37, '201809', '', 'Artigo 3º, da Instrução Normativa DIOPE nº 45, de 15 de dezembro de 2010', 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '15/11/2018', 'Patricia', 'Contabilidade'),
(38, '201812', '', 'Artigo 3º, da Instrução Normativa DIOPE nº 45, de 15 de dezembro de 2010', 13, 'Relatório PPA sobre Provisão de Eventos a Liquidar', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '31/03/2019', 'Patricia', 'Contabilidade'),
(39, '201806', '', 'artigo 2º-A, §1º, da Resolução Normativa nº 173, de 10 de julho de 2008', 14, 'Relatório de Procedimentos Previamente Acordados sobre as informações econômico-financeiras (Relatório PPA sobre DIOPS/ANS)', '', 'Multa base de R$ 25.000,00, conforme artigo 34, da Resolução Normativa nº 124, de 30 de março de 2006', '15/08/2018', 'Patricia', 'Contabilidade'),
(40, '201803', '15/05/2018', 'artigo 3º, inciso II, alínea \"a\", da Resolução Normativa nº 173, de 10 de julho de 2009', 15, 'Documento de Informações Periódicas - DIOPS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '15/05/2018', 'Patricia', 'Contabilidade'),
(41, '201801', '07/02/2018', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', 'Arquivo retificado em 22/03/2018', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/02/2018', 'Williane', 'Revisão de Contas'),
(42, '201802', '23/03/2018', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/04/2018', 'Williane', 'Revisão de Contas'),
(43, '201803', '02/04/2018', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/05/2018', 'Williane', 'Revisão de Contas'),
(44, '201804', '08/05/2018', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/06/2018', 'Williane', 'Revisão de Contas'),
(45, '201805', '05/06/2018', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/07/2018', 'Williane', 'Revisão de Contas'),
(46, '201806', '04/07/2018', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/08/2018', 'Williane', 'Revisão de Contas'),
(47, '201807', '02/08/2018', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/09/2018', 'Williane', 'Revisão de Contas'),
(48, '201808', '', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/10/2018', 'Williane', 'Revisão de Contas'),
(49, '201809', '', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/11/2018', 'Williane', 'Revisão de Contas'),
(50, '201810', '', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/12/2018', 'Williane', 'Revisão de Contas'),
(51, '201811', '', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/01/2019', 'Williane', 'Revisão de Contas'),
(52, '201812', '', 'artigo 26, da Resolução Normativa nº 305, de 9 de outubro de 2012', 16, 'Monitoramento do Padrão para Troca de Informações na Saúde Suplementar - TISS', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '25/02/2019', 'Williane', 'Revisão de Contas'),
(53, '201712', '12/03/2018', 'artigo 2º, inciso I, da Instrução Normativa da DIPRO nº 13, de 21 de julho de 2006', 17, 'Reajuste de Plano Coletivo - RPC', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '31/03/2018', 'Meire', 'Revisão de Contas'),
(54, '201806', '03/08/2018', 'artigo 2º, inciso I, da Instrução Normativa da DIPRO nº 13, de 21 de julho de 2006', 17, 'Reajuste de Plano Coletivo - RPC', '', 'Multa base de R$ 25.000,00, conforme artigo 35, da Resolução Normativa nº 124, de 30 de março de 2006', '30/09/2018', 'Meire', 'Revisão de Contas'),
(55, '201712', '', 'artigo 10, da Resolução Normativa nº 389, de 26 de novembro de 2015', 18, 'Portal de Informações do Beneficiário da Saúde Suplementar (PIN-SS), Utilização dos Serviços', '', 'Advertência e/ou multa base de R$ 25.000,00, conforme artigo 74, da Resolução Normativa nº 124, de 30 de março de 2006 ', '28/02/2018', 'Meire', 'Revisão de Contas'),
(56, '201806', '', 'artigo 10, da Resolução Normativa nº 389, de 26 de novembro de 2015', 18, 'Portal de Informações do Beneficiário da Saúde Suplementar (PIN-SS), Utilização dos Serviços', '', 'Advertência e/ou multa base de R$ 25.000,00, conforme artigo 74, da Resolução Normativa nº 124, de 30 de março de 2006 ', '31/08/2018', 'Meire', 'Revisão de Contas');

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` bigint(20) NOT NULL,
  `login` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `tipoUsuario` varchar(255) DEFAULT NULL,
  `ultimoAcesso` varchar(255) DEFAULT NULL,
  `emailResp` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `login`, `nome`, `senha`, `tipoUsuario`, `ultimoAcesso`, `emailResp`) VALUES
(1, 'vitor', 'Vitor Castilho Ciocca', '438aafabc2ecd8988a83bda6a58148da', 'A', NULL, 'vitor@unimedcop.coop.br'),
(2, 'busch', 'Dr. Nilton Carlos Busch', 'b71e7f18a043b020b4935f6694a918cf', 'A', NULL, 'busch@unimedcop.coop.br'),
(3, 'patricia', 'Patricia dos Santos Martins Sacardo', 'afe38e352dcb2a515ea06a31c301b9ba', 'U', NULL, 'patricia@unimedcop.coop.br'),
(4, 'marilia', 'Marilia da Costa Camargo Pinto', '10f28f1d34b9c7996fc533d38baa1938', 'U', NULL, 'marilia@unimedcop.coop.br'),
(5, 'roger', 'Roger Chaves', '8d09fb7efc7a7b1c807b0079116e08b0', 'U', NULL, 'roger@unimedcop.coop.br'),
(6, 'claudio', 'Claudio Alberto Cameschi', 'f6a47a638824180d57f0a561fd5843c6', 'U', NULL, 'claudio@unimedcop.coop.br'),
(7, 'karina', 'Karina Terezinha Cabrera Ayub', 'a37b2a637d2541a600d707648460397e', 'U', NULL, 'kayub@unimedcop.coop.br'),
(8, 'dmoreira', 'Daniela Moreira Tremontin', '7e2fc85f0756682886fd3f33e0d8e642', 'U', NULL, 'dmoreira@unimedcop.coop.br'),
(9, 'carolina', 'Carolina Correa Cres', '5f3bc5221626b2f8d66261fb07339462', 'U', NULL, 'carolinacres@unimedcop.coop.br'),
(10, 'demerval', 'Demerval Benedetti Lourenco', '21d512f476bba74e28a7b7b0e8a62b92', 'U', NULL, 'demerval@unimedcop.coop.br'),
(11, 'bruno', 'Bruno Weverton Macedo de Oliveira', 'e3928a3bc4be46516aa33a79bbdfdb08', 'U', NULL, 'bruno@unimedcop.coop.br'),
(12, 'walter', 'Walter Thomazini', '841d93525b9f0960ceaf38f4fdf22e2e', 'U', NULL, 'walter@unimedcop.coop.br'),
(13, 'eliana', 'Eliana Rodrigues Giannine', '6a6ddeaf5e37076d17b8495f7114a729', 'U', NULL, 'eliana@unimedcop.coop.br'),
(14, 'dcristina', 'Daniela Cristina de Oliveira Pereira', '07a88e756847244f3496f63f473d6085', 'U', NULL, 'dcristina@unimedcop.coop.br'),
(15, 'meire', 'Rosemeire de Cassia Modolo Dias', 'b169190b7c9d21969da51ad5895cfd4f', 'U', NULL, 'meire@unimedcop.coop.br'),
(16, 'williane', 'Williane Ishikawa Barbosa Zulian', '6f152c667840d08aa407087f5cd1d17e', 'U', NULL, 'williane@unimedcop.coop.br'),
(17, 'flavia', 'Flávia Alves da Silva Draghi', '91582721be524e4c58484983c73120c9', 'U', NULL, 'flavia@unimedcop.coop.br'),
(18, 'fabiane', 'Fabiane Saab', '738c5d6e4d4f023307dd9ab75f4a7224', 'A', NULL, 'fabiane@unimedcop.coop.br'),
(19, 'sonia', 'Sônia Helena de Carvalho', '21232f297a57a5a743894a0e4a801fc3', 'U', NULL, 'qualidade@unimedcop.coop.br'),
(20, 'admin', 'Administrador', '21232f297a57a5a743894a0e4a801fc3', 'A', NULL, 'ericsson@unimedcop.coop.br');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `historico`
--
ALTER TABLE `historico`
  ADD PRIMARY KEY (`idHistorico`);

--
-- Indexes for table `obrigacao`
--
ALTER TABLE `obrigacao`
  ADD PRIMARY KEY (`idObrigacao`);

--
-- Indexes for table `permissoes`
--
ALTER TABLE `permissoes`
  ADD PRIMARY KEY (`idPermissao`),
  ADD KEY `FK_Permissao_Obrigacao_idx` (`idObrigacao`),
  ADD KEY `FK_Permissao_Usuario_idx` (`nomeUsuario`);

--
-- Indexes for table `prazo`
--
ALTER TABLE `prazo`
  ADD PRIMARY KEY (`idPrazo`),
  ADD KEY `FK_Prazo_Obrigacao_idx` (`idObrigacao`),
  ADD KEY `FK_Prazo_NomeObrigacao_idx` (`nomeObrigacao`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD UNIQUE KEY `UK_73l619d30rt4w3wvgnu7oqot3` (`nome`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `historico`
--
ALTER TABLE `historico`
  MODIFY `idHistorico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `obrigacao`
--
ALTER TABLE `obrigacao`
  MODIFY `idObrigacao` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `permissoes`
--
ALTER TABLE `permissoes`
  MODIFY `idPermissao` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=107;
--
-- AUTO_INCREMENT for table `prazo`
--
ALTER TABLE `prazo`
  MODIFY `idPrazo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `permissoes`
--
ALTER TABLE `permissoes`
  ADD CONSTRAINT `FK_Permissao_Obrigacao` FOREIGN KEY (`idObrigacao`) REFERENCES `obrigacao` (`idObrigacao`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Permissao_Usuario` FOREIGN KEY (`nomeUsuario`) REFERENCES `usuario` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `prazo`
--
ALTER TABLE `prazo`
  ADD CONSTRAINT `FK_Prazo_Obrigacao` FOREIGN KEY (`idObrigacao`) REFERENCES `obrigacao` (`idObrigacao`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
