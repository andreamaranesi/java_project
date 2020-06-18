# INSTAGRAM API

Il progetto ha come obiettivo quello di fornire un ampio numero di filtri e statistiche (in JSON e successivamente in formato HTML) relativi ai post di uno o più utenti Instagram.

Il programma è in grado di recuperare informazioni relativamente a

- dimensioni dei post ( e sui figli di un album ) in MB e KB
- altezza e larghezza (in px) delle immagini
- hashtag
- follower



## INDICE CONTENUTI

<ul>
    <li>UML<ul>
        <li><a href="#uml_usecase">Diagramma dei casi d'uso</a></li>
        <li><a href="#uml_classi">Diagramma delle classi</a></li>
        <li><a href="#uml_sequenze">Diagramma delle sequenze</a></li>
        </ul></li>
    <li>Introduzione<ul>
         <li><a href="#config">config.json</a></li>
    <li><a href="#notabene">Nota bene</a></li>
        <li><a href="#file_locali">File Locali</a></li>
        </ul>
    </li>
    <li><a href="#chiamate">CHIAMATE</a><ul>
         <li><a href="#parametri">Parametri</a></li>
    <li><a href="#risposta_filtri">Risposta filtri</a></li>
        <li><a href="#risposta_statistiche">Risposta statistiche</a></li>
        <li><a href="#uso_parametri">Utilizzo parametri</a><ul>
         <li><a href="#esempi_parametri">Esempi</a></li>
        </ul>
    </li>
        <li><a href="#body_chiamate">Body</a><ul>
         <li><a href="#uso_filtri">filtri</a></li>
            <li><a href="#uso_statistiche">statistiche</a>
            </li>
    </ul>
</li>
    </ul>
    <ul>
    <li href="#esempi_chiamate">Esempi chiamate</li>
        <ul>
            <li><a href="#esempi_filtri">/filtri</a></li>
            <li><a href="#esempi_statistiche">/statistiche</a></li>
        </ul>
</ul>
    </li>
 <li><a href="#web">WEB (<b>in Via di Sviluppo</b>)</a><ul>
</ul>






## <div id="uml_usecase">DIAGRAMMA DEI CASI D'USO </div>



