
//Thread encargado de actualizar el estado de la tabla de páginas
public class Thread1 extends Thread {
	//0011111111111111111111111111111
	private int separador = 536870911;

	private int[] marcos;
	private int marcos_llenos = 0;
	
	public Thread1(int marcos){
		this.marcos= new int[marcos];
	}
	
	public void run(){
		try{
			inicializar_marcos();
			for(int actual = 0; actual < MMU.referencias.length; actual ++) {
				synchronized(MMU.getTablaPaginas()) {
					int referenciactual = MMU.referencias[actual];
					//Esta en RAM? 
					boolean estaenRam = false;
					for (int i = 0; i < marcos_llenos && !estaenRam; i++) {
						if(marcos[i]==referenciactual) {
							estaenRam=true;
							MMU.getTablaPaginas()[referenciactual] = MMU.getTablaPaginas()[referenciactual] | MMU.lleno_referenciado;
							//System.out.println("Referencia en memoria: " + referenciactual);
						}
					}
					if(estaenRam == false){
						//fallo de pagina 
						fallodepagina(referenciactual);
					}
				}
				Thread.sleep(5);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		MMU.cambiartermino();
		System.out.println("se detectaron:"+ MMU.fallosPag +" fallos de pagina");
	}
	
	public void fallodepagina(int referencia) {
		if(marcos_llenos < marcos.length) {
			marcos[marcos_llenos]=referencia;
			marcos_llenos++;
			//System.out.println("Fallo inicial, referencia: " + referencia);
		}else {
			int pagina_min = marcos[0];
			int marco_min = 0;
			int minimo = MMU.getTablaPaginas()[pagina_min] & separador;
			int apariciones = contar(minimo);
			
			for (int i = 1; i < marcos.length; i++) {
				int apariciones_actual = contar(MMU.getTablaPaginas()[marcos[i]]&separador);
				if(apariciones_actual<apariciones){			
					minimo = MMU.getTablaPaginas()[marcos[i]];
					marco_min = i;
					pagina_min = marcos[i];
				}
			}
			marcos[marco_min]= referencia;
			//Valor que vamos a retirar
			MMU.getTablaPaginas()[referencia] = MMU.getTablaPaginas()[referencia] & separador;
			//System.out.println("Fallo común, referencia: " + referencia);
		}
		//Valor que acabamos de agregar
		MMU.getTablaPaginas()[referencia] = MMU.getTablaPaginas()[referencia] | MMU.lleno_referenciado;
		MMU.fallosPag++;
	}

	public void inicializar_marcos(){
		for (int i = 0; i < marcos.length; i++) {
			marcos[i]=-1;
		}
	}
	
	public int contar(int numero) {
		int conteo = 0;
		for(int i = 0; i < 29; i++) {
			//System.out.println(numero + " "+  (int)Math.pow(2, i));
			if((numero & (int)Math.pow(2, i)) == Math.pow(2, i)) {
				conteo++;
			}
		}
		//System.out.println(conteo);
		return conteo;	
	}
}