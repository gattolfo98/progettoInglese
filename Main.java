/**
 * Project to classificate conditional phrases
 * 
 * @author Gabriele Triglia, Federico Biasioli
 * @version 1.2
 */
 import java.io.*;
import java.util.Scanner;
public class Main {
    static String primoPeriodo = "";
    static String secondoPeriodo = "";
	static int tempoPrimoPeriodo = 0;
    static int tempoSecondoPeriodo = 0;
	static String presentSimple[] = new String[1000];
	static String pastSimple[] = new String[1000];
	static String pastParticiple[] = new String[1000];
	static String terzaColonna[] = new String[1000];
	static String ingForm[] = new String[1000];

    public static void main(String[] args) {
        System.out.println("\t\tConditional phrases control program");
        /*
         * frasi usate:
         * If it hadn't been for you, I wouldn't be here now.
         * I'm afraid of flying. If I wasn't afraid of flying we'd have travelled by plane.
         */
        String frase = "done if do";
        phraseSplit(frase);
		creazioneVettori();
		Rumbling();
    }

    private static void phraseSplit(String frase) {
        int valore = 0;
        int valorePrimo = 0;
        int indice = 0;

        String parolaDaCercare = "if";

        String lineaFile = frase.toLowerCase();
        // assegnamento del valore precedente all'inizio della parola
        if (lineaFile.indexOf(parolaDaCercare) == 0) {
            valorePrimo = 0;
        } else {
            valorePrimo = lineaFile.indexOf(parolaDaCercare) - 1;
        }
        // assegnamento del valore che corrisponde al carattere successivo della parola
        valore = lineaFile.indexOf(parolaDaCercare) + (parolaDaCercare.length());
        if (lineaFile.contains(parolaDaCercare)) {

            // controllo per capire se è una sottostringa o una vera e propria parola
            if ((lineaFile.charAt(valore) < 65 || lineaFile.charAt(valore) > 90)
                    && (lineaFile.charAt(valore) < 97 || lineaFile.charAt(valore) > 122)
                    && (lineaFile.charAt(valorePrimo) < 65 || lineaFile.charAt(valorePrimo) > 90
                            || lineaFile.charAt(valorePrimo) == parolaDaCercare.charAt(0))
                    && (lineaFile.charAt(valorePrimo) < 97 || lineaFile.charAt(valorePrimo) > 122
                            || lineaFile.charAt(valorePrimo) == parolaDaCercare.charAt(0))) {
                indice = lineaFile.indexOf(parolaDaCercare);
            }
        }
        System.out.println("Indice di if: " + indice);

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

        if (indice2 != -1 && indice2 > indice) {
            secondoPeriodo = lineaFile.substring(indice2 + 1);
            primoPeriodo = lineaFile.substring(0, indice2 + 1);
            System.out.println(primoPeriodo);
            System.out.println(secondoPeriodo);
        }

        if (indice2 < indice) {
            secondoPeriodo = lineaFile.substring(indice);
            primoPeriodo = lineaFile.substring(0, indice);
            System.out.println(primoPeriodo);
            System.out.println(secondoPeriodo);
        }
    }
	private static void creazioneVettori()
	{
		String nomeFile = "English_verb.CSV";
		String nomeCartella = "Database";
		String percorsoFile = nomeCartella + File.separator + nomeFile;
		String lineaFile = "";
		int i = 0;
		int indice = 0;
		int indice2 = 0;
		File file = new File(percorsoFile);
		try
		{
			Scanner sc = new Scanner(file);
				while(sc.hasNextLine())
				{
					lineaFile = sc.nextLine().trim();
					indice = lineaFile.indexOf(";");
					presentSimple[i] = lineaFile.substring(0,indice);
					lineaFile = lineaFile.substring(indice+1);
					indice = lineaFile.indexOf(";");
					pastSimple[i] = lineaFile.substring(0,indice);
					lineaFile = lineaFile.substring(indice+1);
					indice = lineaFile.indexOf(";");
					pastParticiple[i] = lineaFile.substring(0,indice);
					lineaFile = lineaFile.substring(indice+1);
					indice = lineaFile.indexOf(";");
					terzaColonna[i] = lineaFile.substring(0,indice);
					lineaFile = lineaFile.substring(indice+1);
					indice = lineaFile.indexOf(";");
					ingForm[i] = lineaFile.substring(0,indice);
					i++;
				}
				i=0;
		}
		catch(Exception e)
		{
			System.out.println("Tipo di eccezione --> " + e.toString());
		}
	}
	private static void Rumbling()
	{
		int i = 0;
		int valore = 0;
		int valorePrimo = 0;
		for(i=0;i<999;i++)
		{
			if(primoPeriodo.contains(presentSimple[i]))
			{
				if (primoPeriodo.indexOf(presentSimple[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = primoPeriodo.indexOf(presentSimple[i]) - 1;
				}
				valore = primoPeriodo.indexOf(presentSimple[i]) + (presentSimple[i].length());
				if(valore<primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
					&& (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
					&& (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
					|| primoPeriodo.charAt(valorePrimo) == presentSimple[i].charAt(0))
					&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
					|| primoPeriodo.charAt(valorePrimo) == presentSimple[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 1;
						System.out.println("Il tempo verbale del primo periodo e' il Present Simple.");
						break;			
					}
				}
				if(valore==primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
					|| primoPeriodo.charAt(valorePrimo) == presentSimple[i].charAt(0))
					&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
					|| primoPeriodo.charAt(valorePrimo) == presentSimple[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 1;
						System.out.println("Il tempo verbale del primo periodo e' il Present Simple.");
						break;			
					}
				}				
			}
			if(primoPeriodo.contains(pastSimple[i]))
			{
				if (primoPeriodo.indexOf(pastSimple[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = primoPeriodo.indexOf(pastSimple[i]) - 1;
				}
				valore = primoPeriodo.indexOf(pastSimple[i]) + (pastSimple[i].length());
				if(valore<primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
                    && (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
                    && (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
                    || primoPeriodo.charAt(valorePrimo) == pastSimple[i].charAt(0))
                    && (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
                    || primoPeriodo.charAt(valorePrimo) == pastSimple[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 2;
						System.out.println("Il tempo verbale del primo periodo e' il Past Simple.");
						break;		
					}
				}
				if(valore==primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
					|| primoPeriodo.charAt(valorePrimo) == pastSimple[i].charAt(0))
					&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
					|| primoPeriodo.charAt(valorePrimo) == pastSimple[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 2;
						System.out.println("Il tempo verbale del primo periodo e' il Past Simple.");
						break;				
					}
				}				
			}
			if(primoPeriodo.contains(pastParticiple[i]))
			{
				if (primoPeriodo.indexOf(pastParticiple[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = primoPeriodo.indexOf(pastParticiple[i]) - 1;
				}
				valore = primoPeriodo.indexOf(pastParticiple[i]) + (pastParticiple[i].length());
				if(valore<primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
                    && (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
                    && (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
                    || primoPeriodo.charAt(valorePrimo) == pastParticiple[i].charAt(0))
                    && (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
                    || primoPeriodo.charAt(valorePrimo) == pastParticiple[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 3;
						System.out.println("Il tempo verbale del primo periodo e' il Past Participle.");
						break;	
					}
				}
				if(valore==primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
					|| primoPeriodo.charAt(valorePrimo) == pastParticiple[i].charAt(0))
					&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
					|| primoPeriodo.charAt(valorePrimo) == pastParticiple[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 3;
						System.out.println("Il tempo verbale del primo periodo e' il Past Participle.");
						break;					
					}
				}				
			}
			if(primoPeriodo.contains(terzaColonna[i]))
			{
				if (primoPeriodo.indexOf(terzaColonna[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = primoPeriodo.indexOf(terzaColonna[i]) - 1;
				}
				valore = primoPeriodo.indexOf(terzaColonna[i]) + (terzaColonna[i].length());
				if(valore<primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
                    && (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
                    && (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
                    || primoPeriodo.charAt(valorePrimo) == terzaColonna[i].charAt(0))
                    && (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
                    || primoPeriodo.charAt(valorePrimo) == terzaColonna[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 4;
						System.out.println("Il tempo verbale del primo periodo e' della terza persona");
						break;
					}	
				}
				if(valore==primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
					|| primoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))
					&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
					|| primoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 4;
						System.out.println("Il tempo verbale del primo periodo e' della terza persona");
						break;					
					}
				}				
			}
			if(primoPeriodo.contains(ingForm[i]))
			{
				if (primoPeriodo.indexOf(ingForm[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = primoPeriodo.indexOf(ingForm[i]) - 1;
				}
				valore = primoPeriodo.indexOf(ingForm[i]) + (ingForm[i].length());
				if(valore<primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valore) < 65 || primoPeriodo.charAt(valore) > 90)
                    && (primoPeriodo.charAt(valore) < 97 || primoPeriodo.charAt(valore) > 122)
                    && (primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
                    || primoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))
                    && (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
                    || primoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 5;
						System.out.println("Il tempo verbale del primo periodo e' in forma ing.");
						break;		
					}
				}
				if(valore==primoPeriodo.length())
				{
					if ((primoPeriodo.charAt(valorePrimo) < 65 || primoPeriodo.charAt(valorePrimo) > 90
					|| primoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))
					&& (primoPeriodo.charAt(valorePrimo) < 97 || primoPeriodo.charAt(valorePrimo) > 122
					|| primoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))) 
					{
						tempoPrimoPeriodo = 5;
						System.out.println("Il tempo verbale del primo periodo e' in forma ing.");
						break;					
					}
				}				
			}
		}
		for(i=0;i<999;i++)
		{
			if(secondoPeriodo.contains(presentSimple[i]))
			{
				if (secondoPeriodo.indexOf(presentSimple[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = secondoPeriodo.indexOf(presentSimple[i]) - 1;
				}
				valore = secondoPeriodo.indexOf(presentSimple[i]) + (presentSimple[i].length());
				if(valore<secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
                    && (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
                    && (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
                    || secondoPeriodo.charAt(valorePrimo) == presentSimple[i].charAt(0))
                    && (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
                    || secondoPeriodo.charAt(valorePrimo) == presentSimple[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 1;
						System.out.println("Il tempo verbale del secondo periodo e' il Present Simple.");
						break;			
					}
				}
				if(valore==secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
					|| secondoPeriodo.charAt(valorePrimo) == presentSimple[i].charAt(0))
					&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
					|| secondoPeriodo.charAt(valorePrimo) == presentSimple[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 1;
						System.out.println("Il tempo verbale del secondo periodo e' il Present Simple.");
						break;					
					}
				}
			}
			if(secondoPeriodo.contains(pastSimple[i]))
			{
				if (secondoPeriodo.indexOf(pastSimple[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = secondoPeriodo.indexOf(pastSimple[i]) - 1;
				}
				valore = secondoPeriodo.indexOf(pastSimple[i]) + (pastSimple[i].length());
				if(valore<secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
                    && (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
                    && (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
                    || secondoPeriodo.charAt(valorePrimo) == pastSimple[i].charAt(0))
                    && (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
                    || secondoPeriodo.charAt(valorePrimo) == pastSimple[i].charAt(0))) 
					{
					tempoSecondoPeriodo = 2;
					System.out.println("Il tempo verbale del secondo periodo e' il Past Simple.");
					break;				
					}
				}
				if(valore==secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
					|| secondoPeriodo.charAt(valorePrimo) == pastSimple[i].charAt(0))
					&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
					|| secondoPeriodo.charAt(valorePrimo) == pastSimple[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 2;
						System.out.println("Il tempo verbale del secondo periodo e' il Past Simple.");
						break;					
					}
				}				
			}
			if(secondoPeriodo.contains(pastParticiple[i]))
			{
				if (secondoPeriodo.indexOf(pastParticiple[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = secondoPeriodo.indexOf(pastParticiple[i]) - 1;
				}
				valore = secondoPeriodo.indexOf(pastParticiple[i]) + (pastParticiple[i].length());
				if(valore<secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
					&& (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
					&& (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
					|| secondoPeriodo.charAt(valorePrimo) == pastParticiple[i].charAt(0))
					&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
					|| secondoPeriodo.charAt(valorePrimo) == pastParticiple[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 3;
						System.out.println("Il tempo verbale del secondo periodo e' il Past Participle.");
						break;					
					}
				}
				if(valore==secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
					|| secondoPeriodo.charAt(valorePrimo) == pastParticiple[i].charAt(0))
					&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
					|| secondoPeriodo.charAt(valorePrimo) == pastParticiple[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 3;
						System.out.println("Il tempo verbale del secondo periodo e' il Past Participle.");
						break;					
					}
				}					
			}
			if(secondoPeriodo.contains(terzaColonna[i]))
			{
				if (secondoPeriodo.indexOf(terzaColonna[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = secondoPeriodo.indexOf(terzaColonna[i]) - 1;
				}
				valore = secondoPeriodo.indexOf(terzaColonna[i]) + (terzaColonna[i].length());
				if(valore<secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
                    && (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
                    && (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
                    || secondoPeriodo.charAt(valorePrimo) == terzaColonna[i].charAt(0))
                    && (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
                    || secondoPeriodo.charAt(valorePrimo) == terzaColonna[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 4;
						System.out.println("Il tempo verbale del secondo periodo e' della terza persona.");
						break;						
					}						
				}
				if(valore==secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
					|| secondoPeriodo.charAt(valorePrimo) == terzaColonna[i].charAt(0))
					&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
					|| secondoPeriodo.charAt(valorePrimo) == terzaColonna[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 4;
						System.out.println("Il tempo verbale del secondo periodo e' della terza persona.");
						break;					
					}
				}
			}
			if(secondoPeriodo.contains(ingForm[i]))
			{
				if (secondoPeriodo.indexOf(ingForm[i]) == 0) {
				valorePrimo = 0;
				} else {
				valorePrimo = secondoPeriodo.indexOf(ingForm[i]) - 1;
				}
				valore = secondoPeriodo.indexOf(ingForm[i]) + (ingForm[i].length());
				if(valore<secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valore) < 65 || secondoPeriodo.charAt(valore) > 90)
                    && (secondoPeriodo.charAt(valore) < 97 || secondoPeriodo.charAt(valore) > 122)
                    && (secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
                    || secondoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))
                    && (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
                    || secondoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 5;
						System.out.println("Il tempo verbale del secondo periodo e' in forma ing.");
						break;						
					}
				}
				if(valore==secondoPeriodo.length())
				{
					if ((secondoPeriodo.charAt(valorePrimo) < 65 || secondoPeriodo.charAt(valorePrimo) > 90
					|| secondoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))
					&& (secondoPeriodo.charAt(valorePrimo) < 97 || secondoPeriodo.charAt(valorePrimo) > 122
					|| secondoPeriodo.charAt(valorePrimo) == ingForm[i].charAt(0))) 
					{
						tempoSecondoPeriodo = 5;
						System.out.println("Il tempo verbale del secondo periodo e' in forma ing.");
						break;					
					}
				}				
			}
		}
	}
}