package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import util.Calendario;
import util.UtilProjeto;

public class GeraBook {

	private UtilProjeto objUtil = new UtilProjeto();
	private Calendario objCalendario = new Calendario();
	private Scanner leitorEnvio;
	private Scanner leitorRetorno;
	private PrintStream gravador;
	private List<String> varWrkEnvio = new ArrayList<String>();
	private List<String> varWrkRetorno = new ArrayList<String>();
	private List<Integer> varForTamEnvio = new ArrayList<Integer>();
	private String campoDaTabela;
	private String novoCampoDaTabela;
	private String campoFinalizado;
	private String listCampoFinalizado;
	private String impCampoFinalizado;
	private String prefixo;
	private String linha1;
	private String linha2;
	private String tag;
	private String spaces;
	private int tamanhoLinha1;
	private int tamanhoLinha2;
	private int tamanhoCampo;
	private int tamanhoCampoFinal;
	private int b;
	private int index;
	private int campo1;
	private int campo2;
	private int indDuplicadoEnvio;
	private int indDuplicadoRetorno;
	private boolean envio = false;
	private boolean retorno = false;
	private String dataAtual;

	public void processar() {
		try {
			this.leitorEnvio = new Scanner(new FileReader("envio.txt"));
			this.leitorRetorno = new Scanner(new FileReader("retorno.txt"));

			while (this.leitorEnvio.hasNext()) {
				this.montarListaVariaveisEnvio();
			}

			while (this.leitorRetorno.hasNext()) {
				this.montarListaVariaveisRetorno();
			}

			if (this.varWrkEnvio.size() > 0 || this.varWrkRetorno.size() > 0) {
				this.gravador = new PrintStream("saida.txt");
				this.prefixo = JOptionPane.showInputDialog(null,
						"Prefixo do BOOK:","GERADOR DE BOOK:", 3);
				this.montaWorkingFixo(this.prefixo.toUpperCase());
			}

			if (this.varWrkEnvio.size() > 0) {
				this.envio = true;
				this.montaWorkingEnvio(this.prefixo.toUpperCase());
			} else {
				this.envio = false;
			}

			if (this.varWrkRetorno.size() > 0) {
				this.retorno = true;
				this.montaWorkingRetorno(this.prefixo.toUpperCase());
			} else {
				this.retorno = false;
			}

			if (this.envio == false && this.retorno == false) {
				JOptionPane.showMessageDialog(null,
						"Informar ENVIO ou RETORNO - BOOK NÃO gerado!");
			} else {
				this.gravador.close();
				JOptionPane.showMessageDialog(null, "Processamento Concluído.");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	private void montaWorkingFixo(String wrk) {
		this.dataAtual = this.objCalendario.getMesAtual() + "/"
				+ this.objCalendario.getAnoAtual();

		this.gravador
				.println("      *****************************************************************");
		this.gravador
				.println("      *    BOOK DE TAGS = ESTRUTURA ARQUIVO ACCCXXX                   *");
		this.gravador.println("      *    NOME DA INC  = SCC3" + wrk
				+ "                                    *");
		this.gravador.println("      *    DATA         = " + this.dataAtual
				+ "                                     *");
		this.gravador
				.println("      *    RESPONSAVEL  = BSI TECNOLOGIA                              *");
		this.gravador
				.println("      *****************************************************************");
	}

	private void montaWorkingEnvio(String wrk) {

		this.gravador.println("");
		this.gravador.println("       01  " + wrk + "-TAGS-ENVIO.");

		for (String a : this.varWrkEnvio) {

			this.linha1 = "           05  " + wrk + "-" + a.toUpperCase();
			this.tamanhoLinha1 = this.linha1.length();
			this.campo1 = this.varForTamEnvio.get(b) + 2;
			if (campo1 < 10) {
				this.impCampoFinalizado = "00" + this.campo1;
			} else {
				this.impCampoFinalizado = "0" + this.campo1;
			}

			if (tamanhoLinha1 >= 40) {
				this.gravador.println(linha1);
				this.tag = a.replace('-', '_');
				this.gravador
						.println("                                        PIC  X("
								+ this.impCampoFinalizado
								+ ")         VALUE\n"
								+ "               '<" + tag + ">'.");
			} else {
				this.tamanhoLinha1 = 40 - this.tamanhoLinha1;
				this.spaces = this.objUtil.getEspacos(this.tamanhoLinha1);
				this.tag = a.replace('-', '_');

				this.linha1 = "           05  "
						+ wrk
						+ "-"
						+ a.toUpperCase()
						+ this.spaces
						+ ("PIC  X(" + this.impCampoFinalizado
								+ ")         VALUE\n" + "               '<"
								+ tag + ">'.");
				this.gravador.println(this.linha1);
			}

			this.linha2 = "           05  " + wrk + "-F-" + a.toUpperCase();
			this.tamanhoLinha2 = this.linha2.length();
			this.campo2 = this.varForTamEnvio.get(b) + 3;

			if (campo2 < 10) {
				this.impCampoFinalizado = "00" + this.campo2;
			} else {
				this.impCampoFinalizado = "0" + this.campo2;
			}

			if (tamanhoLinha2 >= 40) {
				this.gravador.println(this.linha2);
				this.tag = a.replace('-', '_');
				this.gravador
						.println("                                        PIC  X("
								+ this.impCampoFinalizado
								+ ")         VALUE\n"
								+ "               '</" + tag + ">'.");
			} else {
				this.tamanhoLinha2 = 40 - this.tamanhoLinha2;
				this.spaces = this.objUtil.getEspacos(this.tamanhoLinha2);
				this.tag = a.replace('-', '_');

				this.linha2 = "           05  "
						+ wrk
						+ "-F-"
						+ a.toUpperCase()
						+ this.spaces
						+ ("PIC  X(" + this.impCampoFinalizado
								+ ")         VALUE\n" + "               '</"
								+ tag + ">'.");
				this.gravador.println(this.linha2);
			}
			this.b++;
		}
	}

	private void montaWorkingRetorno(String wrk) {
		this.gravador.println("");
		this.gravador.println("       01  " + wrk + "-TAGS-RETORNO.");

		for (String a : this.varWrkRetorno) {
			this.linha1 = "           05  " + wrk + "-" + a.toUpperCase()
					+ "-R";
			this.tamanhoLinha1 = this.linha1.length();

			if (tamanhoLinha1 >= 40) {
				this.gravador.println(linha1);
				this.tag = a.replace('-', '_');
				this.gravador
						.println("                                        PIC  X(040)"
								+ "         VALUE\n"
								+ "               '"
								+ this.tag + "'.");
			} else {
				this.tamanhoLinha1 = 40 - this.tamanhoLinha1;
				this.spaces = this.objUtil.getEspacos((this.tamanhoLinha1));
				this.tag = a.replace('-', '_');
				this.linha1 = "           05  "
						+ wrk
						+ "-"
						+ a.toUpperCase()
						+ "-R"
						+ this.spaces
						+ ("PIC  X(040)         VALUE\n" + "               '"
								+ this.tag + "'.");
				this.gravador.println(this.linha1);
			}
		}
	}

	private void montarListaVariaveisEnvio() {
		try {
			this.campoDaTabela = (String) this.leitorEnvio.nextLine();
			this.campoDaTabela = this.campoDaTabela.trim();
			this.index = this.campoDaTabela.indexOf("/");

			if (this.index < 0) {
				this.novoCampoDaTabela = this.campoDaTabela.replace('_', '-');
				this.tamanhoCampo = this.novoCampoDaTabela.length();
				this.campoFinalizado = this.novoCampoDaTabela.substring(1,
						this.tamanhoCampo);
				this.index = this.campoFinalizado.indexOf(">");
				this.listCampoFinalizado = this.campoFinalizado.substring(0,
						this.index);

				if (this.listCampoFinalizado.contains("Grupo")) {
					this.indDuplicadoEnvio = this.varWrkEnvio
							.indexOf(this.listCampoFinalizado);
					if (this.indDuplicadoEnvio < 0) {
						this.tamanhoCampoFinal = (this.listCampoFinalizado
								.trim()).length();
						this.varWrkEnvio.add(this.listCampoFinalizado.trim());
						this.varForTamEnvio.add(this.tamanhoCampoFinal);
					}
				} else {
					this.tamanhoCampoFinal = (this.listCampoFinalizado.trim())
							.length();
					this.varWrkEnvio.add(this.listCampoFinalizado.trim());
					this.varForTamEnvio.add(this.tamanhoCampoFinal);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao montar lista de Envio.");
		}
	}

	private void montarListaVariaveisRetorno() {
		try {
			this.campoDaTabela = (String) this.leitorRetorno.nextLine();
			this.campoDaTabela = this.campoDaTabela.trim();
			this.index = this.campoDaTabela.indexOf("/");

			if (this.index < 0) {
				this.novoCampoDaTabela = this.campoDaTabela.replace('_', '-');
				this.tamanhoCampo = this.novoCampoDaTabela.length();
				this.campoFinalizado = this.novoCampoDaTabela.substring(1,
						this.tamanhoCampo);
				this.index = this.campoFinalizado.indexOf(">");
				this.listCampoFinalizado = this.campoFinalizado.substring(0,
						this.index);

				if (this.listCampoFinalizado.contains("Grupo")) {
					this.indDuplicadoRetorno = this.varWrkRetorno
							.indexOf(this.listCampoFinalizado);
					if (this.indDuplicadoRetorno < 0) {
						this.varWrkRetorno.add(this.listCampoFinalizado.trim());
					}
				} else {
					this.varWrkRetorno.add(this.listCampoFinalizado.trim());
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao montar lista de Retorno.");
		}
	}

}