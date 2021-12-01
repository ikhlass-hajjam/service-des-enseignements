package champollion;

import java.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class ChampollionJUnitTest {
	Enseignant untel;
	UE uml, java;
		
	@BeforeEach
	public void setUp() {
		untel = new Enseignant("untel", "untel@gmail.com");
		uml = new UE("UML");
		java = new UE("Programmation en java");		
	}
	

	@Test
	public void testNouvelEnseignantSansService() {
		assertEquals(0, untel.heuresPrevues(),
                        "Un nouvel enseignant doit avoir 0 heures prévues");
	}
	
	@Test
	public void testAjouteHeures() {
                // 10h TD pour UML
		untel.ajouteEnseignement(uml, 0, 10, 0);

		assertEquals(10, untel.heuresPrevuesPourUE(uml),
                        "L'enseignant doit maintenant avoir 10 heures prévues pour l'UE 'uml'");

                // 20h TD pour UML
                untel.ajouteEnseignement(uml, 0, 20, 0);
                
		assertEquals(10 + 20, untel.heuresPrevuesPourUE(uml),
                         "L'enseignant doit maintenant avoir 30 heures prévues pour l'UE 'uml'");		
		
	}
        
        @Test
        public void testAjouterIntervention() throws Exception{
        
            untel.ajouteIntervention(new Intervention(new Date(), 10, false,10, TypeIntervention.CM, new Salle("B019", 29), uml, untel));
       
            assertFalse(untel.getInterventionsPlanifieesList().isEmpty());
            //on verifie si ma liste est vide ou pas 
            
            Enseignant untel2 = new Enseignant("untel", "untel@gmail.com");

            Exception exception = assertThrows(Exception.class, () -> {
                untel.ajouteIntervention(new Intervention(new Date(), 3, false, 8, TypeIntervention.TP, new Salle("B019", 35), java, untel2));
            });
        
        }
        
        @Test
        public void testAjouteEnseignement(){
            untel.ajouteEnseignement(java,0,0,0);

            assertFalse(untel.getServicesPrevusList().isEmpty());
        }
        

        
        @Test
    public void testEnSousService() throws Exception {
        Salle salle19 = new Salle("B019", 35);

        untel.ajouteEnseignement(java, 0, 5, 10);
        untel.ajouteEnseignement(uml, 10, 0, 0);

        untel.ajouteIntervention(new Intervention(new Date(), 10, false, 9, TypeIntervention.TP, salle19,java, untel ));
        untel.ajouteIntervention(new Intervention(new Date(), 10, false, 9, TypeIntervention.CM, salle19,uml, untel ));

        assertTrue(untel.enSousService());

        untel.ajouteIntervention(new Intervention(new Date(), 5, false, 9, TypeIntervention.TD, salle19,java, untel ));

        assertFalse(untel.enSousService());

    }
    
    @Test
    public void testResteAPlanifier() throws Exception {
        Salle salle19 = new Salle("B019", 35);

        untel.ajouteEnseignement(java, 0, 5, 10);
        untel.ajouteEnseignement(uml, 10, 0, 30);

        untel.ajouteIntervention(new Intervention(new Date(), 10, false, 9, TypeIntervention.TP, salle19,java, untel ));
        untel.ajouteIntervention(new Intervention(new Date(), 10, false, 9, TypeIntervention.CM, salle19,uml, untel ));

        System.out.println(untel.resteAPlanifier(java, TypeIntervention.TD));
        System.out.println(untel.resteAPlanifier(uml, TypeIntervention.TP));
        
        
        assertEquals(5, untel.resteAPlanifier(java, TypeIntervention.TD));
        assertEquals(23, untel.resteAPlanifier(uml, TypeIntervention.TP));
    }
    	
}
