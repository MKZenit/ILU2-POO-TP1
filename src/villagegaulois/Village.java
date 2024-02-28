package villagegaulois;
import java.util.Arrays;
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
				if(!etals[i].isEtalOccupe()) return i;
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalsTrouves = 0;
			for(int i=0; i<this.etals.length; i++) 
				if(this.etals[i].contientProduit(produit))
					nbEtalsTrouves += 1;
			
			Etal[] etalsTrouves = new Etal[nbEtalsTrouves];
			int iEtalTrouve = 0;
			for(int i=0; i<this.etals.length; i++)
				if(this.etals[i].contientProduit(produit)) {
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
			String result = "";
			int i;
			for(i=0; i<this.etals.length && this.etals[i]!=null; i++)
				result += this.etals[i].afficherEtal();
			
			return result + "Il reste " + (this.etals.length-i) + " étals non utilisés dans le marché.\n";
		}
		
	}

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