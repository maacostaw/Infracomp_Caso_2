

public class Thread1 extends Thread {

	private int totalPasar;
	private int actual=0;
	
	public Thread1(int totalPasar){
		this.totalPasar= totalPasar;
	}

	public void run(){
		try{
			while(totalPasar>actual){
				synchronized (MMU.getColaEsperaIntermedia()) {
					if(MMU.getColaEsperaProductor().size()>0 && MMU.getColaEsperaIntermedia().getLista().size()<1 ) {
						Producto x = MMU.getColaEsperaProductor().poll();
						synchronized (System.out){
							System.out.println("Intermediario 1: \tRecibe el producto " + x.hashCode());
						}
						MMU.getColaEsperaIntermedia().meter(x);
						actual++;
						synchronized (System.out){
							String mensaje = String.format("Intermediario 1: \tPasa el producto %d, faltan por pasar %d", 
									MMU.getColaEsperaIntermedia().getLista().get(0).hashCode(),
									totalPasar - actual);
							System.out.println(mensaje);
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}