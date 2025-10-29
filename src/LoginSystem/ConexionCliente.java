package LoginSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ConexionCliente{
	public static void main(String[] args) {
		Socket conexionCliente = null;
		BufferedReader entrada = null;
		PrintWriter salida = null;
		
		try {
			conexionCliente = new Socket(InetAddress.getLocalHost(), 4242);
			entrada = new BufferedReader(new InputStreamReader(conexionCliente.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conexionCliente.getOutputStream())));		
			
			boolean enviando = true; 
			
			Scanner sc = new Scanner(System.in);
			
			boolean entradaValida = false;
			int opcion = 0;
			
			while(!entradaValida) {
				try {
				System.out.println("Elige una opcion: ");
				System.out.println("0: Registrar un nuevo usuario\n1: Iniciar sesión");
				opcion = Integer.parseInt(sc.nextLine());
				entradaValida = true;
				} catch (NumberFormatException e) {
					System.out.println("Debes introducir un numero, 1 o 2");
				}
			
			}	
			
			// Condicional si el usuario se quiere registrar
			if (opcion == 0) {
				System.out.println("Has elegido registrar");
				salida.println("Registro: " + user + ":" + pass );
				salida.flush();

				// Condicional si el usuario quiere iniciar sesión
			} else if (opcion == 1) {
				System.out.println("Has elegido iniciar sesión");
				if (verificarUsuario(sc)) {
					System.out.println("Inicio de sesión exitoso.");
				} else {
					System.out.println("No se ha podido iniciar sesión.");
				}

			} else {
				System.out.println("Esa opción no es válida.");
			}

			sc.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("No se ha podido conectar");
			System.exit(-1);
		}finally {
			salida.close();
			try {
				entrada.close();
				conexionCliente.close();
			} catch (IOException e2) {
				// TODO: handle exception
				e2.printStackTrace();
				System.exit(-1);
			}
		}
		
	}
	
	
}
