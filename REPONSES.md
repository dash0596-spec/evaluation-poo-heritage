# Évaluation POO - Héritage

## Exercice 1

### A. Prédiction (N = 20)

    A vehicule
    F null 0
    E moto 120
    Moto2
    vehicule
    moto
    C3
    C2
    D-vehicule
    A vehicule
    F null 0
    E moto 120
    true false
    true
    A vehicule
    B vehicule
    A vehicule
    B vehicule
    4

### Justifications

1. **Ligne « F null 0 »** : le constructeur de Vehicule appelle demarrer(), redéfinie dans Moto, donc la version de Moto s'exécute (liaison dynamique). Mais les champs de Moto ne sont pas encore initialisés à ce moment : nom vaut null et cylindree vaut 0.
2. **« C3 »** : le type déclaré de v est Vehicule, donc le compilateur choisit comparer(Vehicule). À l'exécution, l'objet est un Moto qui redéfinit cette méthode, d'où C3.
3. **« C2 »** : avec le cast (Moto) v, la surcharge la plus spécifique est comparer(Moto). Moto ne la redéfinit pas, donc c'est celle de Vehicule qui répond.
4. **« D-vehicule »** : categorie() est static, donc il n'y a pas de polymorphisme. La méthode est choisie selon le type déclaré (Vehicule), pas selon l'objet réel.
5. **Ordre autour de « true false »** : l'expression o.equals(v) + " " + (o == new Moto()) doit être évaluée en entier avant que println affiche quoi que ce soit. Le second new Moto() affiche donc A vehicule, F null 0, E moto 120 avant « true false ». Ce nouvel objet est distinct de o, d'où false.

Le compteur final vaut 4 : 2 Moto (new Moto() de départ et celui de la comparaison) + 2 Vehicule (20 % 3 = 2 tours de boucle).

### B. Transtypage

| n° | Résultat | Pourquoi |
|---|---|---|
| 1 | OK | c est un Moto, donc un Vehicule (upcast implicite). |
| 2 | Erreur de compilation | a est déclaré Vehicule : affectation à un Moto impossible sans cast. |
| 3 | OK | Cast accepté, et l'objet réel est bien un Moto. |
| 4 | ClassCastException | Compile, mais l'objet réel est un Camion, pas un Moto. |
| 5 | Erreur de compilation | Moto et Camion n'ont aucun lien d'héritage : cast impossible. |
| 6 | ClassCastException | Compile (String est un Object), mais l'objet réel est un Camion. |
| 7 | Erreur de compilation | b est déclaré Object, qui n'a pas de méthode type(). |
| 8 | OK ("Vehicule") | Cast valide car Camion hérite de Vehicule ; Camion ne redéfinit pas type(), donc celle de Vehicule s'exécute. |


