public class Tournee {

    // a) Tri fusion sur place (ordre de compareTo)
    public static void trierFusion(Colis[] t) {
        if (t == null || t.length < 2) return;
        trierFusion(t, 0, t.length - 1);
    }

    private static void trierFusion(Colis[] t, int debut, int fin) {
        if (debut >= fin) return;
        int milieu = (debut + fin) / 2;
        trierFusion(t, debut, milieu);
        trierFusion(t, milieu + 1, fin);
        fusionner(t, debut, milieu, fin);
    }

    private static void fusionner(Colis[] t, int debut, int milieu, int fin) {
        Colis[] tmp = new Colis[fin - debut + 1];
        int i = debut, j = milieu + 1, k = 0;
        while (i <= milieu && j <= fin) {
            if (t[i].compareTo(t[j]) <= 0) tmp[k++] = t[i++];
            else tmp[k++] = t[j++];
        }
        while (i <= milieu) tmp[k++] = t[i++];
        while (j <= fin) tmp[k++] = t[j++];
        for (k = 0; k < tmp.length; k++) {
            t[debut + k] = tmp[k];
        }
    }

    // b) Chargement optimal (programmation dynamique, sac a dos 0/1)
    public static Colis[] chargementOptimal(Colis[] t, int capacite) {
        int n = t.length;
        long[][] meilleur = new long[n + 1][capacite + 1];

        for (int i = 1; i <= n; i++) {
            int poids = t[i - 1].getPoids();
            long tarif = t[i - 1].tarif();
            for (int cap = 0; cap <= capacite; cap++) {
                meilleur[i][cap] = meilleur[i - 1][cap];
                if (poids <= cap) {
                    long avec = meilleur[i - 1][cap - poids] + tarif;
                    if (avec > meilleur[i][cap]) {
                        meilleur[i][cap] = avec;
                    }
                }
            }
        }

        // Retrouver les colis choisis
        boolean[] pris = new boolean[n];
        int nb = 0;
        int cap = capacite;
        for (int i = n; i >= 1; i--) {
            if (meilleur[i][cap] != meilleur[i - 1][cap]) {
                pris[i - 1] = true;
                cap -= t[i - 1].getPoids();
                nb++;
            }
        }

        // Tableau de la taille exacte
        Colis[] resultat = new Colis[nb];
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (pris[i]) resultat[k++] = t[i];
        }
        return resultat;
    }

    // c) Programme principal
    public static void main(String[] args) {
        Colis[] lot = {
            new ColisStandard("C01", 4, false),
            new ColisExpress("C02", 3, true),
            new ColisFragile("C03", 5, false, 120_000),
            new ColisStandard("C04", 7, true),
            new ColisExpress("C05", 2, false),
            new ColisFragile("C06", 6, true, 45_000),
            new ColisStandard("C07", 1, true),
            new ColisExpress("C08", 8, false),
            new ColisFragile("C09", 3, false, 300_000),
            new ColisStandard("C10", 5, false)
        };

        trierFusion(lot);
        for (Colis c : lot) {
            System.out.println(c);
        }

        Colis[] choisis = chargementOptimal(lot, Parametres.CAPACITE);
        long recette = 0;
        for (Colis c : choisis) {
            recette += c.tarif();
        }
        System.out.println("Recette optimale : " + recette
                + " FCFA (capacite " + Parametres.CAPACITE + " kg)");
    }
}
