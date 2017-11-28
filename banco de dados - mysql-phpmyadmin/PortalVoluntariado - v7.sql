-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 28, 2017 at 02:21 PM
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
-- Table structure for table `projeto`
--

DROP TABLE IF EXISTS `projeto`;
CREATE TABLE `projeto` (
  `ID_Projeto` int(5) NOT NULL,
  `Nome` tinytext,
  `Descricao` text,
  `Imagem` longblob,
  `ID_Pessoa` int(5) NOT NULL,
  `ID_Instituicao` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `projeto`
--

INSERT INTO `projeto` (`ID_Projeto`, `Nome`, `Descricao`, `Imagem`, `ID_Pessoa`, `ID_Instituicao`) VALUES
(4, 'projeto teste', 'dfd', NULL, 1, 10),
(5, 'projeto teste', 'aa', NULL, 1, 10),
(6, 'projeto 3', 'hghh', NULL, 1, 10);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `projeto`
--
ALTER TABLE `projeto`
  ADD PRIMARY KEY (`ID_Projeto`,`ID_Pessoa`,`ID_Instituicao`),
  ADD UNIQUE KEY `ID_Projeto_UNIQUE` (`ID_Projeto`),
  ADD KEY `fk_Projeto_Pessoa-Instituicao1_idx` (`ID_Pessoa`,`ID_Instituicao`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `projeto`
--
ALTER TABLE `projeto`
  MODIFY `ID_Projeto` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `projeto`
--
ALTER TABLE `projeto`
  ADD CONSTRAINT `fk_Projeto_Pessoa-Instituicao1` FOREIGN KEY (`ID_Pessoa`,`ID_Instituicao`) REFERENCES `pessoa-instituicao` (`ID_Pessoa`, `ID_Instituicao`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
