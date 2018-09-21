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
            byte[] byteKey = Arrays.copyOf(bytePass, 24);
            
            SecretKey key = new SecretKeySpec(byteKey, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] byteText = clearText.getBytes("utf-8");
            byte[] buf = cipher.doFinal(byteText);
            
            byte[] byteBase64 = Base64.getEncoder().encode(buf);
            String data = new String(byteBase64);
            
            return data;
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
    }
    
    private String decrypt(String data, String secretKey) {
        try {
            byte[] byteData = Base64.getDecoder().decode(data.getBytes("utf-8"));
            byte[] bytePass = secretKey.getBytes("utf-8");
            byte[] byteKey = Arrays.copyOf(bytePass, 24);
            
            SecretKey key = new SecretKeySpec(byteKey, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            byte[] byteText = cipher.doFinal(byteData);
            String clearText = new String(byteText, "utf-8");
            
            return clearText;
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
    }
    
    public static void main(String[] args) {
        String clearText = "Hola soy Sara";
        String secretKey = "SecretKey";
        
        String data = new TripleDESTest().encrypt(clearText, secretKey);
        clearText = new TripleDESTest().decrypt(data, secretKey);
        
        System.out.println("Encrypted String: " + data);
        System.out.println(clearText);
    }
}