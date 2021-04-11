
//Thread encargado de ejecutar el algoritmo de envejecimiento
public class Thread2 extends Thread {
	
	public void run() {
		try{
			while(!MMU.getTermino()) {
				Thread.sleep(5);
				synchronized(MMU.getTablaPaginas()){
					int i = 0;
					while(i<MMU.paginas) {
						char[] pagina = MMU.getTablaPaginas()[i];
						char lleno_referenciado = (char)255;
						char lleno_no_referenciado = (char)254;
						char vacio = (char)0;
						if(pagina[0] == vacio || pagina[0] == lleno_no_referenciado) {
							pagina[1] = (char)(pagina[1]>>1);
							synchronized (System.out) {
								System.out.println((char)pagina[1]);
							}

						} 
						if(pagina[0] == lleno_referenciado) {
							pagina[1] = (char)(pagina[1]>>1);
							pagina[1] = (char)(pagina[1] | (char) 128);
							pagina[0] = lleno_no_referenciado;
						}
						i+=1;
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
