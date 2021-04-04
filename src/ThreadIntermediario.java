

public class ThreadIntermediario extends Thread {

	private int totalPasar;
	private int actual=0;
	
	public ThreadIntermediario(int totalPasar){
		this.totalPasar= totalPasar;
	}

	public void run(){
		try{
			while(totalPasar>actual){
				synchronized (Mercado.getColaEsperaIntermedia()) {
					if(Mercado.getColaEsperaProductor().size()>0 && Mercado.getColaEsperaIntermedia().getLista().size()<1 ) {
						Producto x = Mercado.getColaEsperaProductor().poll();
						synchronized (System.out){
							System.out.println("Intermediario 1: \tRecibe el producto " + x.hashCode());
						}
						Mercado.getColaEsperaIntermedia().meter(x);
						actual++;
						synchronized (System.out){
							String mensaje = String.format("Intermediario 1: \tPasa el producto %d, faltan por pasar %d", 
									Mercado.getColaEsperaIntermedia().getLista().get(0).hashCode(),
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