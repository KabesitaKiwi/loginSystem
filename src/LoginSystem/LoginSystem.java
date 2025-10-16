package LoginSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class LoginSystem {

	public void menu () {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Elige una opcion!");
		System.out.println("0: Registrar un nuevo usuario\n"
				+ "1: Iniciar sesion");
		int opcion = Integer.parseInt(teclado.nextLine());
		
		if(opcion == 0) {
			System.out.println("Has elegido registrar");
			Usuario nuevoUsuario = registrarUsuario(teclado);
			System.out.println(nuevoUsuario.toString());
			guardarUsuarioTxt(nuevoUsuario);
		} else if (opcion == 1) {
			System.out.println("Has elegido iniciar sesión");
		} else {
			System.out.println("Esa opcion no es valida");
		}
		
		teclado.close();
	}
	
	public Usuario registrarUsuario (Scanner teclado) {
		System.out.println("Escriba un nombre de usuario: ");
		String user = teclado.nextLine();
		System.out.println("Escriba una contraseña: ");
		String pass = teclado.nextLine();
		String passHash = calcularHash(pass);
		
		return new Usuario(user, passHash);
	}
	
	public void guardarUsuarioTxt(Usuario usuarioNuevo) {
		try {
			FileWriter fw = new FileWriter("usuarios.txt", true);
			fw.write(usuarioNuevo.getUsuario() + ";" + usuarioNuevo.getContrasenya() + "\n");
			fw.close();
			System.out.println("Usuario guardado correctamente en usuarios.txt");
		} catch (IOException e) {
			System.out.println("Error al guardar el usuario en el archivo usuarios.txt");
			e.printStackTrace();
		}
	}
	
	public String calcularHash(String contrasenya) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(contrasenya.getBytes());
			String hashHex = toHexadecimal(hashBytes);
			return hashHex;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static String toHexadecimal(byte[] hash) {
		String hex = "";
		for (int i = 0; i < hash.length; i++) {
			String h = Integer.toHexString(hash[i] & 0xFF);
			if (h.length() == 1) {
				hex += "0";
			}
			hex += h;
		}
		return hex.toUpperCase();
	}
	
	
	
}
