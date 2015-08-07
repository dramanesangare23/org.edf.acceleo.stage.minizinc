package org.edf.acceleo.stage.minizinc.services;

/**
 * 
 * @author G10076
 *
 *Documentation of the class MiniZincServices
 *C'est la classe de services. C'est dans cette classe que nous allons definir nos services java pour
 *le code acceleo. Les m�thodes java deviennent des queries acceleo.
 */

import java.util.ArrayList;
import java.util.List;
import org.eclipse.uml2.uml.Class;

public class MiniZincServices {
	
	/**
	 * Documentation of the operation contientSigneComparaison
	 * Cette methode retourne true si une chaine de caracteres passee en argument (exp) contient un
	 * comparateur logique (<, >, <=, >=, <> ou =)
	*/
	public boolean contientSigneComparaison(Class clazz, String exp){
		return exp.contains("<>") || exp.contains("<=") || exp.contains(">=") || exp.contains("<") || exp.contains(">") || exp.contains("=");
	}

	/**
	 * Documentation of the operation contientSigneOperation
	 * Cette methode retourne true si une chaine de caracteres passee en argument (exp) contient un
	 * operateur arithmetique (+, -, * ou /)
	*/
	public boolean contientSigneOperation(Class clazz, String exp){
		if(exp.contains("+") || exp.contains("-") || exp.contains("*") || exp.contains("/"))
			return true;
		return false;
	}

	/**
	  * Documentation of the operation contientOperateurLogique
	  * Cette methode retourne true si une chaine de caracteres passee en argument (exp) contient un
	  * operateur logique (and, or, xor ou implies)
	 */
	public boolean contientOperateurLogique(Class clazz, String exp){
		return exp.contains(" and ") || exp.contains(" xor ") || exp.contains(" or ") || 
				exp.contains(" implies ");
	}

	/**
	 * Documentation of the operation getSigneComparaison
	 * Cette methode retourne le comparateur logique contenu dans une chaine de caracteres passee en
	 * argument (exp) sinon "null".
	*/
	public String getSigneComparaison(Class clazz, String exp){
		String [] signes = {"<>", "<=", ">=", "<", ">", "="};
		String signe = "nullComparaison";
		int i = 0;
		for(i = 0; i < signes.length; i++){
			if(exp.contains(signes[i])){
				signe = signes[i];
				break;
			}
		}
		return signe;
	}

	/**
	 * Documentation of the operation getSigneOperation
	 * Cette methode retourne l'operateur arithmetique contenu dans une chaine de caracteres passee en 
	 * argument (exp) sinon "null".
	*/
	public String getSigneOperation(Class clazz, String exp){
		if(!contientSigneOperation(clazz, exp))
			return "noOperationSigne";
		int i = 0;
		while(i < exp.length()){
			if(exp.charAt(i) == '+' || exp.charAt(i) == '-' || exp.charAt(i) == '*' || exp.charAt(i) == '/'){
				return ""+exp.charAt(i);
			}
			i++;
		}
		return "noOperationSigne";
	}

	/**
	 * Documentation of the operation getSignesOperation
	 * Cette methode retourne les operateurs arithmetiques contenus dans une chaine de caracteres 
	 * passee en argument (exp) sinon "null".
	 */
	public List<String> getSignesOperation(Class clazz, String exp){
		String signe;
		List<String> signesOperation = new ArrayList<String>();
		String expCopie = exp;
		while((contientSigneOperation(clazz, expCopie)) && !(expCopie.equals(""))){
			signe = getSigneOperation(clazz, expCopie);
			signesOperation.add(signe);
			expCopie = expCopie.substring(0, expCopie.indexOf(signe)) + 
					expCopie.substring(expCopie.indexOf(signe)+1, expCopie.length());
		}
		return signesOperation;
	}

	/**
	 * Documentation of the operation nombreDeSignesOperation
	 * Cette methode retourne le nombre de signes d'operateurs arithmetiques contenus dans une 
	 * chaine de caracteres passee en argument (exp).
	*/
	public int nombreDeSignesOperation(Class clazz, String exp){
		return getSignesOperation(clazz, exp).size();
	}

	/**
	 * Documentation of the operation getOperateurLogique
	 * Cette methode retourne l'operateur logique contenu dans une chaine de caracteres passee en 
	 * argument (arg1) sinon "null".
	*/
	public String getOperateurLogique(Class clazz, String exp){
		String [] signes = {" and ", " xor ", " or ", " implies "};
		String signe = "nullOperateurLogique";
		int i = 0;
		for(i = 0; i < signes.length; i++){
			if(exp.contains(signes[i])){
				signe = signes[i];
				break;
			}
		}
		return signe;
	}

	/**
	 * Documentation of the operation getPartieInteressante
	 * Elle comporte trois (3) parametres successivement arg1, arg2 et arg3. Son role est de :
	 * ****considerer arg1 s'il est different de arg3, sinon arg2. Ensuite elle retranche de 
	 * ****l'expression consideree la chaine de caracteres "xor" et retourne la chaine restante.
	 * ****Cette procedure lui permet d'analyser la deuxieme contrainte ocl de la classe Tournee afin
	 * ****d'en extraire la partie qui lui interesse.
	*/
	public String getPartieInteressante(Class clazz, String arg1, String arg2, String arg3){
		String partieInteressante =  (arg1.equalsIgnoreCase(arg3)) ? arg2.trim() : arg1.trim();
		return (partieInteressante.substring(0, 3).equalsIgnoreCase("xor")) ?
				partieInteressante.substring(3, partieInteressante.length()) :
					partieInteressante.substring(0, 3);
	}

}
