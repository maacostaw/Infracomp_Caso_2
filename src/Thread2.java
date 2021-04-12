
//Thread encargado de ejecutar el algoritmo de envejecimiento
public class Thread2 extends Thread {
	//0111111111111111111111111111111
	private int separador = 1073741823;
	
	public void run() {
		try{
			while(!MMU.getTermino()) {
				synchronized(MMU.getTablaPaginas()){
					for(int pag = 0; pag < MMU.paginas; pag++) {
						//Separo registro
						int registro = separador & MMU.getTablaPaginas()[pag];
						//Desplazo el registro
						registro = registro>>1;
						//Junto denuevo
						MMU.getTablaPaginas()[pag] = MMU.lleno_no_referenciado | registro;
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
