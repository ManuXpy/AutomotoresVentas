/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : ventas

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2016-04-13 15:20:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cargos`
-- ----------------------------
DROP TABLE IF EXISTS `cargos`;
CREATE TABLE `cargos` (
  `id_cargos` int(11) NOT NULL AUTO_INCREMENT,
  `cargo_descrip` varchar(100) NOT NULL,
  PRIMARY KEY (`id_cargos`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of cargos
-- ----------------------------
INSERT INTO `cargos` VALUES ('1', 'admin');
INSERT INTO `cargos` VALUES ('2', 'caja');
INSERT INTO `cargos` VALUES ('5', 'administrador');
INSERT INTO `cargos` VALUES ('11', 'auditoria');
INSERT INTO `cargos` VALUES ('13', 'supermod');
INSERT INTO `cargos` VALUES ('14', 'tapitaaaa');
INSERT INTO `cargos` VALUES ('15', 'vendedor');
INSERT INTO `cargos` VALUES ('16', 'moddd');

-- ----------------------------
-- Table structure for `clientes`
-- ----------------------------
DROP TABLE IF EXISTS `clientes`;
CREATE TABLE `clientes` (
  `cod_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_cliente` varchar(20) NOT NULL,
  `razonsocial_cliente` varchar(50) NOT NULL,
  `ruc_cliente` varchar(20) NOT NULL,
  `telefono_cliente` varchar(20) NOT NULL,
  PRIMARY KEY (`cod_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of clientes
-- ----------------------------
INSERT INTO `clientes` VALUES ('1', 'tigo', 'telecel', '0000000000', '6189000');
INSERT INTO `clientes` VALUES ('2', 'personal', 'nucleo', '0000000000', '6187000');
INSERT INTO `clientes` VALUES ('3', 'vox', 'hola paraguay', '0000000000', '6180600');
INSERT INTO `clientes` VALUES ('4', 'claroo', 'amx paraguay', '000000000', '618500');
INSERT INTO `clientes` VALUES ('6', 'uaa', 'universidad autonoma de auncion', '00000000', '61840000');

