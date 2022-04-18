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
	static String primoPeriodo = "";
	static String secondoPeriodo = "";
	static int tempoPrimoPeriodo = 0;
	static int tempoSecondoPeriodo = 0;
	static ArrayList<String> presentSimpleList = new ArrayList<String>();
	static ArrayList<String> pastParticipleList = new ArrayList<String>();
	static ArrayList<String> pastSimpleList = new ArrayList<String>();
	static ArrayList<String> terzaColonnaList = new ArrayList<String>();
	static ArrayList<String> ingFormList = new ArrayList<String>();
	static ArrayList<String> condizione = new ArrayList<String>();

	public static void main(String[] args) {
		System.out.println("\t\tConditional phrases control program");
		/*
		 * frasi usate:
		 * If it hadn't been for you, I wouldn't be here now.
		 * I'm afraid of flying. If I wasn't afraid of flying we'd have travelled by
		 * plane.
		 */
		String frase = "done if do";
		condizione.add("if ");
		condizione.add("as long as ");
		phraseSplit(frase);
		creazioneVettori();
		Rumbling();
	}

	private static void phraseSplit(String frase) {
		String lineaFile = frase.toLowerCase();

		for (int i = 0; i < condizione.size(); i++) {
			String parolaDaCercare = condizione.get(i);
			int poizioneParola = findToken(frase, parolaDaCercare);
			if (poizioneParola==-1) {
				continue;
			}
			
			System.out.println("Indice di if: " + poizioneParola);		

			int indice2 = 0;
			// controllo per capire se è una sottostringa o una vera e propria parola
			if (lineaFile.contains(",") || lineaFile.contains("?") || lineaFile.contains("!") || lineaFile.contains(".")) {
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

	private static void Rumbling() {
		int i = 0;
		int valore = 0;
		int valorePrimo = 0;
		for (i = 0; i < presentSimpleList.size(); i++) {
			if (primoPeriodo.contains(presentSimpleList.get(i))) {
				if (primoPeriodo.indexOf(presentSimpleList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = primoPeriodo.indexOf(presentSimpleList.get(i)) - 1;
				}
				valore = primoPeriodo.indexOf(presentSimpleList.get(i)) + (presentSimpleList.get(i).length());
				if (valore < primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
							&& (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
							&& (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
									|| primoPeriodo.charAt(valorePrimo) == presentSimpleList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == presentSimpleList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 1;
						System.out.println("Il tempo verbale del primo periodo e' il Present Simple.");
						break;
					}
				}
				if (valore == primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
							|| primoPeriodo.charAt(valorePrimo) == presentSimpleList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == presentSimpleList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 1;
						System.out.println("Il tempo verbale del primo periodo e' il Present Simple.");
						break;
					}
				}
			}
			if (primoPeriodo.contains(pastSimpleList.get(i))) {
				if (primoPeriodo.indexOf(pastSimpleList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = primoPeriodo.indexOf(pastSimpleList.get(i)) - 1;
				}
				valore = primoPeriodo.indexOf(pastSimpleList.get(i)) + (pastSimpleList.get(i).length());
				if (valore < primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
							&& (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
							&& (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
									|| primoPeriodo.charAt(valorePrimo) == pastSimpleList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == pastSimpleList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 2;
						System.out.println("Il tempo verbale del primo periodo e' il Past Simple.");
						break;
					}
				}
				if (valore == primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
							|| primoPeriodo.charAt(valorePrimo) == pastSimpleList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == pastSimpleList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 2;
						System.out.println("Il tempo verbale del primo periodo e' il Past Simple.");
						break;
					}
				}
			}
			if (primoPeriodo.contains(pastParticipleList.get(i))) {
				if (primoPeriodo.indexOf(pastParticipleList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = primoPeriodo.indexOf(pastParticipleList.get(i)) - 1;
				}
				valore = primoPeriodo.indexOf(pastParticipleList.get(i)) + (pastParticipleList.get(i).length());
				if (valore < primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
							&& (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
							&& (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
									|| primoPeriodo.charAt(valorePrimo) == pastParticipleList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == pastParticipleList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 3;
						System.out.println("Il tempo verbale del primo periodo e' il Past Participle.");
						break;
					}
				}
				if (valore == primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
							|| primoPeriodo.charAt(valorePrimo) == pastParticipleList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == pastParticipleList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 3;
						System.out.println("Il tempo verbale del primo periodo e' il Past Participle.");
						break;
					}
				}
			}
			if (primoPeriodo.contains(terzaColonnaList.get(i))) {
				if (primoPeriodo.indexOf(terzaColonnaList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = primoPeriodo.indexOf(terzaColonnaList.get(i)) - 1;
				}
				valore = primoPeriodo.indexOf(terzaColonnaList.get(i)) + (terzaColonnaList.get(i).length());
				if (valore < primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
							&& (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
							&& (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
									|| primoPeriodo.charAt(valorePrimo) == terzaColonnaList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == terzaColonnaList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 4;
						System.out.println("Il tempo verbale del primo periodo e' della terza persona");
						break;
					}
				}
				if (valore == primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
							|| primoPeriodo.charAt(valorePrimo) == terzaColonnaList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == terzaColonnaList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 4;
						System.out.println("Il tempo verbale del primo periodo e' della terza persona");
						break;
					}
				}
			}
			if (primoPeriodo.contains(ingFormList.get(i))) {
				if (primoPeriodo.indexOf(ingFormList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = primoPeriodo.indexOf(ingFormList.get(i)) - 1;
				}
				valore = primoPeriodo.indexOf(ingFormList.get(i)) + (ingFormList.get(i).length());
				if (valore < primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
							&& (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
							&& (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
									|| primoPeriodo.charAt(valorePrimo) == ingFormList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == ingFormList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 5;
						System.out.println("Il tempo verbale del primo periodo e' in forma ing.");
						break;
					}
				}
				if (valore == primoPeriodo.length()) {
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
							|| primoPeriodo.charAt(valorePrimo) == ingFormList.get(i).charAt(0))
							&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
									|| primoPeriodo.charAt(valorePrimo) == ingFormList.get(i).charAt(0))) {
						tempoPrimoPeriodo = 5;
						System.out.println("Il tempo verbale del primo periodo e' in forma ing.");
						break;
					}
				}
			}
		}
		for (i = 0; i < presentSimpleList.size(); i++) {
			if (secondoPeriodo.contains(presentSimpleList.get(i))) {
				if (secondoPeriodo.indexOf(presentSimpleList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = secondoPeriodo.indexOf(presentSimpleList.get(i)) - 1;
				}
				valore = secondoPeriodo.indexOf(presentSimpleList.get(i)) + (presentSimpleList.get(i).length());
				if (valore < secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
							&& (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
							&& (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
									|| secondoPeriodo.charAt(valorePrimo) == presentSimpleList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == presentSimpleList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 1;
						System.out.println("Il tempo verbale del secondo periodo e' il Present Simple.");
						break;
					}
				}
				if (valore == secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
							|| secondoPeriodo.charAt(valorePrimo) == presentSimpleList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == presentSimpleList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 1;
						System.out.println("Il tempo verbale del secondo periodo e' il Present Simple.");
						break;
					}
				}
			}
			if (secondoPeriodo.contains(pastSimpleList.get(i))) {
				if (secondoPeriodo.indexOf(pastSimpleList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = secondoPeriodo.indexOf(pastSimpleList.get(i)) - 1;
				}
				valore = secondoPeriodo.indexOf(pastSimpleList.get(i)) + (pastSimpleList.get(i).length());
				if (valore < secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
							&& (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
							&& (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
									|| secondoPeriodo.charAt(valorePrimo) == pastSimpleList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == pastSimpleList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 2;
						System.out.println("Il tempo verbale del secondo periodo e' il Past Simple.");
						break;
					}
				}
				if (valore == secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
							|| secondoPeriodo.charAt(valorePrimo) == pastSimpleList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == pastSimpleList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 2;
						System.out.println("Il tempo verbale del secondo periodo e' il Past Simple.");
						break;
					}
				}
			}
			if (secondoPeriodo.contains(pastParticipleList.get(i))) {
				if (secondoPeriodo.indexOf(pastParticipleList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = secondoPeriodo.indexOf(pastParticipleList.get(i)) - 1;
				}
				valore = secondoPeriodo.indexOf(pastParticipleList.get(i)) + (pastParticipleList.get(i).length());
				if (valore < secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
							&& (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
							&& (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
									|| secondoPeriodo.charAt(valorePrimo) == pastParticipleList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == pastParticipleList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 3;
						System.out.println("Il tempo verbale del secondo periodo e' il Past Participle.");
						break;
					}
				}
				if (valore == secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
							|| secondoPeriodo.charAt(valorePrimo) == pastParticipleList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == pastParticipleList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 3;
						System.out.println("Il tempo verbale del secondo periodo e' il Past Participle.");
						break;
					}
				}
			}
			if (secondoPeriodo.contains(terzaColonnaList.get(i))) {
				if (secondoPeriodo.indexOf(terzaColonnaList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = secondoPeriodo.indexOf(terzaColonnaList.get(i)) - 1;
				}
				valore = secondoPeriodo.indexOf(terzaColonnaList.get(i)) + (terzaColonnaList.get(i).length());
				if (valore < secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
							&& (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
							&& (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
									|| secondoPeriodo.charAt(valorePrimo) == terzaColonnaList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == terzaColonnaList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 4;
						System.out.println("Il tempo verbale del secondo periodo e' della terza persona.");
						break;
					}
				}
				if (valore == secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
							|| secondoPeriodo.charAt(valorePrimo) == terzaColonnaList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == terzaColonnaList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 4;
						System.out.println("Il tempo verbale del secondo periodo e' della terza persona.");
						break;
					}
				}
			}
			if (secondoPeriodo.contains(ingFormList.get(i))) {
				if (secondoPeriodo.indexOf(ingFormList.get(i)) == 0) {
					valorePrimo = 0;
				} else {
					valorePrimo = secondoPeriodo.indexOf(ingFormList.get(i)) - 1;
				}
				valore = secondoPeriodo.indexOf(ingFormList.get(i)) + (ingFormList.get(i).length());
				if (valore < secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
							&& (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
							&& (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
									|| secondoPeriodo.charAt(valorePrimo) == ingFormList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == ingFormList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 5;
						System.out.println("Il tempo verbale del secondo periodo e' in forma ing.");
						break;
					}
				}
				if (valore == secondoPeriodo.length()) {
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
							|| secondoPeriodo.charAt(valorePrimo) == ingFormList.get(i).charAt(0))
							&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
									|| secondoPeriodo.charAt(valorePrimo) == ingFormList.get(i).charAt(0))) {
						tempoSecondoPeriodo = 5;
						System.out.println("Il tempo verbale del secondo periodo e' in forma ing.");
						break;
					}
				}
			}
		}
	}
	
	private static int findToken(String frase, String parolaDaCercare){
		String lineaFile = frase.toLowerCase();
		int poizioneParola = lineaFile.indexOf(parolaDaCercare);

		// verifico presenza stringa: ciclo se non presente
		if (poizioneParola == -1) {
			return -1;
		}
		
		// verifico la presenza di un carattere prima della stringa se la stringa non inizia in posizione 0
		if ((poizioneParola != 0) && (Character.isLetter(lineaFile.charAt(poizioneParola - 1)))) {
			return -1;
		}

		return poizioneParola;
	}
}