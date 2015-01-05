package com.mycompany.kaeser.txt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


public class Transforma {

	public static void toTXT57(DTEBean bean) throws Exception {
		
		Collection<Map<String, String>> detalle = new ArrayList<Map<String, String>> ( );
		Collection<Map<String, String>> subdescuentos = new ArrayList<Map<String, String>> ( );
		Collection<Map<String, String>> codigos = new ArrayList<Map<String, String>> ( );
		Collection<Map<String, String>> referehncias = new ArrayList<Map<String, String>> ( );
		String txt = "";
		String encabezadoArray[] = null;
		String detalleArray[] = null;
		String vlibresArray[] = null;
		String referenciaArray[] = null;
		String actecoArray[] = null;
		String codigosArray[] = null;
		String subdescuentosArray[] = null;

		try {
			
			String nombreFichero = bean.getPathtxt();
			
			BufferedReader br = null;
			try {
			   br = new BufferedReader(new FileReader(nombreFichero));
			   String texto = br.readLine();
			   while(texto != null)
			   {
				   String inicio=texto.substring(0, 2);
				   if(inicio.equals("A0")){
			       vlibresArray=texto.split(";", -1);
				   }
				   if(inicio.equals("A1")){
				       actecoArray=texto.split(";", -1);
					   }
				   if(inicio.equals("B1")){
				       subdescuentosArray=texto.split(";", -1);
					   }
				   if(inicio.equals("B2")){
				       codigosArray=texto.split(";", -1);
					   }
				   if(inicio.equals("A;")){
					   encabezadoArray=texto.split(";", -1);
					   System.out.println("hola "+encabezadoArray[28]);
				   }
				   if(inicio.equals("B;")){
					   detalleArray=texto.split(";", -1);
					   Map<String, String> det = new HashMap<String, String>();
							det.put("Numero Linea", detalleArray[1]);
							det.put("Indicador Exencion", detalleArray[2]);
							det.put("Numero Parte", detalleArray[3]);
							det.put("Descripcion", "" + detalleArray[4]);
							det.put("Cantidad Referencia", "" + detalleArray[5]);
							det.put("Unidad Referencia", "" + detalleArray[6]);	
							det.put("Precio Referencia", "" + detalleArray[7]);
							det.put("Cantidad", "" + detalleArray[8]);
							det.put("Fecha Elaboracion", "" + detalleArray[9]);
							det.put("Fecha Vencimiento", "" + detalleArray[10]);
								det.put("Sales Unit", "" + detalleArray[11]);
								det.put("Unit Price", "" + detalleArray[12]);
								det.put("Precio Unitario en  Otra Moneda", "" + detalleArray[13]);
								det.put("Codigo de Otra Moneda", detalleArray[14]);
								det.put("Factor de Conversion a pesos", "" + detalleArray[15]);
								det.put("Discount porcent", "" + detalleArray[16]);
								det.put("Discoun amount", "" + detalleArray[17]);
								det.put("Porcentaje de  Recargo", "" + detalleArray[18]);
								det.put("Monto del Recargo", "" + detalleArray[19]);
								det.put("Codigo de Impuesto Adicional", "" + detalleArray[20]);
								det.put("Total ampount", "" + detalleArray[21]);
						detalle.add(det);
				   }
				   if (inicio.equals("D;")){
					   referenciaArray=texto.split(";", -1);
				   }
			       texto = br.readLine();
			   }
			   

				// Variables Libres
                                bean.setCodigokaeser(vlibresArray[6]);
				String varLibre = "";
				varLibre += "A0;";
				varLibre += vlibresArray[1]+";";
				varLibre += vlibresArray[2]+";";
				varLibre += vlibresArray[3]+";";
				varLibre += vlibresArray[4]+";";
				varLibre += vlibresArray[5]+";";
				varLibre += vlibresArray[6]+";";
				varLibre += vlibresArray[7]+";";
				varLibre += vlibresArray[8]+";";
				varLibre += vlibresArray[9]+";";
				varLibre += vlibresArray[10]+";";
				varLibre += "\n";

				// Se asigna a folio
				bean.setRutEmisor(encabezadoArray[18]);
				bean.setTipoDTE(encabezadoArray[1]);
				bean.setFolioDTE(encabezadoArray[3]);
				bean.setSucursalEmisorDTE(encabezadoArray[21]);

				String encabezado = "";
				encabezado += "A;";
				
				 
				encabezado += bean.getTipoDTE()+";"; 			// pos_1_Tipo
											// Electrï¿½nico
				encabezado += ";"; 					// pos_2_Tipo Impresiï¿½n
				encabezado += encabezadoArray[3] + ";"; 		// pos_3_FOLIO-Documento
				

				encabezado += encabezadoArray[4] + ";"; 		// pos_4_Fecha
											// de
											// Emisiï¿½n
				encabezado += encabezadoArray[5]
						+ ";"; // pos_5_Indicador de No Rebaja
				encabezado += encabezadoArray[6]
						+ ";"; // pos_6_Tipo Despacho
                                if(!bean.getTipoDTE().equals("52"))
                                    encabezadoArray[7]="";
				encabezado += encabezadoArray[7]
						+ ";"; // pos_7_Indicador Tipo de Translado de bienes
				encabezado += encabezadoArray[8]
						+ ";"; // pos_8_Indicador Servicio
				encabezado +=  ";"; // pos_9_Indicador
                                                    // Montos
                                                    // Brutos
				encabezado += encabezadoArray[10]+";"; // pos_10_Forma
                                                                        // de
                                                                        // Pago
				encabezado += encabezadoArray[11] + ";"; // pos_11_Fecha
                                                                        // de
                                                                        // Cancelaciï¿½n
				encabezado += encabezadoArray[12]
						+ ";"; // pos_12_Periodo desde
				encabezado += encabezadoArray[13]
						+ ";"; // pos_13_Periodo hasta
				encabezado += encabezadoArray[14] + ";"; // pos_14_Medio
																					// de
																					// Pago
				encabezado += encabezadoArray[15]
						+ ";"; // pos_15_Tï¿½rmino de Pago, cï¿½digo
				encabezado += 
						";"; // pos_16_Tï¿½rmino de Pago, dï¿½as
				encabezado += encabezadoArray[17]+";"; // pos_17_Fecha
																					// de
																					// Vencimiento

				encabezado += encabezadoArray[18] + ";"; // pos_18_RUT
																						// Emisor
				encabezado += encabezadoArray[19] + ";"; // pos_19_Nombre
																					// o
																					// Razï¿½n
																					// Social
																					// Emisor
				encabezado += encabezadoArray[20] + ";"; // pos_20_Giro
																					// del
																					// Negocio
																					// del
																					// Emisor
				encabezado += encabezadoArray[21] + ";"; // pos_21_Sucursal
				encabezado += encabezadoArray[22]
						+ ";"; // pos_22_Cï¿½digo Sucursal
				encabezado += encabezadoArray[23] + ";"; // pos_23_Direcciï¿½n
																						// Origen
				if (encabezadoArray[24].length()>=20)
                                    encabezadoArray[24]=encabezadoArray[24].substring(0,16)+"...";
                                encabezado += encabezadoArray[24]
						+ ";"; // pos_24_Comuna Origen
				encabezado += encabezadoArray[25]
						+ ";"; // pos_25_Ciuidad Origen
				encabezado += encabezadoArray[26]
						+ ";"; // pos_26_Cï¿½digo del Vendedor

				encabezado += encabezadoArray[27] + ";"; // pos_27_RUT
																				// Mandante

				encabezado += encabezadoArray[28]
						+ ";"; // pos_28_RUT Receptor
				encabezado += encabezadoArray[29]
						+ ";"; // pos_29_Cï¿½digo Interno del Receptor
				encabezado += encabezadoArray[30]
						+ ";"; // pos_30_Nombre o Razï¿½n Social Receptor
				encabezado += encabezadoArray[31]
						+ ";"; // pos_31_Giro del Negocio del Receptor
				encabezado += encabezadoArray[32]
						+ ";"; // pos_32_Contacto receptor
				encabezado += encabezadoArray[33]
						+ ";"; // pos_33_Direcciï¿½n Receptor
                                if (encabezadoArray[34].length()>=20)
                                    encabezadoArray[34]=encabezadoArray[34].substring(0,16)+"...";
				encabezado += encabezadoArray[34]
						+ ";"; // pos_34_Comuna Receptor
				encabezado += encabezadoArray[35]
						+ ";"; // pos_35_Ciudad Receptor
				encabezado += encabezadoArray[36]
						+ ";"; // pos_36_Direcciï¿½n Postal
				encabezado += encabezadoArray[37]
						+ ";"; // pos_37_Comuna Postal
				encabezado += encabezadoArray[38]
						+ ";"; // pos_38_Ciudad Postal

				encabezado += encabezadoArray[39] + ";"; // pos_39_RUT
																				// de
																				// Solicitante
																				// de
																				// Factura

				encabezado += encabezadoArray[40]
						+ ";"; // pos_40_Informaciï¿½n Transporte (Patente)
				encabezado += encabezadoArray[41]
						+ ";"; // pos_41_RUT Transportista
				encabezado += encabezadoArray[42]
						+ ";"; // pos_42_Direcciï¿½n Destino
				encabezado += encabezadoArray[43]
						+ ";"; // pos_43_Comuna Destino
				encabezado += encabezadoArray[44]
						+ ";"; // pos_44_Ciudad Destino

				encabezado += encabezadoArray[45] + ";"; // pos_45_Monto
																					// Neto
				encabezado += encabezadoArray[46] + ";"; // pos_46_Monto
																					// No
																					// Afecto
																					// o
																					// Exento
				encabezado += encabezadoArray[47] + ";"; // pos_47_Monto
																					// base
																					// faenamiento
																					// carne
				encabezado += encabezadoArray[48] + ";"; // pos_48_Tasa
																					// IVA
				encabezado += encabezadoArray[49] + ";"; // pos_49_IVA
				encabezado += encabezadoArray[50] + ";"; // pos_50_IVA
																						// no
																						// Retenido
				encabezado += encabezadoArray[51] + ";"; // pos_51_Crï¿½dito
																					// especial
																					// 65%
																					// Empresas
																					// Contructoras
				encabezado += encabezadoArray[52]
						+ ";"; // pos_52_Monto Perï¿½odo
				encabezado += encabezadoArray[53] + ";"; // pos_53_Garantia
																					// por
																					// deposito
																					// o
																					// envases
																					// o
																					// embalajes
				encabezado += encabezadoArray[54] + ";"; // pos_54_Monto
																					// No
																					// Facturable
				encabezado += encabezadoArray[55] + ";"; // pos_55_Monto
																						// Total
				encabezado += encabezadoArray[56]
						+ ";"; // pos_56_Saldo Anterior
				encabezado += encabezadoArray[57] + ";"; // pos_57_Valor
																						// a
																						// pagar
				encabezado += "\n";

				// ACTECO
				encabezado += "A1;";
				encabezado += actecoArray[1] + ";";
				encabezado += "\n";

				String detalles = "";
				int contador=1;
				for (Map<String, String> map: detalle) {
//					if (detalle.valueOf("IndExe").contains("2"))
//						continue;
					String nombre = map.get("Descripcion");
					String descri = "";
					
					if (map.get("Descripcion").contains("*")){
						int pos= nombre.indexOf("*");
						descri = nombre.substring(pos+1);
						nombre = nombre.substring (0,pos);
					}
					if(!nombre.equals("")){
					
					detalles += "B;";
					detalles += map.get("Numero Linea") + ";"; // pos_1_Nï¿½ de
																	// Lï¿½nea o Nï¿½
																	// Secuencial
					detalles += map.get("Indicador Exencion") + ";"; // pos_2_Indicador
																	// de
																	// facturaciï¿½n/
																	// exenciï¿½n
					detalles += nombre + ";"; // pos_3_Nombre
																	// del ï¿½tem
					detalles += descri + ";"; 								// pos_4_Descripciï¿½n
																	// Adicional
					detalles += map.get("Cantidad Referencia") + ";"; // pos_5_Cantidad
																	// de Referencia
					detalles += map.get("Unidad Referencia") + ";"; // pos_6_Unidad de
																	// Medida de
																	// Referencia
					detalles += map.get("Precio Referencia") + ";"; // pos_7_Precio de
																	// Referencia
					detalles += map.get("Cantidad") + ";"; // pos_8_Cantidad
																	// del ï¿½tem
					detalles += map.get("Fecha Elaboracion") + ";"; // pos_9_Fecha
																	// Eleboraciï¿½n
					detalles += map.get("Fecha Vencimiento") + ";"; // pos_10_Fecha
																	// Vencimiento
					detalles += map.get("Sales Unit") + ";"; // pos_11_Unidad
																	// de Medida
					detalles += map.get("Unit Price") + ";"; // pos_12_Precio
																	// Unitario del
																	// ï¿½tem
					detalles += map.get("Precio Unitario en  Otra Moneda") + ";"; // pos_13_Precio Unitario en Otra Moneda
					detalles += map.get("Codigo de Otra Moneda") +";"; // pos_14_Cï¿½digo de Otra Moneda
					detalles += map.get("Factor de Conversion a pesos") +";"; // pos_15_Factor de Conversiï¿½n
					detalles += map.get("Discount porcent") + ";"; // pos_16_Descuento
																		// en %
					detalles += map.get("Discoun amount") + ";"; // pos_17_Monto
																			// Descuento
					detalles += map.get("Porcentaje de  Recargo") + ";"; // pos_18_Recargo
																		// en %
					detalles += map.get("Monto del Recargo") + ";"; // pos_19_Monto
																		// de
																		// Recargo
					detalles += map.get("Codigo de Impuesto Adicional") + ";"; // pos_20_Cï¿½digo
																		// Impuesto
																		// o
																		// Retenciones
					detalles += map.get("Total ampount") + ";"; // pos_21_Monto
																	// de ï¿½tem
					detalles += "\n";
					detalles += "B2;";
					detalles += "Interno;";
					detalles += map.get("Numero Parte") + ";";
					detalles += "\n";
					}		
					contador++;
				}
				String descripcion = "";
				String nombre2 = "";
				String repeticion = vlibresArray[7];
                                if (repeticion.contains("*")){
				while (repeticion.contains("*")){
				 int pos= repeticion.indexOf("*");
				 descripcion = repeticion.substring(0,pos);
				 repeticion = repeticion.substring(pos+1);
				 if (descripcion.equals(""))
					 descripcion = "-";
				 detalles += "B;";
					detalles += contador + ";"; // pos_1_Nï¿½ de
																	// Lï¿½nea o Nï¿½
																	// Secuencial
					detalles +=  "2;"; // pos_2_Indicador
																	// de
																	// facturaciï¿½n/
																	// exenciï¿½n
					detalles += descripcion+";"; // pos_3_Nombre
																	// del ï¿½tem
					detalles += ";"; 								// pos_4_Descripciï¿½n
																	// Adicional
					detalles += ";"; // pos_5_Cantidad
																	// de Referencia
					detalles += ";"; // pos_6_Unidad de
																	// Medida de
																	// Referencia
					detalles += ";"; // pos_7_Precio de
																	// Referencia
					detalles += ";"; // pos_8_Cantidad
																	// del ï¿½tem
					detalles += ";"; // pos_9_Fecha
																	// Eleboraciï¿½n
					detalles += ";"; // pos_10_Fecha
																	// Vencimiento
					detalles += ";"; // pos_11_Unidad
																	// de Medida
					detalles += ";"; // pos_12_Precio
																	// Unitario del
																	// ï¿½tem
					detalles +=  ";"; // pos_13_Precio Unitario en Otra Moneda
					detalles += ";"; // pos_14_Cï¿½digo de Otra Moneda
					detalles += ";"; // pos_15_Factor de Conversiï¿½n
					detalles +=  ";"; // pos_16_Descuento
																		// en %
					detalles +=  ";"; // pos_17_Monto
																			// Descuento
					detalles +=  ";"; // pos_18_Recargo
																		// en %
					detalles += ";"; // pos_19_Monto
																		// de
																		// Recargo
					detalles +=  ";"; // pos_20_Cï¿½digo
																		// Impuesto
																		// o
																		// Retenciones
					detalles +=  "0;"; // pos_21_Monto
																	// de ï¿½tem
					detalles += "\n";
					contador ++;
				}
                                
                                if (!repeticion.contains("*")){
                                    descripcion = repeticion;
				 if (descripcion.equals(""))
					 descripcion = "-";
				 detalles += "B;";
					detalles += contador + ";"; // pos_1_Nï¿½ de
																	// Lï¿½nea o Nï¿½
																	// Secuencial
					detalles +=  "2;"; // pos_2_Indicador
																	// de
																	// facturaciï¿½n/
																	// exenciï¿½n
					detalles += descripcion+";"; // pos_3_Nombre
																	// del ï¿½tem
					detalles += ";"; 								// pos_4_Descripciï¿½n
																	// Adicional
					detalles += ";"; // pos_5_Cantidad
																	// de Referencia
					detalles += ";"; // pos_6_Unidad de
																	// Medida de
																	// Referencia
					detalles += ";"; // pos_7_Precio de
																	// Referencia
					detalles += ";"; // pos_8_Cantidad
																	// del ï¿½tem
					detalles += ";"; // pos_9_Fecha
																	// Eleboraciï¿½n
					detalles += ";"; // pos_10_Fecha
																	// Vencimiento
					detalles += ";"; // pos_11_Unidad
																	// de Medida
					detalles += ";"; // pos_12_Precio
																	// Unitario del
																	// ï¿½tem
					detalles +=  ";"; // pos_13_Precio Unitario en Otra Moneda
					detalles += ";"; // pos_14_Cï¿½digo de Otra Moneda
					detalles += ";"; // pos_15_Factor de Conversiï¿½n
					detalles +=  ";"; // pos_16_Descuento
																		// en %
					detalles +=  ";"; // pos_17_Monto
																			// Descuento
					detalles +=  ";"; // pos_18_Recargo
																		// en %
					detalles += ";"; // pos_19_Monto
																		// de
																		// Recargo
					detalles +=  ";"; // pos_20_Cï¿½digo
																		// Impuesto
																		// o
																		// Retenciones
					detalles +=  "0;"; // pos_21_Monto
																	// de ï¿½tem
					detalles += "\n";
					contador ++;
                                }
                                
                                } else {
                                    
				 descripcion = repeticion;
				 if (descripcion.equals(""))
					 descripcion = "-";
				 detalles += "B;";
					detalles += contador + ";"; // pos_1_Nï¿½ de
																	// Lï¿½nea o Nï¿½
																	// Secuencial
					detalles +=  "2;"; // pos_2_Indicador
																	// de
																	// facturaciï¿½n/
																	// exenciï¿½n
					detalles += descripcion+";"; // pos_3_Nombre
																	// del ï¿½tem
					detalles += ";"; 								// pos_4_Descripciï¿½n
																	// Adicional
					detalles += ";"; // pos_5_Cantidad
																	// de Referencia
					detalles += ";"; // pos_6_Unidad de
																	// Medida de
																	// Referencia
					detalles += ";"; // pos_7_Precio de
																	// Referencia
					detalles += ";"; // pos_8_Cantidad
																	// del ï¿½tem
					detalles += ";"; // pos_9_Fecha
																	// Eleboraciï¿½n
					detalles += ";"; // pos_10_Fecha
																	// Vencimiento
					detalles += ";"; // pos_11_Unidad
																	// de Medida
					detalles += ";"; // pos_12_Precio
																	// Unitario del
																	// ï¿½tem
					detalles +=  ";"; // pos_13_Precio Unitario en Otra Moneda
					detalles += ";"; // pos_14_Cï¿½digo de Otra Moneda
					detalles += ";"; // pos_15_Factor de Conversiï¿½n
					detalles +=  ";"; // pos_16_Descuento
																		// en %
					detalles +=  ";"; // pos_17_Monto
																			// Descuento
					detalles +=  ";"; // pos_18_Recargo
																		// en %
					detalles += ";"; // pos_19_Monto
																		// de
																		// Recargo
					detalles +=  ";"; // pos_20_Cï¿½digo
																		// Impuesto
																		// o
																		// Retenciones
					detalles +=  "0;"; // pos_21_Monto
																	// de ï¿½tem
					detalles += "\n";
					contador ++;
				
                                }
				
				
				
				

//				String recargosDescuentos = "";
//				@SuppressWarnings("unchecked")
//				List<Node> descRecaList = XMLdom.selectNodes("//DscRcgGlobal");
//				for (Node descReca : descRecaList) {
//					recargosDescuentos += "C;";
//					recargosDescuentos += descReca.valueOf("NroLinDR") + ";"; // pos_1_Nï¿½
//																				// de
//																				// Lï¿½nea
//																				// o
//																				// Nï¿½
//																				// Secuencial
//					recargosDescuentos += descReca.valueOf("TpoMov") + ";"; // pos_2_Tipo
//																			// de
//																			// Movimiento
//					recargosDescuentos += descReca.valueOf("GlosaDR") + ";"; // pos_3_Glosa
//					recargosDescuentos += descReca.valueOf("TpoValor") + ";"; // pos_4_Tipo
//																				// de
//																				// Valor
//					recargosDescuentos += descReca.valueOf("ValorDR") + ";"; // pos_5_Valor
//					recargosDescuentos += descReca.valueOf("IndExeDR") + ";"; // pos_6_Indicador
//																				// de
//																				// facturaciï¿½n/
//																				// exenciï¿½n
//					recargosDescuentos += "\n";
//				}
				
				//Borrar despues de hacer las pruebas
				if(bean.getTipoDTE().equals("61")){
					if (referenciaArray[2].equals("801")){
						referenciaArray[2]="33";
					}
				}
				String referencias = "";
				referencias += "D;";
				referencias += referenciaArray[1]+ ";"; // pos_1_Nï¿½ de Lï¿½nea
				referencias += referenciaArray[2] + ";"; // pos_2_Tipo
																// Documento de
																// referencia
				referencias += referenciaArray[3] + ";"; // pos_3_Indicador
																// de Referencia
																// Global
				referencias += referenciaArray[4] + ";"; // pos_4_FOLIO-
																// de referencia
				referencias += referenciaArray[5] + ";"; // pos_5_RUT Otro
																// contribuyente
				referencias += referenciaArray[6] + ";"; // pos_6_FECHA de
																// la Referencia
				referencias += referenciaArray[7] + ";"; // pos_7_Cï¿½digo de
																// referencia
				referencias += referenciaArray[8] + ";"; // pos_8_Razï¿½n
																// referencia
				referencias += "\n";
			

				txt = varLibre + encabezado + detalles 
						+ referencias;
			   
			}
			finally {
			    try {
			        if(br != null)
			            br.close();
			    }
			    catch (Exception e) {
			        System.out.println("Error al cerrar el fichero");
			        System.out.println(e.getMessage());
			    }
			}


			
			
			

		} catch (Exception e) {
			throw new Exception("ERROR al parserar XML: " + e.getMessage(), e);
		}

		bean.setTXT(txt);
	}

	
	

}
