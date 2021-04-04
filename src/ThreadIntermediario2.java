

public class ThreadIntermediario2 extends Thread {

	private int totalPasar;
	private int actual=0;
	
	public ThreadIntermediario2(int totalPasar){
		this.totalPasar= totalPasar;
	}
	
	public void run() {
		try{
			while(actual<totalPasar){
				synchronized (Mercado.getColaEsperaIntermedia()){
					if(Mercado.getColaEsperaIntermedia().getLista().size()>0){
						if(Mercado.getColaEsperaConsumidor().size()< Mercado.tamanoBuzonConsumidor) {
							Producto x = Mercado.getColaEsperaIntermedia().sacar();
							synchronized (System.out){
								System.out.println("Intermediario 2: \tRecibe el producto " + x.hashCode());
							}
							Mercado.getColaEsperaConsumidor().add(x);
							actual++;
							synchronized (System.out){
								String mensaje = String.format("Intermediario 2: \tPasa el producto %d, faltan por pasar %d", 
										x.hashCode(),
										totalPasar - actual);
								System.out.println(mensaje);
								System.out.println("Cola consumidores: \tHay "+Mercado.getColaEsperaConsumidor().size()+" y el max es: "+ Mercado.tamanoBuzonConsumidor);
							}						
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
