package com.mycompany.kaeser.txt;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import cl.facele.docele.soap.logica.*;

/**
 * Hello world!
 *
 */
public class App {
	private static Path dirDTE;
        private static long folio = 1;
    public static void main( String[] args ) throws Exception {
        dirDTE = Paths.get(System.getProperty("user.home"), "Facele", "Kaeser", "txt");
        if (Files.notExists(dirDTE))
        	Files.createDirectories(dirDTE);
        
        doit();
    }
    
	private static void doit() throws Exception {
		System.out.print("hola mundo");

		DirectoryStream<Path> directory = null;
		DTEBean bean;
		Transforma tr;
                Soap soap;
		
		try {
			directory = Files.newDirectoryStream(dirDTE);
			//Obtiene folio menor
			for (Path filePath : directory) {
                                if(Files.isDirectory(filePath))
                                    continue;
				tr = new Transforma();
				bean = new DTEBean();
				soap = new Soap(Soap.SOAP_SFE);
                                System.out.println(System.getProperty("os.name").toLowerCase());
                                if(filePath.getFileName().toString().contains(".DS_Store"))
                                    System.out.println("archivo no valido");
                                else{
                                File original = new File (filePath.toString());
				bean.setContendioFile(new String(Files.readAllBytes(filePath), Charset.forName("ISO-8859-1")));
				bean.setPathtxt(filePath.toString());
				bean.setTipoDTE("61");		
				bean.setRutEmisor("99520290-5");		
				Transforma.toTXT57(bean);
                                System.out.println(filePath.toString());
				
				System.out.println(bean.getTXT());
				
				
				
				
				Thread.sleep(10);
                                try{
                                        soap.generaDTE(bean.getRutEmisor(), "999999999", bean.getTXT());
                                        folio=soap.folioDTE;


                                        Thread.sleep(10);
                                        File file;
                                        if(System.getProperty("os.name").toLowerCase().contains("win"))
                                        file = new File(Paths.get(System.getProperty("user.home"), "Facele", "Kaeser", "txt", "proceso-ok")+"\\"+bean.getRutEmisor()+"_T"+bean.getTipoDTE()+"_F"+folio+".txt");
                                        else
                                             file = new File(Paths.get(System.getProperty("user.home"), "Facele", "Kaeser", "txt", "proceso-ok")+"/"+bean.getRutEmisor()+"_T"+bean.getTipoDTE()+"_F"+folio+".txt");

                                        // if file doesnt exists, then create it
                                        if (!file.exists()) {
                                                file.createNewFile();
                                        }

                                        FileWriter fw = new FileWriter(file.getAbsoluteFile());
                                        BufferedWriter bw = new BufferedWriter(fw);
                                        bw.write(bean.getTXT());
                                        bw.close();
                                        original.delete();
                                }catch (Exception h) {
                                    File file;
                                    if(System.getProperty("os.name").toLowerCase().contains("win"))
                                	 file = new File(Paths.get(System.getProperty("user.home"), "Facele", "Kaeser", "txt",  "proceso-error")+"\\"+bean.getRutEmisor()+"_T"+bean.getTipoDTE()+"_F"+bean.getCodigokaeser()+".txt");
				    else
                                        file = new File(Paths.get(System.getProperty("user.home"), "Facele", "Kaeser", "txt",  "proceso-error")+"/"+bean.getRutEmisor()+"_T"+bean.getTipoDTE()+"_F"+bean.getCodigokaeser()+".txt");
                                        
                                        // if file doesnt exists, then create it
                                        if (!file.exists()) {
                                                file.createNewFile();
                                        }

                                        FileWriter fw = new FileWriter(file.getAbsoluteFile());
                                        BufferedWriter bw = new BufferedWriter(fw);
                                        bw.write(new String(Files.readAllBytes(filePath), Charset.forName("ISO-8859-1")));
                                        bw.close();
                                        original.delete();
                                } 

				
			}}
		} catch (Exception e) {
			throw new Exception("Error iterando archivos: " + e.getMessage(), e);
		} finally {
			try {directory.close();
			} catch (Exception e) {
				Logger.getLogger(App.class.getCanonicalName()).log(Level.INFO, e.getMessage());
			}
		}
		
	}
}
