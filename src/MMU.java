import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files

public class MMU {
	public static int marcos;
	public static int paginas;
	public static float localidad;
	public static int[] referencias = new int[1000];
	
	//1100000000000000000000000000000
	public static final int lleno_referenciado = 1610612736;
	//1000000000000000000000000000000
	public static final int lleno_no_referenciado = 1073741824;
	//0000000000000000000000000000000
	public static final int vacio = 0;
	
	private static int[] tabla_paginas;
	private static boolean termino;
	public static int fallosPag;
	
	public static int[] getTablaPaginas() {
		return tabla_paginas;
	}
	public static void cambiartermino() {
		termino = true;
	}
	public static boolean getTermino() {
		return termino;
	}
	

	public synchronized static void modificarTablaPaginas(int fila, int valor){
		tabla_paginas[fila] = tabla_paginas[fila] & valor;
	}
	
	public static void main(String[] args){
		try {
			File archivo = new File ("data/" + args[0]);
			Scanner scanner = new Scanner(archivo);

			String sMarcos = scanner.nextLine();
			marcos = Integer.parseInt(sMarcos);

			String sPaginas = scanner.nextLine();
			paginas = Integer.parseInt(sPaginas);
			tabla_paginas = new int[paginas];

			String sNivelLocalidad = scanner.nextLine();
			localidad = Float.parseFloat(sNivelLocalidad);

			int i = 0;
			while (i < 1000) {
				String sReferencia = scanner.nextLine();
				referencias[i] = Integer.parseInt(sReferencia);
				i+=1;
			}
			
			String datos = String.format("Los datos son: %d páginas, %d marcos, %.2f porcentaje de localidad",
					paginas, marcos, localidad);

			System.out.println(datos);

			scanner.close();
			
			termino = false;
			fallosPag = 0;
			
			Thread1 primero = new Thread1(marcos);
			primero.start();
			Thread2 segundo= new Thread2();
			segundo.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
