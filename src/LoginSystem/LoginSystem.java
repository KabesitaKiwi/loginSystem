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

	// Método para crear un objeto de tipo Usuario con la contraseña hasheada
	public Usuario registrarUsuario(String usuario, String contrasenya) {
			String user = usuario;
			if (!comprobarUsuario(user)) {
				String pass = contrasenya;

				String passHash = calcularHash(pass);
				return new Usuario(user, passHash);
			} else {
				System.out.println("El usuario ya existe, por favor intentelo con otro.");
			}
		
		
		return null;

	}

	// Método para guardar el objeto Usuario en el archivo .txt
	public void guardarUsuarioTxt(Usuario usuarioNuevo) {
		try (FileWriter fw = new FileWriter("usuarios.txt", true)) {
			fw.write(usuarioNuevo.getUsuario() + ";" + usuarioNuevo.getContrasenya() + "\n");
			System.out.println("Usuario guardado correctamente en usuarios.txt");
		} catch (IOException e) {
			System.out.println("Error al guardar el usuario en el archivo usuarios.txt");
			e.printStackTrace();
		}
	}

	// Método para verificar inicio de sesión
	public boolean verificarUsuario(String usuario, String pass) {
		String nombre = usuario ;

		String contrasenya = pass;

		String hashIntroducido = calcularHash(contrasenya);

		try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
			String linea;

			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(";");
				String nombreArchivo = partes[0];
				String hashArchivo = partes[1];

				if (nombreArchivo.equals(nombre)) {
					return hashArchivo.equals(hashIntroducido);
				}
			}

			return false; // Usuario no encontrado

		} catch (FileNotFoundException e) {
			System.out.println("El archivo usuarios.txt no existe. Registra un usuario primero.");
			return false;
		} catch (IOException e) {
			System.out.println("Error al leer el archivo usuarios.txt");
			e.printStackTrace();
			return false;
		}
	}

	public boolean comprobarUsuario(String usuario) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"));
			String linea;
			try {
				while ((linea = br.readLine()) != null) {
					String[] partes = linea.split(";");
					String nombreUsuario = partes[0];
					if (usuario.equals(nombreUsuario)) {
						return true;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// Método para hashear la contraseña con SHA-256
	public String calcularHash(String contrasenya) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(contrasenya.getBytes());
			return toHexadecimal(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error: Algoritmo SHA-256 no disponible.");
			e.printStackTrace();
			return null;
		}
	}

	// Método auxiliar para convertir bytes a hexadecimal
	private static String toHexadecimal(byte[] hash) {
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
