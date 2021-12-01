package champollion;

import java.util.ArrayList;


public class Enseignant extends Personne {
    
    private final ArrayList<ServicePrevu> myServicePrevu = new ArrayList<>();
    private final ArrayList<Intervention> myInterventions = new ArrayList<>();
    
    // TODO : rajouter les autres méthodes présentes dans le diagramme UML

    public Enseignant(String nom, String email) {
        super(nom, email);
    }


    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        // TODO: Implémenter cette méthode
        //throw new UnsupportedOperationException("Pas encore implémenté");
    
        int sommeHeure=0;
        for(ServicePrevu s : this.myServicePrevu){
        sommeHeure=(int) Math.round(sommeHeure+ (s.getVolumeCM()*1.5+s.getVolumeTD()*1+s.getVolumeTP()*0.75));
        }
        return sommeHeure;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {

        // TODO: Implémenter cette méthode

        int somme = 0;
        for (ServicePrevu service : myServicePrevu){
            if (service.getUE() == ue){
                somme = (int) Math.round(somme + (service.getVolumeTP()*0.75) + (service.getVolumeCM()*1.5) + service.getVolumeTD() );
            }
        }
        return somme;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        // TODO: Implémenter cette méthode
        myServicePrevu.add(new ServicePrevu(volumeCM,volumeTD,volumeTP,ue,this));
    }
    

    public boolean enSousService() throws Exception {
        int nbHeuresPrevues = heuresPrevues();
        double nbHeuresRealisees = 0;

        for (Intervention inter : myInterventions){
            nbHeuresRealisees = nbHeuresRealisees + inter.dureeEquivalentTD();
        }

        return nbHeuresPrevues-nbHeuresRealisees > 0;

    }
    
    public void ajouteIntervention(Intervention inter) throws Exception {
        if(inter.getIntervenant() == this){
            myInterventions.add(inter);
        }else{
            throw new Exception("Cette intervention ne correspond pas à cet enseignant");
        }
    }
    
public int resteAPlanifier(UE ue, TypeIntervention type) throws Exception {
        double sommeInter = 0;
        if (enSousService()){

            for(Intervention inter : myInterventions){
                if (inter.getType().equals(type) && inter.getMatiere().equals(ue)){
                    sommeInter = sommeInter + inter.dureeEquivalentTD();
                }
            }

            double sommeServ = 0;

            for (ServicePrevu servicePrevu : myServicePrevu) {
                if (servicePrevu.getUE().equals(ue)) {
                    switch (type) {
                        case TP:
                            sommeServ += servicePrevu.getVolumeTP()*0.75;
                            break;
                        case TD:
                            sommeServ += servicePrevu.getVolumeTD();
                            break;
                        case CM:
                            sommeServ += servicePrevu.getVolumeCM()*1.5;
                            break;
                    }
                }
            }

            sommeInter -= Math.round(sommeServ);
        }
        return (int) Math.abs(Math.round(sommeInter));
    }
   
    
    public ArrayList<ServicePrevu> getServicesPrevusList() {
        return myServicePrevu;
    }

    public ArrayList<Intervention> getInterventionsPlanifieesList() {
        return myInterventions;
    }    
    

}