-- ----------------------------
-- Table structure for `cta_cobrar`
-- ----------------------------
DROP TABLE IF EXISTS `cta_cobrar`;
CREATE TABLE `cta_cobrar` (
  `id_cta_cobrar` int(11) NOT NULL,
  `factura_id_factura` int(11) NOT NULL,
  PRIMARY KEY (`id_cta_cobrar`),
  KEY `fk_cta_cobrar_factura1_idx` (`factura_id_factura`),
  CONSTRAINT `fk_cta_cobrar_factura1` FOREIGN KEY (`factura_id_factura`) REFERENCES `factura` (`id_factura`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of cta_cobrar
-- ----------------------------

-- ----------------------------
-- Table structure for `empleados`
-- ----------------------------
DROP TABLE IF EXISTS `empleados`;
CREATE TABLE `empleados` (
  `emple_codigo` int(11) NOT NULL AUTO_INCREMENT,
  `emple_nombres` varchar(100) NOT NULL,
  `emple_apellido` varchar(100) NOT NULL,
  `emple_nroci` varchar(20) NOT NULL,
  `emple_direccion` varchar(100) NOT NULL,
  `empleados_telefono` varchar(20) NOT NULL,
  `emple_fechanac` date NOT NULL,
  `cargos_cod_cargos` int(11) NOT NULL,
  PRIMARY KEY (`emple_codigo`) USING BTREE,
  KEY `fk_empleados_cargos1_idx` (`cargos_cod_cargos`),
  CONSTRAINT `fk_empleados_cargos1` FOREIGN KEY (`cargos_cod_cargos`) REFERENCES `cargos` (`id_cargos`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=16384;

-- ----------------------------
-- Records of empleados
-- ----------------------------
INSERT INTO `empleados` VALUES ('1', 'emanuel', 'maldonado', '7744742', 'campo jordan y guerra del 70', '0992810443', '1982-04-30', '1');
INSERT INTO `empleados` VALUES ('2', 'antonio', 'kang', '7894563', 'campo jordan', '580954', '1960-10-09', '15');
INSERT INTO `empleados` VALUES ('3', 'rosa', 'molinas', '3334445', 'campo jordan', '580954', '1960-07-29', '2');
INSERT INTO `empleados` VALUES ('4', 'pepito', 'trulin', '234567', 'san lorenzo', '580954', '1990-03-29', '11');
INSERT INTO `empleados` VALUES ('5', 'juan', 'acosta', '7531598', 'luque', '0981', '1990-01-30', '11');

-- ----------------------------
-- Table structure for `estado`
-- ----------------------------
DROP TABLE IF EXISTS `estado`;
CREATE TABLE `estado` (
  `id_estado` int(11) NOT NULL AUTO_INCREMENT,
  `estado_descripcion` varchar(20) NOT NULL,
  PRIMARY KEY (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of estado
-- ----------------------------
INSERT INTO `estado` VALUES ('1', 'nuevo');
INSERT INTO `estado` VALUES ('2', 'usado');

-- ----------------------------
-- Table structure for `factura`
-- ----------------------------
DROP TABLE IF EXISTS `factura`;
CREATE TABLE `factura` (
  `id_factura` int(11) NOT NULL,
  `fecha_factura` char(11) NOT NULL,
  `clientes_cod_cliente` int(11) NOT NULL,
  `empleados_emple_codigo` int(11) NOT NULL,
  `factura_tipo_id_factura_tipo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_factura`),
  KEY `fk_factura_clientes1_idx` (`clientes_cod_cliente`),
  KEY `fk_factura_factura_tipo1_idx` (`factura_tipo_id_factura_tipo`),
  KEY `fk_final_empleados1_idx` (`empleados_emple_codigo`),
  CONSTRAINT `fk_factura_clientes1` FOREIGN KEY (`clientes_cod_cliente`) REFERENCES `clientes` (`cod_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_factura_factura_tipo1` FOREIGN KEY (`factura_tipo_id_factura_tipo`) REFERENCES `factura_tipo` (`id_factura_tipo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_final_empleados1` FOREIGN KEY (`empleados_emple_codigo`) REFERENCES `empleados` (`emple_codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of factura
-- ----------------------------
INSERT INTO `factura` VALUES ('1', '2016/04/13', '1', '1', '1');
INSERT INTO `factura` VALUES ('2', '2016/04/13', '2', '2', '1');
INSERT INTO `factura` VALUES ('3', '2016/04/13', '3', '3', '1');
INSERT INTO `factura` VALUES ('4', '2016/04/13', '4', '4', '1');
INSERT INTO `factura` VALUES ('5', '2016/04/13', '3', '1', '1');
INSERT INTO `factura` VALUES ('6', '2016/04/13', '4', '1', '1');
INSERT INTO `factura` VALUES ('7', '2016/04/13', '4', '2', '1');

-- ----------------------------
-- Table structure for `factura_detalle`
-- ----------------------------
DROP TABLE IF EXISTS `factura_detalle`;
CREATE TABLE `factura_detalle` (
  `productos_produ_codigo` int(11) NOT NULL,
  `factura_id_factura` int(11) NOT NULL,
  `pagos_id_pago` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  PRIMARY KEY (`productos_produ_codigo`,`factura_id_factura`,`pagos_id_pago`),
  KEY `fk_productos_has_factura_factura1_idx` (`factura_id_factura`),
  KEY `fk_productos_has_factura_productos1_idx` (`productos_produ_codigo`),
  KEY `fk_factura_detalle_pagos1_idx` (`pagos_id_pago`),
  CONSTRAINT `fk_factura_detalle_pagos1` FOREIGN KEY (`pagos_id_pago`) REFERENCES `pagos` (`id_pago`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_has_factura_factura1` FOREIGN KEY (`factura_id_factura`) REFERENCES `factura` (`id_factura`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_has_factura_productos1` FOREIGN KEY (`productos_produ_codigo`) REFERENCES `productos` (`produ_codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of factura_detalle
-- ----------------------------
INSERT INTO `factura_detalle` VALUES ('1', '1', '1', '1');
INSERT INTO `factura_detalle` VALUES ('1', '2', '1', '1');
INSERT INTO `factura_detalle` VALUES ('1', '3', '1', '1');
INSERT INTO `factura_detalle` VALUES ('1', '4', '1', '1');
INSERT INTO `factura_detalle` VALUES ('2', '1', '1', '1');
INSERT INTO `factura_detalle` VALUES ('2', '2', '1', '1');
INSERT INTO `factura_detalle` VALUES ('2', '3', '1', '1');
INSERT INTO `factura_detalle` VALUES ('2', '4', '1', '1');
INSERT INTO `factura_detalle` VALUES ('3', '1', '1', '1');
INSERT INTO `factura_detalle` VALUES ('3', '2', '1', '1');
INSERT INTO `factura_detalle` VALUES ('3', '3', '1', '1');
INSERT INTO `factura_detalle` VALUES ('3', '4', '1', '1');
INSERT INTO `factura_detalle` VALUES ('32', '1', '1', '1');
INSERT INTO `factura_detalle` VALUES ('32', '2', '1', '1');
INSERT INTO `factura_detalle` VALUES ('32', '3', '1', '1');
INSERT INTO `factura_detalle` VALUES ('32', '4', '1', '1');
INSERT INTO `factura_detalle` VALUES ('32', '7', '1', '1');
INSERT INTO `factura_detalle` VALUES ('36', '5', '1', '1');
INSERT INTO `factura_detalle` VALUES ('36', '6', '1', '1');

-- ----------------------------
-- Table structure for `factura_tipo`
-- ----------------------------
DROP TABLE IF EXISTS `factura_tipo`;
CREATE TABLE `factura_tipo` (
  `id_factura_tipo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion_tipo_factura` varchar(20) NOT NULL,
  PRIMARY KEY (`id_factura_tipo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of factura_tipo
-- ----------------------------
INSERT INTO `factura_tipo` VALUES ('1', 'contado');
INSERT INTO `factura_tipo` VALUES ('2', 'credito');

-- ----------------------------
-- Table structure for `impuesto`
-- ----------------------------
DROP TABLE IF EXISTS `impuesto`;
CREATE TABLE `impuesto` (
  `id_impuesto` int(11) NOT NULL AUTO_INCREMENT,
  `descri_impuesto` varchar(20) NOT NULL,
  PRIMARY KEY (`id_impuesto`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of impuesto
-- ----------------------------
INSERT INTO `impuesto` VALUES ('1', '5%');
INSERT INTO `impuesto` VALUES ('2', '10%');

-- ----------------------------
-- Table structure for `pagos`
-- ----------------------------
DROP TABLE IF EXISTS `pagos`;
CREATE TABLE `pagos` (
  `id_pago` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion_pago` varchar(20) NOT NULL,
  PRIMARY KEY (`id_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of pagos
-- ----------------------------
INSERT INTO `pagos` VALUES ('1', 'efectivo');
INSERT INTO `pagos` VALUES ('2', 'tarjeta');
INSERT INTO `pagos` VALUES ('3', 'cheque');
INSERT INTO `pagos` VALUES ('4', 'debito');

-- ----------------------------
-- Table structure for `pago_detalle`
-- ----------------------------
DROP TABLE IF EXISTS `pago_detalle`;
CREATE TABLE `pago_detalle` (
  `factura_id_factura` int(11) NOT NULL,
  PRIMARY KEY (`factura_id_factura`),
  KEY `fk_factura_has_pagos_factura1_idx` (`factura_id_factura`),
  CONSTRAINT `fk_factura_has_pagos_factura1` FOREIGN KEY (`factura_id_factura`) REFERENCES `factura` (`id_factura`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pago_detalle
-- ----------------------------

-- ----------------------------
-- Table structure for `perfiles`
-- ----------------------------
DROP TABLE IF EXISTS `perfiles`;
CREATE TABLE `perfiles` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(20) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of perfiles
-- ----------------------------
INSERT INTO `perfiles` VALUES ('1', 'admin');
INSERT INTO `perfiles` VALUES ('2', 'invitado');

-- ----------------------------
-- Table structure for `procedencia`
-- ----------------------------
DROP TABLE IF EXISTS `procedencia`;
CREATE TABLE `procedencia` (
  `cod_procedencia` int(11) NOT NULL AUTO_INCREMENT,
  `procedencia_descrip` varchar(110) NOT NULL,
  PRIMARY KEY (`cod_procedencia`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of procedencia
-- ----------------------------
INSERT INTO `procedencia` VALUES ('1', 'chile');
INSERT INTO `procedencia` VALUES ('2', 'japon');
INSERT INTO `procedencia` VALUES ('3', 'brasil');
INSERT INTO `procedencia` VALUES ('5', 'paraguay');
INSERT INTO `procedencia` VALUES ('6', 'uruguay');
INSERT INTO `procedencia` VALUES ('7', 'venezuela');
INSERT INTO `procedencia` VALUES ('8', 'mexico');
INSERT INTO `procedencia` VALUES ('9', 'canada');
INSERT INTO `procedencia` VALUES ('10', 'china');
INSERT INTO `procedencia` VALUES ('11', 'argentina');
INSERT INTO `procedencia` VALUES ('12', 'equador');
INSERT INTO `procedencia` VALUES ('13', 'mongoolia');
INSERT INTO `procedencia` VALUES ('15', 'boliviaa');
INSERT INTO `procedencia` VALUES ('16', 'españaa');
INSERT INTO `procedencia` VALUES ('17', 'rusiaaa');

-- ----------------------------
-- Table structure for `productos`
-- ----------------------------
DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `produ_codigo` int(11) NOT NULL AUTO_INCREMENT,
  `produ_descripcion` varchar(100) NOT NULL,
  `productos_marca` varchar(20) NOT NULL,
  `produ_precio_comp` int(11) NOT NULL,
  `produ_precio_vent` int(11) NOT NULL,
  `productos_año` int(11) NOT NULL,
  `productos_km` int(11) NOT NULL,
  `impuesto_id_impuesto` int(11) NOT NULL,
  `procedencia_cod_procedencia` int(11) NOT NULL,
  `productos_cantidad` int(11) NOT NULL,
  `estado_id_estado` int(11) NOT NULL,
  PRIMARY KEY (`produ_codigo`) USING BTREE,
  KEY `fk_productos_impuesto1_idx1` (`impuesto_id_impuesto`),
  KEY `fk_productos_procedencia1_idx` (`procedencia_cod_procedencia`),
  KEY `fk_productos_estado1_idx` (`estado_id_estado`),
  CONSTRAINT `fk_productos_estado1` FOREIGN KEY (`estado_id_estado`) REFERENCES `estado` (`id_estado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_impuesto1` FOREIGN KEY (`impuesto_id_impuesto`) REFERENCES `impuesto` (`id_impuesto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_procedencia1` FOREIGN KEY (`procedencia_cod_procedencia`) REFERENCES `procedencia` (`cod_procedencia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=2730;

-- ----------------------------
-- Records of productos
-- ----------------------------
INSERT INTO `productos` VALUES ('1', 'corola', 'toyota', '40000', '55000', '2016', '0', '1', '1', '1', '1');
INSERT INTO `productos` VALUES ('2', 'sentra', 'nissan', '25000', '30000', '2010', '15000', '1', '2', '1', '2');
INSERT INTO `productos` VALUES ('3', 'swif', 'susuki', '10000', '15000', '2016', '30000', '2', '3', '1', '2');
INSERT INTO `productos` VALUES ('32', 'slk', 'mercedes', '70000', '80000', '2015', '0', '2', '16', '10', '1');
INSERT INTO `productos` VALUES ('35', 'fiesta', 'ford', '2000', '3000', '2005', '0', '2', '3', '1', '1');
INSERT INTO `productos` VALUES ('36', 'mustang', 'ford', '50000', '70000', '1970', '2700000', '1', '1', '1', '2');

-- ----------------------------
-- Table structure for `t_usuarios`
-- ----------------------------
DROP TABLE IF EXISTS `t_usuarios`;
CREATE TABLE `t_usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(20) NOT NULL,
  `pass` varchar(20) NOT NULL,
  `estado` tinyint(1) DEFAULT '1',
  `perfiles_codigo` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `id_user` (`id_usuario`),
  KEY `fk_t_usuarios_perfiles1_idx` (`perfiles_codigo`),
  CONSTRAINT `fk_t_usuarios_perfiles1` FOREIGN KEY (`perfiles_codigo`) REFERENCES `perfiles` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 PACK_KEYS=0;

-- ----------------------------
-- Records of t_usuarios
-- ----------------------------
INSERT INTO `t_usuarios` VALUES ('1', 'manu', '123', '1', '1');
INSERT INTO `t_usuarios` VALUES ('2', 'pola', '000', '1', '2');
