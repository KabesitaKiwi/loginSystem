package LoginSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConexionServidor {
	public static final int PORT = 4242;

	public static void main(String[] args)throws IOException {
		ServerSocket servidor = null;
		try {
			servidor = new ServerSocket(PORT, 1000, InetAddress.getLocalHost());
		} catch (IOException e) {
			// TODO: handle exception
			System.err.println("No podemos escuchar en el puerto: "+PORT);
			System.exit(-1);
		}
		
		Socket conexion = null;
		BufferedReader entrada = null; 
		PrintWriter salida = null;
		
		System.out.println("escuchando: "+ servidor);
		try {
			while(true) {
			conexion = servidor.accept();

			System.out.println("Conexion aceptada: " +  conexion);
			entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(conexion.getOutputStream())));
			
			
			System.out.println("Estas conectado como servidor");
			
			boolean enviando = true; 
			
			while(enviando) {
				
				String respuestaCliente = entrada.readLine();
				System.out.println(respuestaCliente);
				
				String lineaRespuesta = respuestaCliente;
				
				String [] parteRespuesta = lineaRespuesta.split(";");
				String nombreUsuarioCliente = parteRespuesta[0];
				String contraseñaCliente = parteRespuesta[1];
				
				LoginSystem ls = new LoginSystem();
				
				Usuario u = ls.registrarUsuario(nombreUsuarioCliente, contraseñaCliente);
				if (u != null) {
                    ls.guardarUsuarioTxt(u);
                    salida.println("Usuario registrado.");
                } else {
                    salida.println("ERROR: Usuario ya existe.");
                }
                salida.flush();
                
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("IOException: " + e.getMessage());
		}finally {}
		
	}

}