![](https://github.com/andreamaranesi/java_project/raw/master/instagram_api/uml/usecase.jpg)





## <div id="uml_classi">DIAGRAMMA DELLE CLASSI</div>





<img src="https://raw.githubusercontent.com/andreamaranesi/java_project/master/instagram_api/uml/diagramma_classi.jpg" style="zoom:200%;" />





## <div id="uml_sequenze">DIAGRAMMA DELLE SEQUENZE</div>





![](https://github.com/andreamaranesi/java_project/raw/master/instagram_api/uml/sequenze.jpg)



## INTRODUZIONE

All'interno dell'applicazione sono presenti dei file locali JSON dai quali è possibile leggere dei dati di default.

#### CONFIG.JSON

Dal file locale **config.json** troviamo i filtri di default e gli **access_token** dei vari utenti

## <div id="config">CONFIG.JSON</div>

```json
{
	"limite": 140,
	"album": {
		"limite": 5
	},
	"credenziali": [
		{
			"access_token": ""
		},
		{
			"access_token": ""
		}
	]
}
```



| NOME ATTRIBUTO        | DESCRIZIONE                                                  | VALORE DI DEFAULT |
| --------------------- | ------------------------------------------------------------ | ----------------- |
| limite (int)          | limite massimo di post da analizzare durante una nuova chiamata alle API di Instagram | 50                |
| album.limite (int)    | limite massimo di post di un **album** da analizzare durante una nuova chiamata alle API di Instagram | 10                |
| access_token (String) | access_token dell'utente                                     | ""                |



### <div id="notabene">NOTA BENE</div>

non è possibile effettuare più di 200 chiamate l'ora per utente, perciò ad ogni nuova chiamata verrà salvato in un file locale (**dati_lettura.json**) tutti i dati letti. 

Questo file potrà essere utilizzato per filtrare i post e generare le statistiche.



### <div id="file_locali">FILE LOCALI</div>

| NOME                  | DESCRIZIONE                                                  |
| --------------------- | ------------------------------------------------------------ |
| **config.json**       | definisce i filtri di default e gli access_token dei vari utenti |
| **dati_lettura.json** | file locale dove l'applicazione salva i dati ottenuti dai vari utenti |

E' possibile cambiare il nome e la posizione di tali file mediante <a href="https://github.com/andreamaranesi/java_project/blob/master/instagram_api/src/main/resources/application.properties">**application.properties**</a>



## <div id="chiamate">CHIAMATE</div>

All'interno dell'applicazione è possibile effettuare chiamate GET per **filtrare** i post, generare le **statistiche**, ottenere i **metadati**. 

| NOME         | PARAMETRI                               | BODY (`JSON`) |
| ------------ | --------------------------------------- | ------------- |
| /dati        | leggi_locale, hashtag, data_caricamento | `filtri`      |
| /statistiche | leggi_locale, data_caricamento          | `statistiche` |
| /metadati    |                                         |               |



### <div id="parametri">PARAMETRI</div>

| PARAMETRO                     | DESCRIZIONE                                                  | VALORE DEFAULT |
| ----------------------------- | ------------------------------------------------------------ | -------------- |
| **leggi_locale** (boolean)    | Consente di scegliere se fare una **nuova chiamata** ad Instagram per recuperare i dati dei vari utenti, o se leggere quest'ultimi dal file locale **dati_lettura.json** | true           |
| **hashtag** (string)          | Consente di selezionare i post che contengono gli hashtag cercati | ""             |
| **data_caricamento** (string) | Consente di selezionare i post caricati in un certo intervallo temporale | ""             |



### <div id="risposta_filtri">RISPOSTA **`filtri`**</div>

restituisce il seguente **JSON**

```json
{
  "utenti": [
    {
      "id": {
        "tipo": "int",
        "descrizione": "id dell'utente"
      },
      "username": {
        "tipo": "String",
        "descrizione": "nome dell'utente"
      },
      "posts": [
        {
          "id": {
            "tipo": "long",
            "descrizione": "id del post"
          },
          "descrizione": {
            "tipo": "String",
            "descrizione": "descrizione del post"
          },
          "tipo_post": {
            "tipo": "String",
            "descrizione": "Il tipo del post (IMMAGINE, VIDEO, CAROUSEL_ALBUM)"
          },
          "data_creazione": {
            "tipo": "String",
            "descrizione": "data di caricamento del media"
          },
          "altezza": {
            "tipo": "String",
            "descrizione": "l'altezza del media IMMAGINE in px"
          },
          "larghezza": {
            "tipo": "String",
            "descrizione": "larghezza del media IMMAGINE in px"
          },
          "dimensione": {
            "tipo": "String",
            "descrizione": "dimensione in MB o KB del media"
          },
          "children": {
            "tipo": "ArrayList<post>",
            "descrizione": "figli dell'album"
          }
        }
      ]
    }
  ]
}
```



### <div id="risposta_statistiche">RISPOSTA **`statistiche`**</div>

restituisce un **JSON** contenente

- dimensione media dei post (in MB o KB)

- dimensione media altezza e larghezza (in px)

- lunghezza media della descrizione 

- ripetizione dei vari hashtag nei post analizzati

- conteggio post in base alla loro dimensione (in MB o KB)

  

#### <div id="uso_parametri">UTILIZZO PARAMETRI</div>

| PARAMETRO            | UTILIZZO                                   | SEPARATORE |
| -------------------- | ------------------------------------------ | ---------- |
| **hashtag**          | hashtag_cercato_1 or hashtag_cercato_2     | or         |
| **data_caricamento** | > giorno-mese-anno and <  giorno-mese-anno | and        |



#### **<div id="esempi_parametri">ESEMPI** PARAMETRI</div>

- ### **hashtag** 

  > ESEMPIO: **sea or italy or blue**

  

- ### **data_caricamento**

  La stringa deve rispettare i seguenti formati: 

  1) < quantificatore > < giorno > **-** < mese > **-** < anno >   

  2) < quantificatore > < mese > **-** < anno >

  3) < quantificatore > < anno > 

  

  > Quantificatori
  >
  > **>**
  >
  > **<**

  

  1) Verranno trovati i post caricati prima o dopo la data giorno/mese/anno

  2) Verranno trovati i post caricati prima o dopo la data 1/mese/anno

  3) Verranno trovati i post caricati prima o dopo la data 1/1/anno

  

  > ESEMPIO: **> 6-2019 and < 10-6-2020** 



