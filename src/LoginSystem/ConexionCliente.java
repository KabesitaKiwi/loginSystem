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
	public void iniciarCliente() {
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
				boolean correcto = false;
				while(!correcto) {
				System.out.println("Dime el nombre de usuario: ");
				String user = sc.nextLine();
				System.out.println("Dime la contraseña  del usuario: ");
				String pass = sc.nextLine();
				if(!user.isEmpty()&&!pass.isEmpty() && !user.contains(";") && !pass.contains(";") ) {
					salida.println(user + ";" + pass );
					salida.flush();
					correcto=true;
				}else {
				    System.out.println("El usuario y la contraseña no pueden estar vacíos ni contener ':'");
				}

				}
				
				// Condicional si el usuario quiere iniciar sesión
			} else if (opcion == 1) {
				System.out.println("Has elegido iniciar sesión");
				System.out.println("Nombre de usuario");
				String user = sc.nextLine();
				System.out.println("Contraseña");
				String pass = sc.nextLine();
				salida.println(user + ";" + pass );
				salida.flush();
			} else {
				System.out.println("Esa opción no es válida.");
			}
			
			String respuesta = entrada.readLine();
            if (respuesta == null) {
                System.out.println("El servidor ha cerrado la conexión.");
                return;
            }
            System.out.println(respuesta);
			
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
