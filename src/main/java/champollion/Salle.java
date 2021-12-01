/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package champollion;

/**
 *
 * @author hikhl
 */
public class Salle {
    public  String intitule;
    public int capacite;
    
    
    public Salle(String intitule, int capacite){
    
        this.intitule=intitule;
        this.capacite=capacite;
    }
        
    public String getIntitule(){
        return intitule;
       
    }
    
    public int getCapacite(){
        return capacite;
    }
}
