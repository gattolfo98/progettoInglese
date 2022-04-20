import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Project to classificate conditional phrases
 * 
 * @author Gabriele Triglia, Federico Biasioli
 * @version 1.2
 */
public class Main {

	/** prima parte della frase */
	static String primoPeriodo = "";

	/** seconda parte della frase */
	static String secondoPeriodo = "";

	/** tempo verbale del primo periodo */
	static int tempoVerbalePP = 0;

	/** tempo verbale del secondo periodo */
	static int tempoVerbaleSP = 0;

	/** tempo verbale del primo periodo */
	static int tempoPrimoPeriodo = 0;

	/** tempo verbale del secondo periodo */
	static int tempoSecondoPeriodo = 0;

	/** array contenente gli indici dei verbi del primo periodo */
	static String[] parolePrimoPeriodo = new String[100];

	/** array contenente gli indici dei verbi del secondo periodo */
	static String[] paroleSecondoPeriodo = new String[100];

	/** lista contenente dei codici per la funzione gendeRecognition */
	static ArrayList<Integer> codiceTempoVerbalePP = new ArrayList<Integer>();

	/** lista contenente dei codici per la funzione gendeRecognition */
	static ArrayList<Integer> codiceTempoVerbaleSP = new ArrayList<Integer>();

	/** lista contenente i verbi al primo periodo */
	static ArrayList<String> verbiPrimoPeriodo = new ArrayList<String>();

	/** lista contenente i verbi del secondo periodo */
	static ArrayList<String> verbiSecondoPeriodo = new ArrayList<String>();

	/** lista contenente i verbi al present simple */
	static ArrayList<String> presentSimpleList = new ArrayList<String>();

	/** lista contenente i verbi al past participle */
	static ArrayList<String> pastParticipleList = new ArrayList<String>();

	/** lista contenente i verbi al past simple */
	static ArrayList<String> pastSimpleList = new ArrayList<String>();

	/** lista contenente i verbi presenti alla terza persona singolare */
	static ArrayList<String> terzaColonnaList = new ArrayList<String>();

	/** lista contenente i verbi alla ing form */
	static ArrayList<String> ingFormList = new ArrayList<String>();

	/** lista contenente i token per il periodo ipotetico */
	static ArrayList<String> condizione = new ArrayList<String>();

	/**
	 * Metodo principale del programma
	 * @param args arguments
	 */
	public static void main(String[] args) {
		System.out.println("\t\tConditional phrases control program");
		/*
		 * frasi usate:
		 * If it hadn't been for you, I wouldn't be here now.
		 * I'm afraid of flying. If I wasn't afraid of flying we'd have travelled by
		 * plane.
		 */
		String frase = "we'd have travelled by plane If I h not afraid of flying";
		creaConzizionali();
		phraseSplit(frase);
		creazioneVettori();
		scomponiPeriodo();
		Rumbling();
		gendeRecognition();
	}

	/**
	 * metodo statico per dividere la frase condizonale
	 * 
	 * @param frase la frase da dividere
	 */
	private static void phraseSplit(String frase) {
		String lineaFile = frase.toLowerCase();

		for (int i = 0; i < condizione.size(); i++) {
			String parolaDaCercare = condizione.get(i);
			int poizioneParola = findToken(frase, parolaDaCercare);
			if (poizioneParola == -1) {
				continue;
			}

			System.out.println("Indice di if: " + poizioneParola);

			int indice2 = 0;
			// controllo per capire se è una sottostringa o una vera e propria parola
			if (lineaFile.contains(",") || lineaFile.contains("?") || lineaFile.contains("!")
					|| lineaFile.contains(".")) {
				indice2 = lineaFile.indexOf(",");
				if (indice2 == -1) {
					indice2 = lineaFile.indexOf("!");
					indice2 = lineaFile.indexOf("?");
					indice2 = lineaFile.indexOf(".");
				}
				if (indice2 == lineaFile.length()) {
					indice2 = -1;
				}
			}
			System.out.println("Indice di segno di divisione: " + indice2);

			if (indice2 != -1 && indice2 > poizioneParola) {
				secondoPeriodo = lineaFile.substring(indice2 + 1);
				primoPeriodo = lineaFile.substring(0, indice2 + 1);
				System.out.println(primoPeriodo);
				System.out.println(secondoPeriodo);
			}

			if (indice2 < poizioneParola) {
				secondoPeriodo = lineaFile.substring(poizioneParola);
				primoPeriodo = lineaFile.substring(0, poizioneParola);
				System.out.println(primoPeriodo);
				System.out.println(secondoPeriodo);
			}
		}
	}

