package service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.Manganelli_Naccarello.project.exceptions.FileNotFoundException;
import com.Manganelli_Naccarello.project.service.ServiceImpl;

class ServiceImplTest {
	
	private ServiceImpl service;
	
	@BeforeEach
	protected void setUp() throws Exception {
		service = new ServiceImpl();	
	}
	
	@AfterEach
	protected void tearDown() throws Exception {
	}
	
	/**Questo test verifica che la funzione salva operi correttamente il salvataggio.
	 * */
	@Test
    @DisplayName("SALVATAGGIO EFFETTUATO CORRETTAMENTE")
    void salvaTest() throws IOException {
    	
        String cityName = "Tokyo";
        String previsione = "PROVA";
      
        String path = System.getProperty("user.dir") + "/saves/" + cityName + "Call.txt";
		
        assertEquals(path,service.salva(previsione,path));
    }
	
	/**Questa funzione verifica che la funzione cercaStat vada ad estrarre correttamente la stringa di informazioni
	 * richiesta dalla stringa di previsioni. 
	 * */
	@Test
	@DisplayName("STAT TROVATA ED ESTRATTA CORRETTAMENTE")
	void cercaStatTest() {
		
		String prev ="\"humidity\":45},\"visibility\":10000,\"wind\":{\"speed\":4.02,\"deg\":267},\"clouds\":22232eeeeee:eede";
		assertEquals("\"wind\":{\"speed\":4.02,\"deg\":267}",service.cercaStat(prev, "wind", 125));
	}
	
	/**Questa funzione verifica che la funzione estraiStat vada ad estrarre correttamente il valore in double
	 * della statistica richiesta.
	 * */
	@Test 
	@DisplayName("STAT NUMERICA ESTRATTA CORRETTAMENTE")
	void estraiStatTest() {
		String wind = "\"wind\":{\"speed\":4.02,\"deg\":267})";
		double speed = 4.02;
		assertEquals(speed, service.estraiStat(wind, "speed"));
	}
}
