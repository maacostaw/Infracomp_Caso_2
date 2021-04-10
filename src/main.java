import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
	
	public static int numMarcos;
	public static int numPaginas;
	public static float nivelLocalidad;
	
	public static int[] referencias = new int[1000];

	public static void main(String[] args) {
		try {
			File archivo = new File ("data/referencias1.txt");
			Scanner scanner = new Scanner(archivo);

			String sNumMarcos = scanner.nextLine();
			numMarcos = Integer.parseInt(sNumMarcos);

			String sNumPaginas = scanner.nextLine();
			numPaginas = Integer.parseInt(sNumPaginas);

			String sNivelLocalidad = scanner.nextLine();
			nivelLocalidad = Float.parseFloat(sNivelLocalidad);

			String datos = String.format("Los datos son: %d marcos, %d páginas y %f nivel de localidad",
					numMarcos, numPaginas, nivelLocalidad);

			System.out.println(datos);
			
			int i=0;
			while(scanner.hasNextLine()) {
				String referencia = scanner.nextLine();
				referencias[i] = Integer.parseInt(referencia);
				i++;
			}

			scanner.close();

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
			}*/
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}

}
