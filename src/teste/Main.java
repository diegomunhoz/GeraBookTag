package teste;

import model.GeraBook;
import util.UtilProjeto;

public class Main {

	public static void main(String[] args) {

		UtilProjeto util = new UtilProjeto();
		util.mudarAparencia();

		GeraBook working = new GeraBook();
		working.processar();
		
		
/**		
		ConverteBinario conv = new ConverteBinario();
		conv.processar();
	**/	
		
	}
}