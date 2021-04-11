
//Thread encargado de ejecutar el algoritmo de envejecimiento
public class Thread2 extends Thread {
	//0011111111111111111111111111111
	private int separador = 536870911;
	//0010000000000000000000000000000
	private int nuevo_ref = 268435456;
	
	public void run() {
		try{
			while(!MMU.getTermino()) {
				synchronized(MMU.getTablaPaginas()){
					for(int pag = 0; pag < MMU.paginas; pag++) {
						int pagina = MMU.getTablaPaginas()[pag];
						//Separo estado y registro (uso lleno_ref pq es 1100...)
						int estado = MMU.lleno_referenciado & pagina;
						int registro = separador & pagina;
						//Desplazo el registro
						registro = registro>>1;
						//Junto denuevo
						MMU.getTablaPaginas()[pag] = estado | registro;
						//Si el estado es lleno referenciado lo añado al registro
						if(estado == MMU.lleno_referenciado) {
							//Le añado el 1
							registro = nuevo_ref | registro;
							//Junto de nuevo pero convirtiendo en no referenciado
							MMU.getTablaPaginas()[pag] = MMU.lleno_no_referenciado | registro;
						}
					}
				}
				Thread.sleep(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
