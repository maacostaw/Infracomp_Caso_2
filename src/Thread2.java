
//Thread encargado de ejecutar el algoritmo de envejecimiento
public class Thread2 extends Thread {
	
	public void run() {
		try{
			while(!MMU.getTermino()) {
				synchronized(MMU.getTablaPaginas()){
					for(int pag = 0; pag < MMU.paginas; pag++) {
						char[] pagina = MMU.getTablaPaginas()[pag];
						//Realizo el corrimiento
						pagina[1] = (char)(pagina[1]>>1);
						//Si se referencio la pagina lo añado al registro
						if(pagina[0] == MMU.lleno_referenciado) {
							pagina[1] = (char)(pagina[1] | (char) 128);
							MMU.modificarTablaPaginas(pag, MMU.lleno_no_referenciado);
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
