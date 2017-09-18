-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 18, 2017 at 03:33 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `portalvoluntariado`
--

-- --------------------------------------------------------

--
-- Table structure for table `causa-projeto`
--

CREATE TABLE `causa-projeto` (
  `ID_Projeto` int(5) NOT NULL,
  `ID_Causa` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `causadeinteresse`
--

CREATE TABLE `causadeinteresse` (
  `ID_Causa` int(3) NOT NULL,
  `Nome` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `cidade`
--

CREATE TABLE `cidade` (
  `ID_Cidade` int(5) NOT NULL,
  `Nome` tinytext,
  `ID_Estado` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cidade`
--

INSERT INTO `cidade` (`ID_Cidade`, `Nome`, `ID_Estado`) VALUES
(1, 'São Caetano', 1),
(2, 'São Paulo - Capital', 1);

-- --------------------------------------------------------

--
-- Table structure for table `estado`
--

CREATE TABLE `estado` (
  `ID_Estado` int(2) NOT NULL,
  `Nome` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `estado`
--

INSERT INTO `estado` (`ID_Estado`, `Nome`) VALUES
(1, 'São Paulo'),
(2, 'Rio de Janeiro');

-- --------------------------------------------------------

--
-- Table structure for table `habilidade`
--

CREATE TABLE `habilidade` (
  `ID_Habilidade` int(3) NOT NULL,
  `Nome` tinytext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `habilidade-vaga`
--

CREATE TABLE `habilidade-vaga` (
  `ID_Vaga` int(5) NOT NULL,
  `ID_Habilidade` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `instituicao`
--

CREATE TABLE `instituicao` (
  `ID_Instituicao` int(5) NOT NULL,
  `Nome` tinytext,
  `Descricao` text,
  `Rua` tinytext,
  `Complemento` tinytext,
  `Bairro` tinytext,
  `Email` tinytext,
  `Logotipo` longblob,
  `Website` tinytext,
  `ID_Cidade` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `instituicao`
--

INSERT INTO `instituicao` (`ID_Instituicao`, `Nome`, `Descricao`, `Rua`, `Complemento`, `Bairro`, `Email`, `Logotipo`, `Website`, `ID_Cidade`) VALUES
(1, 'Instituicao1', 'sda', 'sdfd', 'sdf', 'fsdfs', 'fsdfs', '', 'sdfs', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `pessoa`
--

CREATE TABLE `pessoa` (
  `CPF` int(20) NOT NULL,
  `Nome Completo` varchar(45) NOT NULL,
  `DataNascimento` date DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Papel` tinyint(2) DEFAULT NULL,
  `NomeDeUsuario` varchar(45) NOT NULL,
  `Senha` varchar(32) NOT NULL,
  `ID_Cidade` int(7) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pessoa`
--

INSERT INTO `pessoa` (`CPF`, `Nome Completo`, `DataNascimento`, `Email`, `Papel`, `NomeDeUsuario`, `Senha`, `ID_Cidade`) VALUES
(1, 'andre1', NULL, 'andre1', 2, 'andre1', '202cb962ac59075b964b07152d234b70', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `pessoa-causa`
--

CREATE TABLE `pessoa-causa` (
  `ID_Pessoa` int(5) NOT NULL,
  `ID_Causa` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `pessoa-habilidade`
--

CREATE TABLE `pessoa-habilidade` (
  `ID_Pessoa` int(5) NOT NULL,
  `ID_Habilidade` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `pessoa-instituicao`
--

CREATE TABLE `pessoa-instituicao` (
  `ID_Pessoa` int(5) NOT NULL,
  `ID_Instituicao` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `pontual`
--

CREATE TABLE `pontual` (
  `ID_Projeto` int(5) NOT NULL,
  `ID_Vaga` int(5) NOT NULL,
  `DataInicio` date DEFAULT NULL,
  `DataFim` date DEFAULT NULL,
  `PrazoParaInscricoes` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `projeto`
--

CREATE TABLE `projeto` (
  `ID_Projeto` int(5) NOT NULL,
  `Nome` tinytext,
  `Descricao` text,
  `Imagem` longblob,
  `ID_Pessoa` int(5) NOT NULL,
  `ID_Instituicao` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `vaga`
--

CREATE TABLE `vaga` (
  `ID_Projeto` int(5) NOT NULL,
  `ID_Vaga` int(5) NOT NULL,
  `Nome` tinytext,
  `Descricao` text,
  `Pre-Requisito` text,
  `Quantidade` int(5) DEFAULT NULL,
  `ADistancia` tinytext,
  `Rua` tinytext,
  `Numero` int(5) DEFAULT NULL,
  `Complemento` tinytext,
  `Bairro` tinytext,
  `Pontual` tinyint(4) NOT NULL,
  `ID_Cidade` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `vaga-pessoa`
--

CREATE TABLE `vaga-pessoa` (
  `ID_Pessoa` int(5) NOT NULL,
  `ID_Vaga` int(5) NOT NULL,
  `DataHora` datetime DEFAULT NULL,
  `Observacoes` text,
  `PresencaConfirmada` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `causa-projeto`
--
ALTER TABLE `causa-projeto`
  ADD PRIMARY KEY (`ID_Projeto`,`ID_Causa`),
  ADD KEY `fk_CausaDeInteresse_has_Projeto_Projeto1_idx` (`ID_Projeto`),
  ADD KEY `fk_CausaDeInteresse_has_Projeto_CausaDeInteresse1_idx` (`ID_Causa`);

--
-- Indexes for table `causadeinteresse`
--
ALTER TABLE `causadeinteresse`
  ADD PRIMARY KEY (`ID_Causa`),
  ADD UNIQUE KEY `ID_Causa_UNIQUE` (`ID_Causa`);

--
-- Indexes for table `cidade`
--
ALTER TABLE `cidade`
  ADD PRIMARY KEY (`ID_Cidade`),
  ADD UNIQUE KEY `ID_Cidade_UNIQUE` (`ID_Cidade`),
  ADD KEY `fk_Cidade_Estado1_idx` (`ID_Estado`);

--
-- Indexes for table `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`ID_Estado`),
  ADD UNIQUE KEY `ID_Estado_UNIQUE` (`ID_Estado`);

--
-- Indexes for table `habilidade`
--
ALTER TABLE `habilidade`
  ADD PRIMARY KEY (`ID_Habilidade`),
  ADD UNIQUE KEY `ID_Habilidade_UNIQUE` (`ID_Habilidade`);

--
-- Indexes for table `habilidade-vaga`
--
ALTER TABLE `habilidade-vaga`
  ADD PRIMARY KEY (`ID_Vaga`,`ID_Habilidade`),
  ADD KEY `fk_Habilidade_has_Vaga_Vaga1_idx` (`ID_Vaga`),
  ADD KEY `fk_Habilidade_has_Vaga_Habilidade1_idx` (`ID_Habilidade`);

--
-- Indexes for table `instituicao`
--
ALTER TABLE `instituicao`
  ADD PRIMARY KEY (`ID_Instituicao`),
  ADD UNIQUE KEY `ID_Instituicao_UNIQUE` (`ID_Instituicao`),
  ADD KEY `fk_Instituicao_Cidade1_idx` (`ID_Cidade`);

--
-- Indexes for table `pessoa`
--
ALTER TABLE `pessoa`
  ADD PRIMARY KEY (`CPF`),
  ADD UNIQUE KEY `NomeDeUsuario_UNIQUE` (`NomeDeUsuario`),
  ADD UNIQUE KEY `CPF_UNIQUE` (`CPF`),
  ADD KEY `fk_Pessoa_Cidade1_idx` (`ID_Cidade`);

--
-- Indexes for table `pessoa-causa`
--
ALTER TABLE `pessoa-causa`
  ADD PRIMARY KEY (`ID_Pessoa`,`ID_Causa`),
  ADD KEY `fk_Pessoa_has_CausaDeInteresse_CausaDeInteresse1_idx` (`ID_Causa`),
  ADD KEY `fk_Pessoa_has_CausaDeInteresse_Pessoa1_idx` (`ID_Pessoa`);

--
-- Indexes for table `pessoa-habilidade`
--
ALTER TABLE `pessoa-habilidade`
  ADD PRIMARY KEY (`ID_Pessoa`,`ID_Habilidade`),
  ADD KEY `fk_Pessoa_has_Habilidade_Habilidade1_idx` (`ID_Habilidade`),
  ADD KEY `fk_Pessoa_has_Habilidade_Pessoa1_idx` (`ID_Pessoa`);

--
-- Indexes for table `pessoa-instituicao`
--
ALTER TABLE `pessoa-instituicao`
  ADD PRIMARY KEY (`ID_Pessoa`,`ID_Instituicao`),
  ADD KEY `fk_Pessoa_has_Instituicao_Instituicao1_idx` (`ID_Instituicao`),
  ADD KEY `fk_Pessoa_has_Instituicao_Pessoa_idx` (`ID_Pessoa`);

--
-- Indexes for table `pontual`
--
ALTER TABLE `pontual`
  ADD PRIMARY KEY (`ID_Projeto`,`ID_Vaga`);

--
-- Indexes for table `projeto`
--
ALTER TABLE `projeto`
  ADD PRIMARY KEY (`ID_Projeto`,`ID_Pessoa`,`ID_Instituicao`),
  ADD UNIQUE KEY `ID_Projeto_UNIQUE` (`ID_Projeto`),
  ADD KEY `fk_Projeto_Pessoa-Instituicao1_idx` (`ID_Pessoa`,`ID_Instituicao`);

--
-- Indexes for table `vaga`
--
ALTER TABLE `vaga`
  ADD PRIMARY KEY (`ID_Vaga`,`ID_Projeto`),
  ADD UNIQUE KEY `ID_Vaga_UNIQUE` (`ID_Vaga`),
  ADD KEY `fk_Vaga_Cidade1_idx` (`ID_Cidade`),
  ADD KEY `fk_Vaga_Projeto1_idx` (`ID_Projeto`);

--
-- Indexes for table `vaga-pessoa`
--
ALTER TABLE `vaga-pessoa`
  ADD PRIMARY KEY (`ID_Pessoa`,`ID_Vaga`),
  ADD KEY `fk_Vaga_has_Pessoa_Pessoa1_idx` (`ID_Pessoa`),
  ADD KEY `fk_Vaga_has_Pessoa_Vaga1` (`ID_Vaga`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `causadeinteresse`
--
ALTER TABLE `causadeinteresse`
  MODIFY `ID_Causa` int(3) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `cidade`
--
ALTER TABLE `cidade`
  MODIFY `ID_Cidade` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `estado`
--
ALTER TABLE `estado`
  MODIFY `ID_Estado` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `habilidade`
--
ALTER TABLE `habilidade`
  MODIFY `ID_Habilidade` int(3) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `instituicao`
--
ALTER TABLE `instituicao`
  MODIFY `ID_Instituicao` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `pessoa`
--
ALTER TABLE `pessoa`
  MODIFY `CPF` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `projeto`
--
ALTER TABLE `projeto`
  MODIFY `ID_Projeto` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `vaga`
--
ALTER TABLE `vaga`
  MODIFY `ID_Vaga` int(5) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `causa-projeto`
--
ALTER TABLE `causa-projeto`
  ADD CONSTRAINT `fk_CausaDeInteresse_has_Projeto_CausaDeInteresse1` FOREIGN KEY (`ID_Causa`) REFERENCES `causadeinteresse` (`ID_Causa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_CausaDeInteresse_has_Projeto_Projeto1` FOREIGN KEY (`ID_Projeto`) REFERENCES `projeto` (`ID_Projeto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `cidade`
--
ALTER TABLE `cidade`
  ADD CONSTRAINT `fk_Cidade_Estado1` FOREIGN KEY (`ID_Estado`) REFERENCES `estado` (`ID_Estado`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `habilidade-vaga`
--
ALTER TABLE `habilidade-vaga`
  ADD CONSTRAINT `fk_Habilidade_has_Vaga_Habilidade1` FOREIGN KEY (`ID_Habilidade`) REFERENCES `habilidade` (`ID_Habilidade`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Habilidade_has_Vaga_Vaga1` FOREIGN KEY (`ID_Vaga`) REFERENCES `vaga` (`ID_Vaga`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `instituicao`
--
ALTER TABLE `instituicao`
  ADD CONSTRAINT `fk_Instituicao_Cidade1` FOREIGN KEY (`ID_Cidade`) REFERENCES `cidade` (`ID_Cidade`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pessoa`
--
ALTER TABLE `pessoa`
  ADD CONSTRAINT `fk_Pessoa_Cidade1` FOREIGN KEY (`ID_Cidade`) REFERENCES `cidade` (`ID_Cidade`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pessoa-causa`
--
ALTER TABLE `pessoa-causa`
  ADD CONSTRAINT `fk_Pessoa_has_CausaDeInteresse_CausaDeInteresse1` FOREIGN KEY (`ID_Causa`) REFERENCES `causadeinteresse` (`ID_Causa`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pessoa_has_CausaDeInteresse_Pessoa1` FOREIGN KEY (`ID_Pessoa`) REFERENCES `pessoa` (`CPF`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pessoa-habilidade`
--
ALTER TABLE `pessoa-habilidade`
  ADD CONSTRAINT `fk_Pessoa_has_Habilidade_Habilidade1` FOREIGN KEY (`ID_Habilidade`) REFERENCES `habilidade` (`ID_Habilidade`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pessoa_has_Habilidade_Pessoa1` FOREIGN KEY (`ID_Pessoa`) REFERENCES `pessoa` (`CPF`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pessoa-instituicao`
--
ALTER TABLE `pessoa-instituicao`
  ADD CONSTRAINT `fk_Pessoa_has_Instituicao_Instituicao1` FOREIGN KEY (`ID_Instituicao`) REFERENCES `instituicao` (`ID_Instituicao`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pessoa_has_Instituicao_Pessoa` FOREIGN KEY (`ID_Pessoa`) REFERENCES `pessoa` (`CPF`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `pontual`
--
ALTER TABLE `pontual`
  ADD CONSTRAINT `fk_Pontual_Vaga1` FOREIGN KEY (`ID_Projeto`,`ID_Vaga`) REFERENCES `vaga` (`ID_Projeto`, `ID_Vaga`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `projeto`
--
ALTER TABLE `projeto`
  ADD CONSTRAINT `fk_Projeto_Pessoa-Instituicao1` FOREIGN KEY (`ID_Pessoa`,`ID_Instituicao`) REFERENCES `pessoa-instituicao` (`ID_Pessoa`, `ID_Instituicao`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `vaga`
--
ALTER TABLE `vaga`
  ADD CONSTRAINT `fk_Vaga_Cidade1` FOREIGN KEY (`ID_Cidade`) REFERENCES `cidade` (`ID_Cidade`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Vaga_Projeto1` FOREIGN KEY (`ID_Projeto`) REFERENCES `projeto` (`ID_Projeto`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `vaga-pessoa`
--
ALTER TABLE `vaga-pessoa`
  ADD CONSTRAINT `fk_Vaga_has_Pessoa_Pessoa1` FOREIGN KEY (`ID_Pessoa`) REFERENCES `pessoa` (`CPF`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Vaga_has_Pessoa_Vaga1` FOREIGN KEY (`ID_Vaga`) REFERENCES `vaga` (`ID_Vaga`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
