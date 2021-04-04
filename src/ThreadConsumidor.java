public class ThreadConsumidor extends Thread {

	private String tipo;
	private int actual = 0;
	
	public ThreadConsumidor(String pTipo){
		tipo= pTipo;
	}

	public void run(){
		try {
			while(actual < Mercado.numProductos){
				synchronized (Mercado.getColaEsperaConsumidor()) {
					if(Mercado.getColaEsperaConsumidor().size()>0){
						Producto x= null;
						for (int i = 0; i < Mercado.getColaEsperaConsumidor().size(); i++) {
							if(Mercado.getColaEsperaConsumidor().get(i).getTipo().equals(tipo)){
								x = Mercado.getColaEsperaConsumidor().remove(i);
								actual++;
								synchronized (System.out){
									System.out.println(String.format("Consumidor: \t\tProducto Consumido Producto(%d)", x.hashCode()));
									System.out.println("Cola consumidores: \tHay "+Mercado.getColaEsperaConsumidor().size()+" y el max es: "+ Mercado.tamanoBuzonConsumidor);
								}
								break;
							}
							if (x==null){
								Thread.yield();
							}
						}
					}
					else
					{
						Thread.yield();
					}
				}
			}
			System.out.println("Un consumidor paró");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}