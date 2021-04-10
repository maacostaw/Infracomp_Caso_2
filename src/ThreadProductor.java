public class ThreadProductor extends Thread {

	private String tipo;
	private int actual = 0;

	public ThreadProductor(String pTipo){
		tipo= pTipo;
	}

	public void run(){
		try{
			while(actual < MMU.numProductos) {
				synchronized(MMU.getColaEsperaProductor()){
					if(MMU.getColaEsperaProductor().size() < MMU.tamanoBuzonProductor){
						Producto nuevo = new Producto(tipo);
						MMU.getColaEsperaProductor().add(nuevo);
						System.out.println("Cola productores: \tHay "+MMU.getColaEsperaProductor().size()+" y el max es: "+ MMU.tamanoBuzonProductor);
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
