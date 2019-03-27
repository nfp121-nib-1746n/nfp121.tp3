package question2;
import java.util.Stack;
import question1.PilePleineException;
import question1.PileVideException;

public class Pile4 implements PileI, Cloneable {
    /** la liste des Maillons/Elements */
    private Maillon stk;
    /** la capacité de la pile */
    private int capacite;
    /** le nombre */
    private int nombre;

    /**
     * Classe interne "statique" contenant chaque élément de la chaine c'est une
     * proposition, vous pouvez l'ignorer !
     */
    private static class Maillon implements Cloneable {
        private Object element;
        private Maillon suivant;

        public Maillon(Object element, Maillon suivant) {
            this.element = element;
            this.suivant = suivant;
        }

        public Maillon suivant() {
            return this.suivant;
        }

        public Object element() {
            return this.element;
        }

        public Object clone() throws CloneNotSupportedException {
            Maillon m = (Maillon) super.clone();
            m.element = element;
            return m;
        }
    }

    /**
     * Création d'une pile.
     * 
     * @param taille
     *            la taille de la pile, la taille doit être > 0
     */
    public Pile4(int taille) {
        if (taille <= 0)
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = null;
        this.capacite = taille;
        this.nombre = 0;
    }

    public Pile4() {
            this(PileI.CAPACITE_PAR_DEFAUT);
    }

    public void empiler(Object o) throws PilePleineException {
        if (estPleine())
            throw new PilePleineException();
        Maillon maillon = new Maillon(o, stk);
        stk = maillon;
        nombre++;
    }

    public Object depiler() throws PileVideException {
        if (estVide())
            throw new PileVideException();
        Object object = stk.element();
        stk = stk.suivant();
        nombre--;
        return object;
    }

    public Object sommet() throws PileVideException {
        if (estVide())
            throw new PileVideException();
        return stk.element();
    }

    /**
     * Effectue un test de l'état de la pile.
     * 
     * @return vrai si la pile est vide, faux autrement
     */
    public boolean estVide() {
        return stk == null;
    }

    /**
     * Effectue un test de l'état de la pile.
     * 
     * @return vrai si la pile est pleine, faux autrement
     */
    public boolean estPleine() {
        return nombre == capacite;
    }

    /**
     * Retourne une représentation en String d'une pile, contenant la
     * représentation en String de chaque élément.
     * 
     * @return une représentation en String d'une pile
     */
    public String toString() {
        Maillon maillonInitial = stk;
        String s = "[";
        while (stk != null){
            s += (stk.element()==null)? "null":stk.element().toString();
            stk = stk.suivant();
            if(stk!=null) s+=", ";    
        }
        stk = maillonInitial;
        return s + "]";
    }

    public boolean equals(Object object){
        if(object == null) return false;
         if(!(object instanceof PileI))
            return false;
        PileI pile = (PileI)object;
        if(super.equals(object))
            return true;
        int capacite = this.capacite();
        int taille = this.taille();
        if(capacite != pile.capacite())
            return false;
        if(taille != pile.taille())
            return false;
        if(taille == 0) return true;
        Pile4 tempPile2 = new Pile4(pile.taille());
        Maillon maillonInitial = stk;
        
        boolean elementEgaux;
        while (stk!=null && !pile.estVide()){
            try{
                elementEgaux = false;
                if(this.sommet() == null){
                    if(pile.sommet() == null) {
                        elementEgaux = true;
                    }
                }
                else if(pile.sommet() == null){
                    if(this.sommet() == null) {
                        elementEgaux = true;
                    }
                }
                else if(stk.element().equals(pile.sommet())){
                    elementEgaux = true;
                }
                if(elementEgaux){
                    stk = stk.suivant();
                    tempPile2.empiler(pile.depiler());
                }
                else {
                    stk = maillonInitial;
                    remplirPile(tempPile2, pile);
                    return false;
                }
            } catch(PilePleineException ppe){ppe.printStackTrace();}
            catch(PileVideException pve){pve.printStackTrace();}
        }
        
         stk = maillonInitial;
        remplirPile(tempPile2, pile);
        
        return true;
    }
    
    private void remplirPile(PileI p1, PileI p2){
        while(!p1.estVide()){
            try{
                p2.empiler(p1.depiler());
            } catch (PileVideException pve){pve.printStackTrace();}
            catch (PilePleineException ppe){ppe.printStackTrace();}
        }
    }

    public int capacite() {
        return this.capacite;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public int taille() {
        return nombre;
    }
    
}