	/**
	 * Metodo statico per popolare le Liste con i verbi
	 */
	private static void creazioneVettori() {
		String nomeFile = "English_verb.CSV";
		String nomeCartella = "Database";
		String percorsoFile = nomeCartella + File.separator + nomeFile;
		String lineaFile = "";
		int indice = 0;
		File file = new File(percorsoFile);
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				lineaFile = sc.nextLine().trim();
				indice = lineaFile.indexOf(";");
				presentSimpleList.add(lineaFile.substring(0, indice));
				lineaFile = lineaFile.substring(indice + 1);
				indice = lineaFile.indexOf(";");
				pastSimpleList.add(lineaFile.substring(0, indice));
				lineaFile = lineaFile.substring(indice + 1);
				indice = lineaFile.indexOf(";");
				pastParticipleList.add(lineaFile.substring(0, indice));
				lineaFile = lineaFile.substring(indice + 1);
				indice = lineaFile.indexOf(";");
				terzaColonnaList.add(lineaFile.substring(0, indice));
				lineaFile = lineaFile.substring(indice + 1);
				indice = lineaFile.indexOf(";");
				ingFormList.add(lineaFile.substring(0, indice));
			}
			sc.close();
		} catch (Exception e) {
			System.out.println("Tipo di eccezione --> " + e.toString());
		}
	}

	/**
	 * metodo statico per risconoscere il tempo verbale dei due periodi
	 */
	private static void gendeRecognition() {
		if (verbiPrimoPeriodo.size() == 1) {
			// Present simple
			if (codiceTempoVerbalePP.get(0) == 1 || codiceTempoVerbalePP.get(0) == 4) {
				tempoVerbalePP = 1;
			}
			// Past simple
			if (codiceTempoVerbalePP.get(0) == 2) {
				tempoVerbalePP = 2;
			}
		}
		if (verbiPrimoPeriodo.size() == 2) {
			// Present Continuous
			if (codiceTempoVerbalePP.get(0) == 1 || codiceTempoVerbalePP.get(0) == 4) {
				if (codiceTempoVerbalePP.get(1) == 5) {
					tempoVerbalePP = 3;
				}
			}
			// Present Perfect
			if (codiceTempoVerbalePP.get(0) == 1 || codiceTempoVerbalePP.get(0) == 4) {
				if (codiceTempoVerbalePP.get(1) == 3 || codiceTempoVerbalePP.get(1) == 2) {
					tempoVerbalePP = 4;
				}
			}
			// Past Continuous
			if (codiceTempoVerbalePP.get(0) == 2) {
				if (codiceTempoVerbalePP.get(1) == 5) {
					tempoVerbalePP = 5;
				}
			}
			// Present Perfect
			if (codiceTempoVerbalePP.get(0) == 2) {
				if (codiceTempoVerbalePP.get(1) == 3 || codiceTempoVerbalePP.get(1) == 2) {
					tempoVerbalePP = 6;
				}
			}
			// Future simple
			if (codiceTempoVerbalePP.get(0) == 6) {
				if (codiceTempoVerbalePP.get(1) == 1) {
					tempoVerbalePP = 7;
				}
			}
		}
		if (verbiPrimoPeriodo.size() == 3) {
			// Present Perfect Continuous
			if (codiceTempoVerbalePP.get(0) == 1 || codiceTempoVerbalePP.get(0) == 4) {
				if (codiceTempoVerbalePP.get(1) == 3 || codiceTempoVerbalePP.get(1) == 2) {
					if (codiceTempoVerbalePP.get(2) == 5) {
						tempoVerbalePP = 8;
					}
				}
			}
			// Present Perfect Continuous
			if (codiceTempoVerbalePP.get(0) == 2) {
				if (codiceTempoVerbalePP.get(1) == 3 || codiceTempoVerbalePP.get(1) == 2) {
					if (codiceTempoVerbalePP.get(2) == 5) {
						tempoVerbalePP = 9;
					}
				}
			}
		}
		if (verbiSecondoPeriodo.size() == 1) {
			// Present simple
			if (codiceTempoVerbaleSP.get(0) == 1 || codiceTempoVerbaleSP.get(0) == 4) {
				tempoVerbaleSP = 1;
			}
			// Past simple
			if (codiceTempoVerbaleSP.get(0) == 2) {
				tempoVerbaleSP = 2;
			}
		}
		if (verbiSecondoPeriodo.size() == 2) {
			// Present Continuous
			if (codiceTempoVerbaleSP.get(0) == 1 || codiceTempoVerbaleSP.get(0) == 4) {
				if (codiceTempoVerbaleSP.get(1) == 5) {
					tempoVerbaleSP = 3;
				}
			}
			// Present Perfect
			if (codiceTempoVerbaleSP.get(0) == 1 || codiceTempoVerbaleSP.get(0) == 4) {
				if (codiceTempoVerbaleSP.get(1) == 3) {
					tempoVerbaleSP = 4;
				}
			}
			// Past Continuous
			if (codiceTempoVerbaleSP.get(0) == 2) {
				if (codiceTempoVerbaleSP.get(1) == 5) {
					tempoVerbaleSP = 5;
				}
			}
			// Present Perfect
			if (codiceTempoVerbaleSP.get(0) == 2) {
				if (codiceTempoVerbaleSP.get(1) == 3) {
					tempoVerbaleSP = 6;
				}
			}
			// Future simple
			if (codiceTempoVerbaleSP.get(0) == 6) {
				if (codiceTempoVerbaleSP.get(1) == 1) {
					tempoVerbaleSP = 7;
				}
			}
		}
		if (verbiSecondoPeriodo.size() == 3) {
			// Present Perfect Continuous
			if (codiceTempoVerbaleSP.get(0) == 1 || codiceTempoVerbaleSP.get(0) == 4) {
				if (codiceTempoVerbaleSP.get(1) == 3) {
					if (codiceTempoVerbaleSP.get(2) == 5) {
						tempoVerbaleSP = 8;
					}
				}
			}
			// Present Perfect Continuous
			if (codiceTempoVerbaleSP.get(0) == 2) {
				if (codiceTempoVerbaleSP.get(1) == 3) {
					if (codiceTempoVerbaleSP.get(2) == 5) {
						tempoVerbaleSP = 9;
					}
				}
			}
		}
		System.out.println("Primo periodo " + tempoVerbalePP);
		// System.out.println("Secondo periodo " +tempoVerbaleSP);
	}

	/**
	 * metodo statico per riconoscere il tempo verbale del verbo non composto
	 */
	private static void Rumbling() {
		String verboCompostoPrimoPeriodo = "";
		String verboCompostoSecondoPeriodo = "";
		int i = 0;
		int z = 0;
		while (z < parolePrimoPeriodo.length) {
			if (parolePrimoPeriodo[z].equals(presentSimpleList.get(i))) {
				if (parolePrimoPeriodo[z].equals("will")) {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(6);
				} else {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(1);
				}
			}
			if (parolePrimoPeriodo[z].equals(pastSimpleList.get(i))) {
				verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbalePP.add(2);
			}
			if (parolePrimoPeriodo[z].equals(pastParticipleList.get(i))) {
				verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbalePP.add(3);
			}
			if (parolePrimoPeriodo[z].equals(terzaColonnaList.get(i))) {
				verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbalePP.add(4);
			}
			if (parolePrimoPeriodo[z].equals(ingFormList.get(i))) {
				verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbalePP.add(5);
			}
			i++;
			if (i == presentSimpleList.size()) {
				i = 0;
				z++;
			}
		}
		i = 0;
		z = 0;
		while (z < paroleSecondoPeriodo.length) {
			if (paroleSecondoPeriodo[z].equals(presentSimpleList.get(i))) {
				verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbaleSP.add(1);
			}
			if (paroleSecondoPeriodo[z].equals(pastSimpleList.get(i))) {
				verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbaleSP.add(2);
			}
			if (paroleSecondoPeriodo[z].equals(pastParticipleList.get(i))) {
				verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbaleSP.add(3);
			}
			if (paroleSecondoPeriodo[z].equals(terzaColonnaList.get(i))) {
				verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbaleSP.add(4);
			}
			if (paroleSecondoPeriodo[z].equals(ingFormList.get(i))) {
				verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
				i = 0;
				z++;
				codiceTempoVerbaleSP.add(5);
			}
			i++;
			if (i == presentSimpleList.size()) {
				i = 0;
				z++;
			}
		}
		i = 0;
		while (verbiPrimoPeriodo.size() > i) {
			if (i == 0) {
				verboCompostoPrimoPeriodo = verbiPrimoPeriodo.get(i);
			} else {
				verboCompostoPrimoPeriodo = verboCompostoPrimoPeriodo + " " + verbiPrimoPeriodo.get(i);
			}
			i++;
		}
		i = 0;
		while (verbiSecondoPeriodo.size() > i) {
			if (i == 0) {
				verboCompostoSecondoPeriodo = verbiSecondoPeriodo.get(i);
			} else {
				verboCompostoSecondoPeriodo = verboCompostoSecondoPeriodo + " " + verbiSecondoPeriodo.get(i);
			}
			i++;
		}
		System.out.println(verboCompostoPrimoPeriodo);
		System.out.println(verboCompostoSecondoPeriodo);
	}

	/**
	 * metodo statico per scomporre i periodi in parole 
	 */
	private static void scomponiPeriodo() {
		String copiaPP = primoPeriodo;
		String copiaSP = secondoPeriodo;
		parolePrimoPeriodo = copiaPP.split(" ");
		paroleSecondoPeriodo = copiaSP.split(" ");
	}

	/**
	 * Metodo statico per trovare il il token (parola da cercare)
	 * 
	 * @param frase           la frase dove eseguire la ricerca
	 * @param parolaDaCercare la parola da cercare nella frase
	 * @return pozisione della parola
	 */
	private static int findToken(String frase, String parolaDaCercare) {
		String lineaFile = frase.toLowerCase();
		int poizioneParola = lineaFile.indexOf(parolaDaCercare);

		// verifico presenza stringa: ciclo se non presente
		if (poizioneParola == -1) {
			return -1;
		}

		// verifico la presenza di un carattere prima della stringa se la stringa non
		// inizia in posizione 0
		if ((poizioneParola != 0) && (Character.isLetter(lineaFile.charAt(poizioneParola - 1)))) {
			return -1;
		}

		return poizioneParola;
	}

	/**
	 * metodo statico per popolare la lista condizione
	 */
	private static void creaConzizionali() {
		condizione.add("if ");
		condizione.add("as long as ");
		condizione.add("only if ");
		condizione.add("provided ");
		condizione.add("providing ");
		condizione.add("on condition ");
		condizione.add("whether or not ");
		condizione.add("even if ");
		condizione.add("suppose ");
		condizione.add("supposing ");
		condizione.add("unless ");
	}
}