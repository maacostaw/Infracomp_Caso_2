public class ThreadProductor extends Thread {

	private String tipo;
	private int actual = 0;

	public ThreadProductor(String pTipo){
		tipo= pTipo;
	}

	public void run(){
		try{
			while(actual < Mercado.numProductos) {
				synchronized(Mercado.getColaEsperaProductor()){
					if(Mercado.getColaEsperaProductor().size() < Mercado.tamanoBuzonProductor){
						Producto nuevo = new Producto(tipo);
						Mercado.getColaEsperaProductor().add(nuevo);
						System.out.println("Cola productores: \tHay "+Mercado.getColaEsperaProductor().size()+" y el max es: "+ Mercado.tamanoBuzonProductor);
						actual++;
						synchronized (System.out){
							System.out.println(String.format("Productor: \t\tNuevo Producto Producto(%d)", nuevo.hashCode()));
						}
					}
					else{
						Thread.yield();
					}
				}
				
			}
			System.out.println("Un productor paró");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
