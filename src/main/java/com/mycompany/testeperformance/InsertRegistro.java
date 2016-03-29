/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testeperformance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Esta implementacao de teste de performance do Insert agrupa 1000 inserts por vez e insere no banco 
 * de dados. Utiliza multiplas queries separados por ; 14000 inserts em 9 minutos
 * 
 * @author lprates
 */
public class InsertRegistro {
    
    public static void main(String args[]){

        Connection con = null;
        
        try{
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?allowMultiQueries=true","root","root" ); 
            
            String path = "C:/Users/lprates/Desktop/arquivo/EFD_Formula_1443_1444.txt" ; 
            //String path = "C:/Users/lprates/Desktop/arquivo/teste.txt" ; 
            
            String INSERT = " INSERT INTO sped(conteudo) VALUES ( '" ;  
            
            BufferedReader bufReader = new BufferedReader( new FileReader(path) );

            int contador = 0;
            String linha = null;
            String SQL = "";
            
            System.out.println("Inserindo Registros ..............");
            
            long start = System.currentTimeMillis();
            
            
            while ( ( linha = bufReader.readLine() ) != null ){
                SQL = SQL + INSERT + linha + "' ) ; " ;
                contador++;
                if ( contador > 1000 ){
                    Statement st = con.createStatement(); 
                    st.execute( SQL );
                    st.close();
                    
                    SQL = "";
                    contador = 0 ; 
                }
            }
            
            if ( contador > 0 ){
                Statement st = con.createStatement(); 
                st.execute( SQL ); 
                st.close();
            }
            long end = System.currentTimeMillis();
            
            System.out.println("Demorou : " + ((end - start) / 1000) + " segundos"  );
            
            System.out.println("Fim da Insercao dos Registros ..............");
            
        }catch(Exception ex){
            System.out.println("Erro:[" + ex+"]");
        }finally{
            try{
                con.close();
            }catch(Exception ex ) {}
        }
        
    }
    
}
