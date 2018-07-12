package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendario {

	public String dataCompleta;
	public String dia;
	public String mes;
	public String ano;

	public String getData() {
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat formataDate = new SimpleDateFormat("dd-MM-yyyy");
		dataCompleta = formataDate.format(data);
		return dataCompleta;
	}

	public String getDiaAtual() {
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat formataDate = new SimpleDateFormat("dd");
		dia = formataDate.format(data);
		return dia;
	}

	public String getMesAtual() {
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat formataDate = new SimpleDateFormat("MM");
		mes = formataDate.format(data);
		return mes;
	}

	public String getAnoAtual() {
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat formataDate = new SimpleDateFormat("yyyy");
		ano = formataDate.format(data);
		return ano;
	}
}
