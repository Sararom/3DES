/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripledestest;

//Importada para utilizar método "copyOf"
import java.util.Arrays;
//Utilizada para las funciones "Base64.getEncoder().encode()/getDencoder().decode()"
//La principal razon es utilizar una base que contenga caracteres compatibles con la mayoria de algoritmos de
//encriptamiento y tambien que se pueda imprimir.
import java.util.Base64;
//Utilizada para crear una instancia del Cipher (cifrador) cuya funcion es cifrar y descifrar el texto plano
import javax.crypto.Cipher;
//Utilizado para crear un objeto "SecretKey" que pueda implementar el constructor "SecretKeySpec()"
import javax.crypto.SecretKey;
//Utilizado para convertir el arreglo de bytes en una llave secreta con el constructor "SecretKeySpec"
import javax.crypto.spec.SecretKeySpec;


public class TripleDESTest {
    private String encrypt(String clearText, String secretKey) {
        try {
            //convierte la key obtenida en string a un arreglo de bytes en el formato utf-8 
            byte[] bytePass = secretKey.getBytes("utf-8");
            //copia el arreglo de bytes creado a un arreglo de 24 elementos
            byte[] byteKey = Arrays.copyOf(bytePass, 24);
            
            //Convierte el arreglo de bytes a una clave correspondiente de el TripleDES con 168 bits 
            SecretKey key = new SecretKeySpec(byteKey, "DESede");
            //Crea una instancia del Cipher que es la principal herramienta de la librería crypto
            //Se le envía de parámetro el algoritmo de encriptamiento que se desea implementar
            Cipher cipher = Cipher.getInstance("DESede");
            //Se inicializa el cipher en modo encriptación, se le envía la llave con el formato correcto
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Se convierte el texto plano a un arreglo de bytes
            byte[] byteText = clearText.getBytes("utf-8");
            //El cipher encripta el arreglo de bytes enviado y se resetea. El resultado se guarda en un buffer
            byte[] buf = cipher.doFinal(byteText);
            //Convierte el arreglo de bits encriptados y los convierte a Base64
            byte[] byteBase64 = Base64.getEncoder().encode(buf);
   
            //System.out.println(byteBase64);
            
            //Convierte en arreglo de bytes base 64 en cadena formato utf-8
            String data = new String(byteBase64, "utf-8");
            
            return data;
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
    }
    
    private String decrypt(String data, String secretKey) {
        try {
            //El texto encriptado se convierte de base 64 a un arreglo de bytes
            byte[] byteData = Base64.getDecoder().decode(data.getBytes("utf-8"));
            //Al igual que en el proceso de encriptamiento se obtienen los bytes del password
            byte[] bytePass = secretKey.getBytes("utf-8");
            //Los bytes del password se guardan en un arreglo de 24 bits
            byte[] byteKey = Arrays.copyOf(bytePass, 24);
            
            //Convierte el arreglo de bytes a una clave correspondiente de el TripleDES con 168 bits
            SecretKey key = new SecretKeySpec(byteKey, "DESede");
            //Se instancia el objeto Cipher en formato DESede
            Cipher cipher = Cipher.getInstance("DESede");
            //Se inicializa el objeto en modo decrypt y se le envia la llave correspondiente
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            //Se desencripta y se guarda el resultado en un arreglo de bytes
            byte[] byteText = cipher.doFinal(byteData);
            //Se convierte el arreglo de bytes a un string para mostrar
            String clearText = new String(byteText, "utf-8");
            
            return clearText;
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
    }
    
    public static void main(String[] args) {
        String clearText = "Este es el algoritmo 3DES";
        String secretKey = "SecretKey";
        
        String data = new TripleDESTest().encrypt(clearText, secretKey);
        String clearTextDecripted = new TripleDESTest().decrypt(data, secretKey);
        
        System.out.println("Encrypted String: " + data);
        System.out.println("Decrypted Text: "+clearTextDecripted);
        System.out.println("Original text: " +clearText);
    }
}