## <div id="body_chiamate">BODY (`JSON`)</div>

#### `filtri`

```json
  {
     "limite":50,
     "post":{
        "tipo_dati":0,
        "dimensioni":{
           "min":0,
           "max":2
        },
        "lunghezza_desc":{
           "min":0,
           "max":40
        },
        "dimensioni_px":{
           "altezza":{
              "min":0,
              "max":2000
           },
           "larghezza":{
              "min":0,
              "max":2000
           }
        },
        "analisi_media":true,
        "descrizione":true
     },
     "album":{
        "limite":5
     }
  }
```



##### <div id="uso_filtri">UTILIZZO FILTRI </div>

- **`limite`** (int)

  consente di limitare il numero di post da filtrare

  > **DEFAULT**: 50

- ##  post

  -  **tipo_dati** (int)

    > **DEFAULT**: 0
    >
    > **VALORI POSSIBILI**: 0 e 1
    >
    > - 0: dimensione in MB
    > - 1: dimensione in KB

  - **`dimensioni`**

    consente di filtrare i post in base alla dimensione in MB ( **tipo_dati = 0** ) o KB ( **tipo_dati = 1** )

    - **min** (double)

      > **DEFAULT**: 0
      >
      > **VALORI POSSIBILI**: >= 0

      

    - **max** (double)

      > **DEFAULT**: 
      >
      > ```java
      > Double.POSITIVE_INFINITY
      > ```
      >
      > **VALORI POSSIBILI**: >= 0
      
      

  - **`lunghezza_desc`**

    consente di filtrare i post in base al numero di caratteri presenti nella descrizione

    - **min** (int)

      > **DEFAULT**: 0
      >
      > **VALORI POSSIBILI**: >= 0

      

    - **max** (int)

      > **DEFAULT**: 
      >
      > ```java
      > Integer.MAX_VALUE
      > ```
      >
      > **VALORI POSSIBILI**: >= 0
      
      

  - **`dimensioni_px`**

    consente di filtrare i post in base alla loro altezza o larghezza (in px)

    - `altezza`

      - **min** (int)

        > **DEFAULT**: 0
        >
        > **VALORI POSSIBILI**: >= 0

      - **max**( int)

        > **DEFAULT**: 
        >
        > ```java
        > Integer.MAX_VALUE
        > ```
        >
        > **VALORI POSSIBILI**: >= 0
      
        

    - `larghezza`

      - **min** (int)
    
        >**DEFAULT**: 0
      >
        >**VALORI POSSIBILI**: >= 0

        
    
      - **max** (int)
      
        >**DEFAULT**: 
        >
        >```java
        >Integer.POSITIVE_INFINITY
        >```
        >
        >**VALORI POSSIBILI**: >= 0
        
        

- **analisi_media** (boolean)

  **false**: disabilita il filtro delle dimensioni 

  > **DEFAULT**: true

  

- **descrizione** (boolean)

  **false**: disabilita il filtro per la lunghezza della descrizione

  > **DEFAULT**: true

  

- **`album`**

  - **limite**

    consente di limitare il numero di media di un album da analizzare

    > **DEFAULT**: 5



#### `statistiche`

```json
{
   "hashtag":true,
   "limite_post":20,
   "dimensione":false,
   "tipo_dimensione":0,
   "numero_fasce":2
}
```



### <div id="uso_statistiche">UTILIZZO STATISTICHE</div>

- **hashtag** (boolean)

  viene effettuata un'analisi del numero di volte un hashtag è stato utilizzato all'interno dei post analizzati

  > **DEFAULT**: true

  

- **limite_post** (boolean)

  consente di limitare il numero di post da analizzare per le statistiche

  > **DEFAULT**: 20

  

- **dimensione** (boolean)

  **false**: consente di disabilitare l'analisi delle dimensioni (MB o KB) dei post

  > **DEFAULT**: false

  

- **tipo_dimensione** (int)

  consente di analizzare le dimensioni dei post in MB o KB

  > **DEFAULT**: 0
  >
  > **VALORI POSSIBILI**: 0 e 1
  >
  > - 0: dimensione in MB
  > - 1: dimensione in KB

  

