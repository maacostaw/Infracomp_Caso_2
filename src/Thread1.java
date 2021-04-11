import java.util.Iterator;

public class Thread1 extends Thread {

	private int actual=0;
	private int[] marcos;
	private int[] referencias;
	private boolean cupo=true;
	public Thread1(int[] referencias, int marcos)
	{
		this.marcos= new int[marcos];
		this.referencias=referencias;
	}

	public void run(){
		try{
			inicializararreglomarco();
			Thread.sleep(5);
			while(referencias.length-1>actual){
				int referenciactual = referencias[actual];
				if (cupo == true)
				{
					for (int i = 0; i < marcos.length; i++) {
						synchronized(MMU.getTablaPaginas()){
							if(marcos[i]==-1)
							{
								marcos[i]=referenciactual;
								fallodepaginainic(referenciactual);
								actual++;
								if(i==marcos.length-1)
								{
									cupo = false;
								}
								break;
							}
						}
					}
				}
				else
				{
					//Esta en RAM? 
					boolean estaenRam=false;
					for (int i = 0; i < marcos.length && !estaenRam; i++) {
						synchronized(MMU.getTablaPaginas()){
							if(marcos[i]==referenciactual) 
							{
								//esta entonces vamos a pasar derecho
								estaenRam=true;
								actual++;
								fallodepaginainic(referenciactual);
							}


							if(estaenRam == false)
							{
								//fallo de pagina 
								fallodepagina(referenciactual);
								actual++;
							}
						}
					}
				}
			}
			MMU.cambiartermino();
			System.out.println("se detectaron "+MMU.fallosPag+" fallos de pagina");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println();
		}
	}

	public void fallodepaginainic(int valor)
	{
		char lleno_referenciado = (char)255;
		MMU.aumentarfallo();
		//cambio de estado
		MMU.modificarTablaPaginas(valor, 0, lleno_referenciado);
	}

	public void fallodepagina(int valor)
	{
		char minimo = MMU.getTablaPaginas()[marcos[0]][1];
		int pos = 0;
		int pag= marcos[0];
		for (int i = 1; i < marcos.length; i++) {
			if(MMU.getTablaPaginas()[marcos[i]][1]<minimo)
			{
				minimo = MMU.getTablaPaginas()[marcos[i]][1];
				pos = i;
				pag = marcos[i];
			}
		}
		marcos[pos]= pag;
		MMU.aumentarfallo();
		char vacio = (char)0;
		char lleno_referenciado = (char)255;
		//cambiamos el valor de la que acabamos de agregar
		MMU.modificarTablaPaginas(valor, 0, lleno_referenciado);
		//cambiamos el valor de la que vamos a retirar
		MMU.modificarTablaPaginas(pag, 0, vacio);		
	}

	public void inicializararreglomarco()
	{
		for (int i = 0; i < marcos.length; i++) {
			marcos[i]=-1;
		}
	}
}