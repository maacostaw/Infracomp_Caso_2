import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Mercado {

	private static ArrayList<Producto> colaEsperaConsumidor = new ArrayList<Producto>();
	private static LinkedList <Producto> colaEsperaProductor= new LinkedList<Producto>();
	private static Buzon colaEsperaIntermedia= new Buzon(1);

	public static int tamanoBuzonProductor;
	public static int tamanoBuzonConsumidor;
	public static int numProdCons;
	public static int numProductos;

	public static ArrayList<Producto> getColaEsperaConsumidor(){
		return colaEsperaConsumidor;
	}
	
	public static LinkedList<Producto> getColaEsperaProductor() {
		return colaEsperaProductor;
	}

	public static Buzon getColaEsperaIntermedia() {
		return colaEsperaIntermedia;
	}
	
	public static void main(String[] args){
		try {
			File archivo = new File ("data/concurrencia.txt");
			Scanner scanner = new Scanner(archivo);

			String sNumProdCons = scanner.nextLine();
			numProdCons = Integer.parseInt(sNumProdCons.split("=")[1].strip());

			String sNumProductos = scanner.nextLine();
			numProductos = Integer.parseInt(sNumProductos.split("=")[1].strip());

			String sBuzonesProd = scanner.nextLine();
			tamanoBuzonProductor = Integer.parseInt(sBuzonesProd.split("=")[1].strip());

			String sBuzonesCons = scanner.nextLine();
			tamanoBuzonConsumidor = Integer.parseInt(sBuzonesCons.split("=")[1].strip());

			String datos = String.format("Los datos son: %d productores/consumidores, %d productos por prod/cons, %d tamaño buzón productor, tamaño buzón consumidor %d",
					numProdCons, numProductos, tamanoBuzonProductor, tamanoBuzonConsumidor);

			System.out.println(datos);

			scanner.close();

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
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}


	}

}
