package villagegaulois;
import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}
	
	private static class Marche{
		private Etal[] etals;
		
		public Marche(int nbEtal) {
			etals = new Etal[nbEtal];
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for(int i=0; i<etals.length; i++) {
				if(this.etals[i]!=null && !etals[i].isEtalOccupe()) return i;
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalsTrouves = 0;
			for(int i=0; i<this.etals.length; i++) 
				if(this.etals[i]!=null && this.etals[i].contientProduit(produit))
					nbEtalsTrouves += 1;
			
			Etal[] etalsTrouves = new Etal[nbEtalsTrouves];
			int iEtalTrouve = 0;
			for(int i=0; i<this.etals.length; i++)
				if(this.etals[i]!=null && this.etals[i].contientProduit(produit)) {
					etalsTrouves[iEtalTrouve] = this.etals[i];
					iEtalTrouve += 1;
				}
		
			return etalsTrouves;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<this.etals.length; i++) {
				if(this.etals[i].getVendeur()==gaulois) {
					return this.etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder result = new StringBuilder();
			int i;
			for(i=0; i<this.etals.length && this.etals[i]!=null; i++)
				result.append(this.etals[i].afficherEtal());
			
			return result.toString() + "Il reste " + (this.etals.length-i) + " étals non utilisés dans le marché.\n";
		}
		
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder result = new StringBuilder();
		result.append(vendeur.getNom());
		result.append(" cherche un endroit pour vendre ");
		result.append(nbProduit);
		result.append(" ");
		result.append(produit);
		result.append("\n");

		int iEtalLibre = this.marche.trouverEtalLibre();
		if(iEtalLibre>=0) {
			this.marche.utiliserEtal(iEtalLibre, vendeur, produit, nbProduit);
			result.append("Le vendeur");
			result.append(vendeur.getNom());
			result.append("vend des fleurs à l'étal n°");
			result.append(iEtalLibre);
			result.append(".\n");
		}else {
			result.append("Le vendeur ");
			result.append(vendeur.getNom());
			result.append("n'a pas trouvé d'étal pour vendre ses ");
			result.append(produit);
			result.append(".\n");
		}

		return result.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsVendeurs = this.marche.trouverEtals(produit);
		StringBuilder result = new StringBuilder();
		
		if(etalsVendeurs.length>0) {
			result.append("Les vendeurs qui proposent des ");
			result.append(produit);
			result.append(" sont :\n");
			for(int i=0; i<etalsVendeurs.length; i++) {
				result.append("- ");
				result.append(etalsVendeurs[i].getVendeur().getNom());
				result.append("\n");
			}
		}else {
			result.append("Personne ne vend de ");
			result.append(produit);
			result.append(".");
		}
		
		return result.toString();
	}

	//public Etal rechercherEtal()
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}