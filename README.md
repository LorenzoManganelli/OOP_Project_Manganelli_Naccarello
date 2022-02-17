# OOP_Project_Manganelli_Naccarello
## Controllo del vento
Questo programma, creato utilizzando java, permette di estrarre i dati del vento, divisi in velocità "speed", raffica "gust" e angolo "degree", dal'API OpenWeather. Inoltre salva questi dati su file, che a loro volta possono essere filtrati per i valori massimi, minimi e medi di tutte e tre le caratteristiche, scegliendo come range sia orari che date.
## Contenuti
1. Introduzione
2. Installazione
3. Rotte
4. Test (TBA)
5. Documentazione
6. Eccezioni
7. Struttura del progetto
## Introduzione
Il programma è capace di estrarre dall'API i dati sopra segnati e li salva su file, e stampa sulla console il path per poterlo trovare. A sua volta questo file può essere filtrato per max, min e media della speed, gust e deg (già pronte ci sono 4 città: *Ancona, Tokyo, Sydney e Helsinki*).

## Installazione
Il programma può essere scaricato inserendo `git clone https://github.com/LorenzoManganelli/OOP_Project_Manganelli_Naccarello.git`

## Rotte
Le rotte che l'utente può effettuare con l'utilizzo di Postman devono essere all'indirizzo: `localhost:8080`

| Tipo  | Rotta | Descrizione |
| ------------- | ------------- | ------------- |
| GET  | /wind?cityName="*città*" | Restituisce le informazioni attuali del vento e le salva su file|
| GET  | /filter?filterType="*filtro*"&cityName="*città*"&dataOraInizio="*inizio*"&dataOraFine="*fine*" | Filtra tutti i dati del file, con la possibilità di filtrare per il **massimo** (*max, Max, MAX*), il **minimo** (*min, Min, MIN*) e **media** (*med, Med, MED*) |
| GET  | /print?cityName="*città*" | Stampa tutto il file di quella città sotto forma di JSONArray |

Per effetuare le richieste basta avviare il programma come SpringBoot e utilizzare Postman nel modo seguente 

## /wind?cityName="*città*"
Semplicemente inserire una qualsiasi città esistente al posto di "*città*", e il Postman restituirà un JSONObject che ha i dati di speed, deg e gust e la data e ora corrente (in caso di errore sulla console verrà stampato un messaggio di errore)
![Screenshot (206)](https://user-images.githubusercontent.com/95304083/154550951-880b884b-c68d-41ae-b97d-d4a7aa9561e9.png)

## /filter?filterType="*filtro*"&cityName="*città*"&dataOraInizio="*inizio*"&dataOraFine="*fine*"
Inserire il tipo di filtro al posto di "*filtro*" (max, Max, MAX, min, Min, MIN, med, Med, MED), il nome dellà città al posto di "*città*" e la data di inizio e di fine che vuole essere analizzata (formato dd-MM-yyyy HH:mm:ss) al posto di "*inizio*" e "*fine*", restituendo alla fine un testo che mostra i dati richiesti con relative date e numero di dati analizzati.
![Screenshot (207)](https://user-images.githubusercontent.com/95304083/154553283-47363ff5-c4bd-4079-b6a4-918e7f0f5be0.png)
