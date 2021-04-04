import java.util.LinkedList;

public class Buzon {

	private LinkedList<Producto> lista = new LinkedList<Producto>();
	private int max; 
	private Object entrada = new Object();
	private Object salida= new Object();
	
	public Buzon(int pMax){
		this.max = pMax;
	}
	
	public Object getEntrada() {
		return entrada;
	}

	public Object getSalida() {
		return salida;
	}	

	public void meter(Producto x){
		synchronized (entrada) {
			while(lista.size()== max){
				try {
					entrada.wait();
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		lista.add(x);
		synchronized (System.out){
			System.out.println(String.format("Buzón: \t\t\tRecibe un producto Producto(%d)", x.hashCode()));
		}
		synchronized (salida) {
			salida.notify();
		}
	}

	public Producto sacar(){
		synchronized (salida) {
			while(lista.size()==0){
				try {
					salida.wait();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Producto x =lista.poll(); 
		synchronized (System.out){
			System.out.println(String.format("Buzon: \t\t\tSaca un producto Producto(%d)", x.hashCode()));
		}
		synchronized (entrada) {
			entrada.notify();
		}
		return x;
	}

	public LinkedList<Producto> getLista() {
		return lista;
	}

	public int getmax(){
		return max;
	}
}


