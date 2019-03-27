package question2;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;

public class Pile2 implements PileI {
    /** par delegation : utilisation de la class Stack */
    private Stack<Object> stk;

    /** la capacite de la pile */
    private int capacite;
    private int taille;
    /**
     * Creation d'une pile.
     * 
     * @param taille
     *            la taille de la pile, la taille doit etre > 0
     */
    public Pile2(int taille) {
     if(taille <= 0) 
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = new Stack<Object>();
        this.capacite = taille;
        this.taille = 0;
    }

    // constructeur fourni
    public Pile2() {
       this(CAPACITE_PAR_DEFAUT);
    }

    public void empiler(Object o) throws PilePleineException {
       if(!estPleine()){
            stk.push(o);
            taille++;
        }
        else throw new PilePleineException();
    }

    public Object depiler() throws PileVideException {
       if(!estVide()){
            taille--;
            return stk.pop();
        }
        else throw new PileVideException();
    }

    public Object sommet() throws PileVideException {
       if(!estVide()){
            return stk.peek();
        }
        else throw new PileVideException();
    }

    /**
     * Effectue un test de l'etat de la pile.
     * 
     * @return vrai si la pile est vide, faux autrement
     */
    public boolean estVide() {
        // a completer
        return taille == 0;
    }

    /**
     * Effectue un test de l'etat de la pile.
     * 
     * @return vrai si la pile est pleine, faux autrement
     */
    public boolean estPleine() {
        // a completer
        return taille == capacite;
    }

    /**
     * Retourne une representation en String d'une pile, contenant la
     * representation en String de chaque element.
     * 
     * @return une representation en String d'une pile
     */
    public String toString() {
     Pile2 pileTemp = new Pile2(capacite());
        Object tempObject = new Object();
        String s = "[";
        while (!estVide()){
            try{ tempObject = depiler();
            } catch (PileVideException pve){pve.printStackTrace();}
            s += (tempObject==null) ? "null": tempObject.toString();
            try{pileTemp.empiler(tempObject);
            } catch (PilePleineException ppe){ppe.printStackTrace();}
            if(!estVide())
                s += ", ";
        }
        remplirPile(pileTemp, this);
        return s + "]";
    }
    
    private void remplirPile(PileI p1, PileI p2){
        while(!p1.estVide()){
            try{
                p2.empiler(p1.depiler());
            } catch (PileVideException pve){pve.printStackTrace();}
            catch (PilePleineException ppe){ppe.printStackTrace();}
        }
    }

    public boolean equals(Object o) {
       if(o == null) return false;
        
        if(!(o instanceof PileI))
            return false;
        PileI pile = (PileI)o;
        
        if(super.equals(o))
            return true;
        
        int capacite = this.capacite();
        int taille = this.taille();
        if(capacite != pile.capacite())
            return false;
        if(taille != pile.taille())
            return false;
            
        if(taille == 0) return true;
        
         Pile2 tempPile1 = new Pile2(taille);
        Pile2 tempPile2 = new Pile2(pile.taille());
        
         boolean elementsEgaux;
        
        while (!this.estVide() && !pile.estVide()){
            
            try{
                elementsEgaux = false;
                if(this.sommet() == null){
                    if(pile.sommet() == null) 
                        elementsEgaux = true;
                }        
                else if(pile.sommet() == null){
                    if(this.sommet() == null) 
                        elementsEgaux = true;
                }  
                else if(this.sommet().equals(pile.sommet())){
                    elementsEgaux = true;
                }
                
                if(elementsEgaux){tempPile1.empiler(this.depiler());tempPile2.empiler(pile.depiler());}
                else{ remplirPile(tempPile1, this);remplirPile(tempPile2, pile); return false; }
              } catch(PilePleineException ppe){ppe.printStackTrace();}
            catch(PileVideException pve){pve.printStackTrace();}
        }
        
       remplirPile(tempPile1, this);
        remplirPile(tempPile2, pile);
        
        return true;
    }

    // fonction fournie
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Retourne le nombre d'element d'une pile.
     * 
     * @return le nombre d'element
     */
    public int taille() {
        // a completer
       return this.taille;
    }

    /**
     * Retourne la capacite de cette pile.
     * 
     * @return le nombre d'element
     */
    public int capacite() {
        // a completer
        return this.capacite;
    }

} // Pile2.java
