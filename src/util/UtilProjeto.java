package util;

public class UtilProjeto {

	public String getEspacos(Integer numeroDeEspacos) {
		StringBuffer acuEspacos = new StringBuffer();
		for (Integer ind = 0; ind < numeroDeEspacos; ind++) {
			acuEspacos.append(" ");
		}
		return String.valueOf(acuEspacos);
	}

	public void mudarAparencia() {
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
