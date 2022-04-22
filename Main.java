import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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
	static int isIf = 0;

	/** tempo verbale del primo periodo */
	static int tempoVerbalePP = 0;

	/** tempo verbale del secondo periodo */
	static int tempoVerbaleSP = 0;

	/** tempo verbale estratto dal primo periodo */
	static String verboCompostoPrimoPeriodo = " ";

	/** tempo verbale estratto dal secondo periodo */
	static String verboCompostoSecondoPeriodo = " ";

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
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		System.out.println("\t\tConditional phrases control program");
		/*
		 * frasi usate:
		 * If it hadn't been for you, I wouldn't be here now.
		 * If I wasn't afraid of flying, we'd have travelled by plane.
		 * As long as she needs me, I'll be there for her.
		 *if you did not study, you would not pass the exam.
		 *If this thing happens, that thing will happen
		 *if he does not call you, tell me immediately
		 */
		String frase = "if he does not call you, tell me immediately.";
		creaConzizionali();
		phraseSplit(frase);
		creazioneVettori();
		scomponiPeriodo();
		Rumbling();
		gendeRecognition();
		theLastOfUs();
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
				secondoPeriodo = lineaFile.substring(indice2 + 2, lineaFile.length()-1);
				primoPeriodo = lineaFile.substring(0, indice2);
				System.out.println(primoPeriodo);
				System.out.println(secondoPeriodo);
				int lunghezza = secondoPeriodo.length();
				isIf = 0;
			}

			if (indice2 < poizioneParola) {
				secondoPeriodo = lineaFile.substring(poizioneParola,lineaFile.length()-1);
				primoPeriodo = lineaFile.substring(0, poizioneParola);
				System.out.println(primoPeriodo);
				System.out.println(secondoPeriodo);
				isIf = 1;
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

	private static void gendeRecognition() {
		int scelta = 0;
		int rimozione = 0;
		Scanner sc1 = new Scanner(System.in);
		System.out.println("Il seguente verbo e' corretto? " + verboCompostoPrimoPeriodo);
		System.out.println("premere 0 per confermare / premere 1 per modificare il verbo");
		scelta = sc1.nextInt();
		while (scelta == 1) {
			System.out.println("Scegliere quale verbo togliere (es have travelled do --> premere 3 per rimuovere do): "
					+ verboCompostoPrimoPeriodo);
			rimozione = sc1.nextInt() - 1;
			System.out.println("Il seguente verbo e' stato rimosso: " + verbiPrimoPeriodo.get(rimozione));
			verbiPrimoPeriodo.remove(rimozione);
			scelta = 0;
		}
		System.out.println("--------------------------------------------------------------");
		System.out.println("Il seguente verbo e' corretto? " + verboCompostoSecondoPeriodo);
		System.out.println("premere 0 per confermare / premere 1 per modificare il verbo");
		scelta = sc1.nextInt();
		while (scelta == 1) {
			System.out.println("Scegliere quale verbo togliere (es have travelled do --> premere 3 per rimuovere do): "
					+ verboCompostoSecondoPeriodo);
			rimozione = sc1.nextInt()-1;
			System.out.println("Il seguente verbo e' stato rimosso: " + verbiSecondoPeriodo.get(rimozione));
			verbiSecondoPeriodo.remove(rimozione);
			scelta = 0;
		}
		sc1.close();
		if(verbiPrimoPeriodo.get(0).equals("would")){
			if(verbiPrimoPeriodo.size() == 2){
				//would + base form
				if((codiceTempoVerbalePP.get(1) == 1 || codiceTempoVerbalePP.get(1) == 4)&&tempoVerbalePP==0){
					tempoVerbalePP=10;
				}
			}
			if(verbiPrimoPeriodo.size() == 3){
				//would have + past participle form
				if(verbiPrimoPeriodo.get(1).equals("have")){
					if((codiceTempoVerbalePP.get(2) == 3 || codiceTempoVerbalePP.get(2) == 2)&&tempoVerbalePP==0){
						tempoVerbalePP=11;
					}
				}
			}
		}
		if (verbiPrimoPeriodo.size() == 1) {
			//imperative
			if(verbiPrimoPeriodo.get(0).equals(parolePrimoPeriodo[0]))
			{
				tempoVerbalePP=17;
			}
			// Present simple
			if ((codiceTempoVerbalePP.get(0) == 1 || codiceTempoVerbalePP.get(0) == 4)&&tempoVerbalePP==0) {
				tempoVerbalePP = 1;
			}
			// Past simple
			if (codiceTempoVerbalePP.get(0) == 2&&tempoVerbalePP==0) {
				tempoVerbalePP = 2;
			}
		}
		if (verbiPrimoPeriodo.size() == 2) {
			//may form
			if(verbiPrimoPeriodo.get(0).equals("may")&&tempoVerbalePP==0){
				if((codiceTempoVerbalePP.get(1)==1 || codiceTempoVerbalePP.get(1) == 4)&&tempoVerbalePP==0){
					tempoVerbalePP = 12;
				}
			}
			//could form
			if (verbiPrimoPeriodo.get(0).equals("could") && tempoVerbalePP == 0) {
				if ((codiceTempoVerbalePP.get(1) == 1 || codiceTempoVerbalePP.get(1) == 4) && tempoVerbalePP == 0) {
					tempoVerbalePP = 13;
				}
			}
			//might form
			if (verbiPrimoPeriodo.get(0).equals("might") && tempoVerbalePP == 0) {
				if ((codiceTempoVerbalePP.get(1) == 1 || codiceTempoVerbalePP.get(1) == 4) && tempoVerbalePP == 0) {
					tempoVerbalePP = 14;
				}
			}
			//must form
			if (verbiPrimoPeriodo.get(0).equals("must") && tempoVerbalePP == 0) {
				if ((codiceTempoVerbalePP.get(1) == 1 || codiceTempoVerbalePP.get(1) == 4) && tempoVerbalePP == 0) {
					tempoVerbalePP = 15;
				}
			}
			// must form
			if (verbiPrimoPeriodo.get(0).equals("can") && tempoVerbalePP == 0) {
				if ((codiceTempoVerbalePP.get(1) == 1 || codiceTempoVerbalePP.get(1) == 4) && tempoVerbalePP == 0) {
					tempoVerbalePP = 16;
				}
			}
			// Past Simple (negativo)
			if ((codiceTempoVerbalePP.get(0) == 2)&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 1) {
					tempoVerbalePP = 2;
				}
			}
			// Present Simple (negativo)
			if ((codiceTempoVerbalePP.get(0) == 1||codiceTempoVerbalePP.get(0) == 4)&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 1) {
					tempoVerbalePP = 1;
				}
			}
			// Present Continuous
			if ((codiceTempoVerbalePP.get(0) == 1 || codiceTempoVerbalePP.get(0) == 4)&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 5) {
					tempoVerbalePP = 3;
				}
			}
			// Present Perfect
			if ((verbiPrimoPeriodo.get(0).equals("have")  || verbiPrimoPeriodo.get(0).equals("has"))&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 3 || codiceTempoVerbalePP.get(1) == 2) {
					tempoVerbalePP = 4;
				}
			}
			// Past Continuous
			if (codiceTempoVerbalePP.get(0) == 2&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 5) {
					tempoVerbalePP = 5;
				}
			}
			// Past Perfect
			if (codiceTempoVerbalePP.get(0) == 2&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 3 || codiceTempoVerbalePP.get(1) == 2) {
					tempoVerbalePP = 6;
				}
			}
			// Future simple
			if (codiceTempoVerbalePP.get(0) == 6&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 1) {
					tempoVerbalePP = 7;
				}
			}
		}
		if (verbiPrimoPeriodo.size() == 3) {
			//could + have + past participle
			if(verbiPrimoPeriodo.get(0).equals("could")){
				if (verbiPrimoPeriodo.get(1).equals("have")) {
					if ((codiceTempoVerbalePP.get(2) == 3 || codiceTempoVerbalePP.get(2) == 2) && tempoVerbalePP == 0) {
						tempoVerbalePP = 18;
					}
				}
			}
			//might + have + past participle
			if(verbiPrimoPeriodo.get(0).equals("might")){
				if (verbiPrimoPeriodo.get(1).equals("have")) {
					if ((codiceTempoVerbalePP.get(2) == 3 || codiceTempoVerbalePP.get(2) == 2) && tempoVerbalePP == 0) {
						tempoVerbalePP = 19;
					}
				}
			}
			// Present Perfect Continuous
			if ((codiceTempoVerbalePP.get(0) == 1 || codiceTempoVerbalePP.get(0) == 4)&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 3 || codiceTempoVerbalePP.get(1) == 2) {
					if (codiceTempoVerbalePP.get(2) == 5) {
						tempoVerbalePP = 8;
					}
				}
			}
			// Present Perfect Continuous
			if (codiceTempoVerbalePP.get(0) == 2&&tempoVerbalePP==0) {
				if (codiceTempoVerbalePP.get(1) == 3 || codiceTempoVerbalePP.get(1) == 2) {
					if (codiceTempoVerbalePP.get(2) == 5) {
						tempoVerbalePP = 9;
					}
				}
			}
		}

		//Tempi verbali secondo periodo

		if(verbiSecondoPeriodo.get(0).equals("would")){
			if(verbiSecondoPeriodo.size() == 2){
				//would + base form
				if((codiceTempoVerbaleSP.get(1) == 1 || codiceTempoVerbaleSP.get(1) == 4)&&tempoVerbaleSP==0){
					tempoVerbaleSP=10;
				}
			}
			if(verbiSecondoPeriodo.size() == 3){
				//would have + past participle form
				if(verbiSecondoPeriodo.get(1).equals("have")){
					if((codiceTempoVerbaleSP.get(2) == 3 || codiceTempoVerbaleSP.get(2) == 2)&&tempoVerbaleSP==0){
						tempoVerbaleSP=11;
					}
				}
			}
		}
		if (verbiSecondoPeriodo.size() == 1) {
			//imperative
			if(verbiSecondoPeriodo.get(0).equals(paroleSecondoPeriodo[0]))
			{
				tempoVerbaleSP=17;
			}
			// Present simple
			if ((codiceTempoVerbaleSP.get(0) == 1 || codiceTempoVerbaleSP.get(0) == 4)&&tempoVerbaleSP==0) {
				tempoVerbaleSP = 1;
			}
			// Past simple
			if (codiceTempoVerbaleSP.get(0) == 2&&tempoVerbaleSP==0) {
				tempoVerbaleSP = 2;
			}
		}
		if (verbiSecondoPeriodo.size() == 2) {
			// may form
			if (verbiSecondoPeriodo.get(0).equals("may") && tempoVerbaleSP == 0) {
				if ((codiceTempoVerbaleSP.get(1) == 1 || codiceTempoVerbaleSP.get(1) == 4) && tempoVerbaleSP == 0) {
					tempoVerbaleSP = 12;
				}
			}
			// could form
			if (verbiSecondoPeriodo.get(0).equals("could") && tempoVerbaleSP == 0) {
				if ((codiceTempoVerbaleSP.get(1) == 1 || codiceTempoVerbaleSP.get(1) == 4) && tempoVerbaleSP == 0) {
					tempoVerbaleSP = 13;
				}
			}
			// might form
			if (verbiSecondoPeriodo.get(0).equals("might") && tempoVerbaleSP == 0) {
				if ((codiceTempoVerbaleSP.get(1) == 1 || codiceTempoVerbaleSP.get(1) == 4) && tempoVerbaleSP == 0) {
					tempoVerbaleSP = 14;
				}
			}
			// must form
			if (verbiSecondoPeriodo.get(0).equals("must") && tempoVerbaleSP == 0) {
				if ((codiceTempoVerbaleSP.get(1) == 1 || codiceTempoVerbaleSP.get(1) == 4) && tempoVerbaleSP == 0) {
					tempoVerbaleSP = 15;
				}
			}
			// can form
			if (verbiSecondoPeriodo.get(0).equals("can") && tempoVerbaleSP == 0) {
				if ((codiceTempoVerbaleSP.get(1) == 1 || codiceTempoVerbaleSP.get(1) == 4) && tempoVerbaleSP == 0) {
					tempoVerbaleSP = 16;
				}
			}
			// Past Simple (negativo)
			if ((codiceTempoVerbaleSP.get(0) == 2)&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 1) {
					tempoVerbaleSP = 3;
				}
			}
			// Present Simple (negativo)
			if ((codiceTempoVerbaleSP.get(0) == 1||codiceTempoVerbaleSP.get(0) == 4)&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 1) {
					tempoVerbaleSP = 1;
				}
			}
			// Present Continuous
			if ((codiceTempoVerbaleSP.get(0) == 1 || codiceTempoVerbaleSP.get(0) == 4)&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 5) {
					tempoVerbaleSP = 3;
				}
			}
			// Present Perfect
			if ((verbiSecondoPeriodo.get(0).equals("have") || verbiSecondoPeriodo.get(0).equals("has"))&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 3||codiceTempoVerbaleSP.get(1) == 2) {
					tempoVerbaleSP = 4;
				}
			}
			// Past Continuous
			if (codiceTempoVerbaleSP.get(0) == 2&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 5) {
					tempoVerbaleSP = 5;
				}
			}
			// Past Perfect
			if (codiceTempoVerbaleSP.get(0) == 2&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 3||codiceTempoVerbaleSP.get(1) == 2) {
					tempoVerbaleSP = 6;
				}
			}
			// Future simple
			if (codiceTempoVerbaleSP.get(0) == 6&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 1) {
					tempoVerbaleSP = 7;
				}
			}
		}
		if (verbiSecondoPeriodo.size() == 3) {
			// could + have + past participle
			if (verbiSecondoPeriodo.get(0).equals("could")) {
				if (verbiSecondoPeriodo.get(1).equals("have")) {
					if ((codiceTempoVerbaleSP.get(2) == 3 || codiceTempoVerbaleSP.get(2) == 2) && tempoVerbaleSP == 0) {
						tempoVerbaleSP = 18;
					}
				}
			}
			// might + have + past participle
			if (verbiSecondoPeriodo.get(0).equals("might")) {
				if (verbiSecondoPeriodo.get(1).equals("have")) {
					if ((codiceTempoVerbaleSP.get(2) == 3 || codiceTempoVerbaleSP.get(2) == 2) && tempoVerbaleSP == 0) {
						tempoVerbaleSP = 19;
					}
				}
			}
			// Present Perfect Continuous
			if ((codiceTempoVerbaleSP.get(0) == 1 || codiceTempoVerbaleSP.get(0) == 4)&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 3) {
					if (codiceTempoVerbaleSP.get(2) == 5) {
						tempoVerbaleSP = 8;
					}
				}
			}
			// past Perfect Continuos
			if (codiceTempoVerbaleSP.get(0) == 2&&tempoVerbaleSP==0) {
				if (codiceTempoVerbaleSP.get(1) == 3) {
					if (codiceTempoVerbaleSP.get(2) == 5) {
						tempoVerbaleSP = 9;
					}
				}
			}
		}
		System.out.println("Primo periodo " + tempoVerbalePP);
		System.out.println("Secondo periodo " + tempoVerbaleSP);
	}

	/**
	 * metodo statico per riconoscere il tempo verbale del verbo non composto
	 */
	private static void Rumbling() {
		verboCompostoPrimoPeriodo = "";
		verboCompostoSecondoPeriodo = "";
		int i = 0;
		int z = 0;
		while (z < parolePrimoPeriodo.length) {
			if (parolePrimoPeriodo[z].equals(presentSimpleList.get(i))) {
				if (parolePrimoPeriodo[z].equals("will")) {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(6);
				}   if (parolePrimoPeriodo[z].equals("would")) {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(7);
				}   else {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(1);
				}
				if (z == parolePrimoPeriodo.length)
					break;
			}
			if (parolePrimoPeriodo[z].equals(pastSimpleList.get(i))) {
				if (parolePrimoPeriodo[z].equals("could")) {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(8);
				}	else{
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(2);
				}
				if (z == parolePrimoPeriodo.length)
					break;
			}
			if (parolePrimoPeriodo[z].equals(pastParticipleList.get(i))) {
				if (parolePrimoPeriodo[z].equals("might")) {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(9);
				} else{
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(3);
				}
				if (z == parolePrimoPeriodo.length)
					break;
			}
			if (parolePrimoPeriodo[z].equals(terzaColonnaList.get(i))) {
				if (parolePrimoPeriodo[z].equals("may")) {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(10);
				} else{
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(4);
				}
				if (z == parolePrimoPeriodo.length)
					break;
			}
			if (parolePrimoPeriodo[z].equals(ingFormList.get(i))) {
				if (parolePrimoPeriodo[z].equals("must")) {
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(11);
				} else{
					verbiPrimoPeriodo.add(parolePrimoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbalePP.add(5);
				}
				if (z == parolePrimoPeriodo.length)
					break;
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
				if (paroleSecondoPeriodo[z].equals("will")) {
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(6);
				}   if (paroleSecondoPeriodo[z].equals("would")) {
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(7);
				}   else {
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(1);
				}
				if (z == paroleSecondoPeriodo.length)
					break;
			}
			if (paroleSecondoPeriodo[z].equals(pastSimpleList.get(i))) {
				if (paroleSecondoPeriodo[z].equals("could")) {
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(8);
				} else{
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(2);
				}
				if (z == paroleSecondoPeriodo.length)
					break;
			}
			if (paroleSecondoPeriodo[z].equals(pastParticipleList.get(i))) {
				if (paroleSecondoPeriodo[z].equals("might")) {
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(9);
				} else{
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(3);
				}
				if (z == parolePrimoPeriodo.length)
					break;
			}
			if (paroleSecondoPeriodo[z].equals(terzaColonnaList.get(i))) {
				if (paroleSecondoPeriodo[z].equals("may")) {
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(10);
				} else{
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(4);
				}
				if (z == paroleSecondoPeriodo.length)
					break;
			}
			if (paroleSecondoPeriodo[z].equals(ingFormList.get(i))) {
				if (paroleSecondoPeriodo[z].equals("must")) {
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(11);
				} else{
					verbiSecondoPeriodo.add(paroleSecondoPeriodo[z]);
					i = 0;
					z++;
					codiceTempoVerbaleSP.add(5);
				}
				if (z == paroleSecondoPeriodo.length)
					break;
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
		//System.out.println(verboCompostoPrimoPeriodo);
		//System.out.println(verboCompostoSecondoPeriodo);
	}

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

	private static void theLastOfUs() {
		// tipo uno
		int conferma = 0;
		if(tempoVerbalePP!=0 && tempoVerbaleSP !=0){
			if ((((isIf == 1 && tempoVerbaleSP == 1) || (isIf == 1 && tempoVerbaleSP == 3) || (isIf == 1 && tempoVerbaleSP == 4))
				|| ((isIf == 0 && tempoVerbalePP == 1) || (isIf == 0 && tempoVerbalePP == 3) || (isIf == 0 && tempoVerbalePP == 4)))
				&& (((isIf == 0 && tempoVerbaleSP == 7) || (isIf == 0 && tempoVerbaleSP == 12) || (isIf == 0 && tempoVerbaleSP == 14)
				|| (isIf == 0 && tempoVerbaleSP == 15) || (isIf == 0 && tempoVerbaleSP == 16)  || (isIf == 0 && tempoVerbaleSP == 17))
				|| ((isIf == 1 && tempoVerbalePP == 7) || (isIf == 1 && tempoVerbalePP == 12)  || (isIf == 1 && tempoVerbalePP == 14)
				|| (isIf == 1 && tempoVerbalePP == 15) || (isIf == 1 && tempoVerbalePP == 16)  || (isIf == 1 && tempoVerbalePP == 17)))) {
				System.out.println("Il tipo di condizionale e': 1");
				conferma++;
			}
			// tipo due
			if ((((isIf == 1 && tempoVerbaleSP == 2) || (isIf == 1 && tempoVerbaleSP == 5)) 
				|| ((isIf == 0&& tempoVerbalePP == 2) || (isIf == 0 && tempoVerbalePP == 5)))
				&& (((isIf == 1 && tempoVerbalePP == 10) || (isIf == 1 && tempoVerbalePP == 13) || (isIf == 1 && tempoVerbalePP == 15))
				|| ((isIf == 0 && tempoVerbaleSP == 10) || (isIf == 0 && tempoVerbaleSP == 13)|| (isIf == 0 && tempoVerbaleSP == 15)))) {
				System.out.println("Il tipo di condizionale e': 2");
				conferma++;
			}
			// tipo tre
			if ((((isIf == 1 && tempoVerbaleSP == 6) || (isIf == 1 && tempoVerbaleSP == 9))
				||(((isIf == 0 && tempoVerbalePP == 6) || (isIf == 0 && tempoVerbalePP == 9))))
				&& (((isIf == 1 && tempoVerbalePP == 11) || (isIf == 1 && tempoVerbalePP == 18) || (isIf == 1 && tempoVerbalePP == 19))
				|| ((isIf == 0&& tempoVerbaleSP == 11) || (isIf == 0 && tempoVerbaleSP == 18)|| (isIf == 0 && tempoVerbaleSP == 19)))) {
				System.out.println("Il tipo di condizionale e': 3");
				conferma++;
			}
			//	tipo zero
			if ((((tempoVerbalePP == 1) || (tempoVerbalePP == 3) || (tempoVerbalePP == 4)))
					&& (((tempoVerbaleSP == 1) || (tempoVerbaleSP == 3) || (tempoVerbaleSP == 4)))) {
				System.out.println("Il tipo di condizionale e': 0");
				conferma++;
			}
			//1 mixed
			if ((((isIf == 1 && tempoVerbaleSP == 6) || (isIf == 1 && tempoVerbaleSP == 9))
				||(((isIf == 0 && tempoVerbalePP == 6) || (isIf == 0 && tempoVerbalePP == 9))))
				&& (((isIf == 1 && tempoVerbalePP == 10) || (isIf == 1 && tempoVerbalePP == 13) || (isIf == 1 && tempoVerbalePP == 15))
				|| ((isIf == 0 && tempoVerbaleSP == 10) || (isIf == 0 && tempoVerbaleSP == 13)|| (isIf == 0 && tempoVerbaleSP == 15)))) {
				System.out.println("Il tipo di condizionale e': mixed conditional");
				conferma++;
			}
			//2 mixed
			if ((((isIf == 1 && tempoVerbaleSP == 2) || (isIf == 1 && tempoVerbaleSP == 5)) 
				|| ((isIf == 0&& tempoVerbalePP == 2) || (isIf == 0 && tempoVerbalePP == 5)))
				&& (((isIf == 1 && tempoVerbalePP == 11) || (isIf == 1 && tempoVerbalePP == 18) || (isIf == 1 && tempoVerbalePP == 19))
				|| ((isIf == 0&& tempoVerbaleSP == 11) || (isIf == 0 && tempoVerbaleSP == 18)|| (isIf == 0 && tempoVerbaleSP == 19)))){
				System.out.println("Il tipo di condizionale e': mixed conditional");
				conferma++;
			}
		}
		else if(conferma==0)
		{
			System.out.println("Errore frase inserita!");
		}
	}
}