- **numero_fasce** (int)

  restituisce i post trovati in un certo intervallo di dimensioni (MB o KB)

  > **DEFAULT**: 4
  >
  > **VALORI POSSIBILI**: 2*k, con k=+1,+2,+3,...



## <div id="esempi_chiamate">ESEMPIO CHIAMATE</div>

#### <div id="esempi_filtri">CHIAMATA PER FILTRARE POST</div>

URL : **/dati?leggi_locale={leggi}&hashtag={hashtag}&data_caricamento={data}**

leggi=**true**

hashtag=**sea or montevettore**

data_caricamento = **> 2019 and < 6-2020****

**BODY**:

```json
{
   "limite":20,
   "post":{
      "tipo_dati":0,
      "dimensioni":{
         "min":0
      },
      "lunghezza_desc":{
         "min":0,
         "max":1000
      },
      "dimensioni_px":{
         "altezza":{
            "min":0
         },
         "larghezza":{
            "min":0
         }
      },
      "analisi_media":true,
      "descrizione":true
   },
   "album":{
      "limite":5
   }
}
```

#### RISPOSTA

```json
{
    "utenti": [
        {
            "id": 17841400127087060,
            "username": "dremar_design",
            "follower": "652",
            "posts": [
                {
                    "id": 17952399373252110,
                    "media_url": "https://scontent-mxp1-1.cdninstagram.com/v/t51.2885-15/52434302_353336301950167_1483678402306919839_n.jpg?_nc_cat=103&_nc_sid=8ae9d6&_nc_ohc=8ePqw_mv-TQAX9tQ9WS&_nc_ht=scontent-mxp1-1.cdninstagram.com&oh=f55d921e7cb1c15b67735151f405a545&oe=5F0E1042",
                    "descrizione": "#nature #italy #sea",
                    "tipo_post": "CAROUSEL_ALBUM",
                    "data_creazione": "17/03/2019 - 19:00:44",
                    "children": [
                        {
                            "id": 18006095329174251,
                            "media_url": "https://scontent-mxp1-1.cdninstagram.com/v/t51.2885-15/52434302_353336301950167_1483678402306919839_n.jpg?_nc_cat=103&_nc_sid=8ae9d6&_nc_ohc=8ePqw_mv-TQAX9tQ9WS&_nc_ht=scontent-mxp1-1.cdninstagram.com&oh=f55d921e7cb1c15b67735151f405a545&oe=5F0E1042",
                            "tipo_post": "IMMAGINE",
                            "altezza": "810px",
                            "larghezza": "1080px",
                            "dimensione": "84,205 KB"
                        },
                        {
                            "id": 18025082986120887,
                            "media_url": "https://scontent-mxp1-1.cdninstagram.com/v/t51.2885-15/53051116_556707454835004_7454077963642978870_n.jpg?_nc_cat=106&_nc_sid=8ae9d6&_nc_ohc=QhXPWYO0MOQAX9UDaom&_nc_ht=scontent-mxp1-1.cdninstagram.com&oh=d0c245c8b00d266447f4afeaf0ba2437&oe=5F10F3CB",
                            "tipo_post": "IMMAGINE",
                            "altezza": "810px",
                            "larghezza": "1080px",
                            "dimensione": "99,372 KB"
                        }
                    ]
                }
            ]
        },
        {
            "id": 17841401672449563,
            "username": "_pittii_",
            "follower": "556",
            "posts": [
                {
                    "id": 17965419811202651,
                    "media_url": "https://scontent-mxp1-1.cdninstagram.com/v/t51.2885-15/49743998_579648919114179_5447581749868890907_n.jpg?_nc_cat=101&_nc_sid=8ae9d6&_nc_ohc=YyVy1fOclPoAX_6inR7&_nc_ht=scontent-mxp1-1.cdninstagram.com&oh=a6880cdb4ab379902d1c698d746327c6&oe=5F0EFF15",
                    "descrizione": "Se Alessio non va dalla montagna, la montagna va da Alessio⛰🌏\n#montevettore #laghidipilato",
                    "tipo_post": "IMMAGINE",
                    "data_creazione": "26/01/2019 - 14:29:49",
                    "altezza": "1350px",
                    "larghezza": "1080px",
                    "dimensione": "91,177 KB"
                }
            ]
        }
    ]
}
```



