import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files

public class MMU {
	
	public static int marcos;
	public static int paginas;
	public static float localidad;
	public static int[] referencias = new int[16];
	
	private static char[][] tabla_paginas;
	private static boolean termino =false;
	
	public static char[][] getTablaPaginas() {
		return tabla_paginas;
	}
	public static void cambiartermino() {
		termino = true;
	}
	public static boolean getTermino() {
		return termino;
	}
	public static int fallosPag = 0;
	public static int aumentarfallo() {
		fallosPag++;
		return fallosPag;
	}
	public synchronized static void modificarTablaPaginas(int fila, int columna, char valor)
	{
		tabla_paginas[fila][columna]= valor;
	}
	public static void main(String[] args){
		try {
			File archivo = new File ("data/referencias4_16.txt");
			Scanner scanner = new Scanner(archivo);

			String sMarcos = scanner.nextLine();
			marcos = Integer.parseInt(sMarcos);

			String sPaginas = scanner.nextLine();
			paginas = Integer.parseInt(sPaginas);
			tabla_paginas = new char[paginas][2];

			String sNivelLocalidad = scanner.nextLine();
			localidad = Float.parseFloat(sNivelLocalidad);

			int i = 0;
			while (i < 16) {
				String sReferencia = scanner.nextLine();
				referencias[i] = Integer.parseInt(sReferencia);
				i+=1;
			}
			
			String datos = String.format("Los datos son: %d páginas, %d marcos, %.2f porcentaje de localidad",
					paginas, marcos, localidad);

			System.out.println(datos);

			scanner.close();
			
			termino = false;
			
			Thread1 primero = new Thread1(referencias, marcos);
			primero.start();
			Thread2 segundo= new Thread2();
			segundo.start();
			
			/**
			try{
				//Aquí se crean y empiezan los productores y consumidores (numProductos) es los productos c/u
				for (int i = 0; i < numProdCons; i++) {
					String tipoActual = (i%2==0) ? Producto.TIPO_B : Producto.TIPO_A;
					ThreadProductor nuevoProductor = new ThreadProductor(tipoActual);
					nuevoProductor.start();
					ThreadConsumidor nuevoConsumidor= new ThreadConsumidor(tipoActual);
					nuevoConsumidor.start();
				}
				ThreadIntermediario primero = new ThreadIntermediario( numProductos*numProdCons);
				primero.start();
				ThreadIntermediario2 segundo= new ThreadIntermediario2( numProductos*numProdCons);
				segundo.start();

			}
			catch(Exception e){
				e.printStackTrace();
			}
			*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}

}
