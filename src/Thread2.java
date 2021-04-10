
//Thread encargado de ejecutar el algoritmo de envejecimiento
public class Thread2 extends Thread {
	
	public void run() {
		try{
			//while(!MMU.getTermino()) {
				synchronized(MMU.getTablaPaginas()){
					int i = 0;
					while(i<MMU.paginas) {
						char[] pagina = MMU.getTablaPaginas()[i];
						char lleno_referenciado = (char)255;
						char lleno_no_referenciado = (char)254;
						char vacio = (char)0;
						if(pagina[0] == vacio || pagina[0] == lleno_no_referenciado) {
							pagina[0] = (char)(pagina[0]>>1);
							pagina[0] = (char)(pagina[0] | (char) 128);
							
							System.out.println(i+ "," + (pagina[0]>>1));
						}
						MMU.getTablaPaginas()[i][1] = (char)100;;
						i+=1;
						
					}
				}
			//}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