#### <div id="esempi_statistiche">CHIAMATA PER GENERARE LE STATISTICHE </div>

URL : **/statistiche?leggi_locale={leggi}&data_caricamento={data}**

leggi=**true**

data_caricamento = **> 2019 and < 6-2020**

**BODY**:

```json
{
   "hashtag":true,
   "limite_post":20,
   "dimensione":true,
   "tipo_dimensione":0,
   "numero_fasce":2
}

```

**RISPOSTA**

```json
{
  "utenti": [
    {
      "id": 17841400127087060,
      "username": "dremar_design",
      "dimensione_media": "0,249 MB",
      "altezza_media": "1080px",
      "larghezza_media": "1080px",
      "media_caricamenti": "ogni 3 mesi",
      "dati_media": [
        {
          "conteggio": 7,
          "min": "0,075 MB",
          "max": "0,657 MB"
        },
        {
          "conteggio": 1,
          "min": "0,657 MB",
          "max": "1,239 MB"
        }
      ],
      "hashtag": [
        {
          "nome": "nature",
          "conteggio": 4
        },
        {
          "nome": "old",
          "conteggio": 1
        },
        {
          "nome": "android",
          "conteggio": 1
        },
        {
          "nome": "cuoio",
          "conteggio": 1
        },
        {
          "nome": "shoes",
          "conteggio": 2
        },
        {
          "nome": "water",
          "conteggio": 1
        },
        {
          "nome": "madeinitaly",
          "conteggio": 1
        },
        {
          "nome": "sea",
          "conteggio": 1
        },
        {
          "nome": "apple",
          "conteggio": 1
        },
        {
          "nome": "brunoparmigianicalzature",
          "conteggio": 1
        },
        {
          "nome": "mountain",
          "conteggio": 1
        },
        {
          "nome": "flutter",
          "conteggio": 1
        },
        {
          "nome": "blue",
          "conteggio": 1
        },
        {
          "nome": "fashionshoes",
          "conteggio": 1
        },
        {
          "nome": "casual",
          "conteggio": 1
        },
        {
          "nome": "fibbia",
          "conteggio": 1
        },
        {
          "nome": "springsummer",
          "conteggio": 1
        },
        {
          "nome": "clothing",
          "conteggio": 1
        },
        {
          "nome": "man",
          "conteggio": 1
        },
        {
          "nome": "brand",
          "conteggio": 1
        },
        {
          "nome": "landscape",
          "conteggio": 1
        },
        {
          "nome": "photography",
          "conteggio": 1
        },
        {
          "nome": "italy",
          "conteggio": 1
        }
      ],
      "lunghezza_media_descrizione": "120 caratteri"
    },
    {
      "id": 17841401672449563,
      "username": "_pittii_",
      "dimensione_media": "0,066 MB",
      "altezza_media": "1009px",
      "larghezza_media": "974px",
      "media_caricamenti": "ogni 9 mesi",
      "dati_media": [
        {
          "conteggio": 1,
          "min": "0,041 MB",
          "max": "0,066 MB"
        },
        {
          "conteggio": 1,
          "min": "0,066 MB",
          "max": "0,091 MB"
        }
      ],
      "hashtag": [
        {
          "nome": "montevettore",
          "conteggio": 1
        },
        {
          "nome": "laghidipilato",
          "conteggio": 1
        }
      ],
      "lunghezza_media_descrizione": "57 caratteri"
    }
  ]
}
```



# <div id="web">IN FASE DI SVILUPPO</div>

SARA' POSSIBILE FILTRARE I POST DEGLI UTENTI E RICEVERE IL RISULTATO IN FORMATO HTML



| NOME                | DESCRIZIONE                        | PARAMETRI                                           |
| ------------------- | ---------------------------------- | --------------------------------------------------- |
| /mostra_dati        | disponibile ma in fase di sviluppo | **leggi_locale**, **hashtag**, **data_caricamento** |
| /mostra_statistiche | in via di sviluppo                 |                                                     |



### PARAMETRI

per **/mostra_dati** vedere <a href="#uso_parametri">PARAMETRI </a>

