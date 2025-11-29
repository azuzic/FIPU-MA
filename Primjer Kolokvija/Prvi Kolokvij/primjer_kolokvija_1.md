<div>

# Mobilne aplikacije

## Primjer kolokvija #1

Kolokvij nosi ukupno **25 bodova** i piše se **120 minuta**.

Svaki zadatak rješavate u zasebnom Android projektu. Postavke projekta neka budu:
- Template: **No Activity**
- Project name: **za svaki zadatak zasebno, npr.** `K1_Z1`, `K1_Z2`, `K1_Z3`
- Package name: **hr.fipu.(project_name)**
- Save location: **po želji**
- Language: **Java**
- Minimum SDK: **API 28: (Pie) Android 9.0**
- Build configuration language: **Groovy DSL (build.gradle)**

<div style="width: fit-content; display: flex; flex-direction: column;">
    <div style="display: flex; justify-content: center;">
        <img src="slike/Postavke.png" style="border: solid 1px lightgray; border-radius: 8px;"/>
    </div>
    <br/>
    <p style="margin-top: -16px; width: 100%; text-align: center;"><i>Postavke svakog projekta</i></p>
</div>

> **Napomena**: Pri rješavanju zadataka dopušteno je koristiti službenu Android dokumentaciju, materijale s predavanja i internet. Nije dopušteno koristiti generative AI alate (*ChatGPT, DeepSeek, Claude, Grok itd.*).

> **Napomena**: Android Studio ima integrirani **Gemini** AI i njegova uporaba je dopuštena *isključivo* za manje funkcionalnosti poput automatskog dovršavanja koda i sugestija unutar editora. **Nije dopušteno generirati kompletna rješenja zadataka od početka do kraja.**

<div class="page"></div>

## Zadatak 1. (5 bodova)

Zadana je sljedeća slika korisničkog sučelja za Android aplikaciju, rekreirajte je koristeći **constraint layout** kao glavni layout:

<div style="width: fit-content; display: flex; flex-direction: column;">
    <div style="display: flex; justify-content: center;">
        <img src="slike/zadatak_1.png" style="border: solid 1px lightgray; border-radius: 8px;"/>
    </div>
    <br/>
    <p style="margin-top: -16px; width: 100%; text-align: center;"><i>Zadatak 1. - GUI za rekreirati</i></p>
</div>

> **Napomena**: Slike korištene u zadatku nalaze se u mapi `zadatak_1` kada preuzmete ovaj primjer kolokvija.

<div class="page"></div>

### Specifikacije korisničkog sučelja:

**Header**
- Background boja: `#191919`

**Naslov "Discogs"**
- Veličina teksta: `48sp`
- Boja: **Bijela**
- Font stil: **Bold**
- Font obitelj: `sans-serif-condensed-medium`

**Logo**
- Dimenzije: `48dp × 48dp`

**Slike albuma**
- Dimenzije: `150dp × 150dp`

**Naslovi albuma**
- Veličina teksta: `16sp`
- Boja: **Crna**
- Font stil: **Bold**
- Redak: Single line

**Autori albuma**
- Veličina teksta: `16sp`
- Boja: **Siva**

**Dugma**
- Background tint: `#26A69A`

> **Napomena**: Razmak između elemenata neka bude sličan kao na slici, ali nije potrebno da bude identičan. Postoji više načina da se postigne sličan izgled.

> **Napomena**: Blueprint izgled je tu samo kao pomoć pri izradi sučelja, nije potrebno da vaše blueprint izgleda identično kao slici.

<div class="page"></div>

## Zadatak 2. (12 bodova)

1. Izradite klasu `Knjiga` koja ima sljedeće atribute:
    - `String naslov`
    - `String autor`
    - `int godinaIzdavanja`
    - `String opis`

- Napišite konstruktor koji prima sve atribute kao parametre i inicijalizira ih. Također, implementirajte metode za dohvaćanje (gettere) svih atributa.

2. Izradite klasu `Knjiznica` koja sadrži listu objekata tipa `Knjiga`. Implementirajte sljedeće metode:
    - `void dodajKnjigu(Knjiga knjiga)` - dodaje knjigu u knjižnicu
    - `void ukloniKnjigu(String naslov)` - uklanja knjigu iz knjižnice prema naslovu
    - `Knjiga nadjiKnjigu(String naslov)` - pronalazi i vraća knjigu prema naslovu
    - `List<Knjiga> dohvatiSveKnjige()` - vraća listu svih knjiga u knjižnici

3. `MainActivity` klasa treba prikazivati listu svih knjiga. Korisničko sučelje treba sadržavati:
    - `EditText` za unos naslova knjige za brisanje ili otvaranje detalja
    - Dugme za dodavanje nove knjige koje otvara `DodajKnjiguActivity`
    - Dugme za brisanje knjige
        - Prilikom klika na dugme, knjiga čiji je naslov unesen u `EditText` treba biti obrisana iz knjižnice
        - Prikazati poruku o uspješnom brisanju knjige
        - Nakon brisanja, ažurirati prikaz liste knjiga
        - Ako knjiga nije pronađena, prikazati odgovarajuću poruku
    - Dugme za prikaz detalja knjige koje otvara `DetaljiKnjigeActivity` 
        - Prikazati detalje knjige čiji je naslov unesen u `EditText`
        - Ako knjiga nije pronađena, prikazati odgovarajuću poruku
    - `TextView` za prikaz liste knjiga (*naslov i autor*)

4. `DodajKnjiguActivity` klasa treba omogućiti korisniku da unese detalje nove knjige putem korisničkog sučelja i doda je u knjižnicu.
    - Korisničko sučelje treba sadržavati polja za unos naslova, autora, godine izdavanja i opisa knjige, te dugme za dodavanje knjige
    - Nakon unosa podataka i klika na dugme, nova knjiga treba biti dodana u knjižnicu unutar `MainActivity` koristeći **extra** proslijeđene podatke nakon zatvaranja `DodajKnjiguActivity` i povratka na `MainActivity`
        - Prikazati poruku o uspješnom dodavanju knjige
        - Spriječiti dodavanje knjige ako je već postoji knjiga s istim naslovom, te prikazati odgovarajuću poruku

<div class="page"></div>

5. `DetaljiKnjigeActivity` klasa treba prikazati detalje knjige čiji je naslov unesen u `MainActivity`.
    - Korisničko sučelje treba sadržavati `TextView` za prikaz naslova, autora, godine izdavanja i opisa knjige
    - Dugme za povratak na `MainActivity`

Podaci:
```java
knjiznica.dodajKnjigu(new Knjiga("Hobit", "J.R.R. Tolkien", 1937,
    "Fantastični roman koji prati avanture hobita Bilba Bagginsa dok putuje Međuzemljem kako bi povratio blago koje je zmaj Smaug oteo patuljcima. Knjiga je uvod u bogati svijet 'Gospodara prstenova' i predstavlja čitateljima mnoge rase i mjesta koja će kasnije postati ključna."));
knjiznica.dodajKnjigu(new Knjiga("1984", "George Orwell", 1949,
    "Distopijski roman koji opisuje život u totalitarnoj državi Oceaniji, gdje Veliki Brat sve nadzire, a misao je zločin. Glavni lik, Winston Smith, radi za Ministarstvo istine i potajno se buni protiv režima, što ga dovodi u veliku opasnost."));
knjiznica.dodajKnjigu(new Knjiga("Ponos i predrasude", "Jane Austen", 1813,
    "Ljubavni roman koji istražuje složene društvene odnose i pitanja braka, morala i odgoja u Engleskoj početkom 19. stoljeća kroz priču o Elizabeth Bennet i gospodinu Darcyju. Kroz duhovite dijaloge i složene likove, Austen kritizira društvene konvencije svog vremena."));
knjiznica.dodajKnjigu(new Knjiga("Ubiti pticu rugalicu", "Harper Lee", 1960,
    "Roman smješten na američkom Jugu tijekom Velike depresije, koji se bavi ozbiljnim temama rasne nepravde i gubitka nevinosti, promatranim očima djevojčice Scout Finch. Njezin otac, Atticus Finch, odvjetnik je koji brani crnca lažno optuženog za silovanje bjelkinje, što izaziva bijes u njihovoj zajednici."));
knjiznica.dodajKnjigu(new Knjiga("Alkemičar", "Paulo Coelho", 1988,
    "Filozofski roman koji prati mladog andaluzijskog pastira Santiaga u njegovoj potrazi za blagom skrivenim u egipatskim piramidama. Putovanje ga uči o važnosti slušanja vlastitog srca, prepoznavanju znakova i ostvarivanju svoje 'Osobne legende'."));
 ```

<div class="page"></div>

<div style="width: fit-content; display: flex; flex-direction: column;">
    <div style="display: flex; justify-content: center;">
        <img src="slike/zadatak_2.png" style="filter: drop-shadow(0 0 1px rgba(0,0,0,0.5));"/>
    </div>
    <br/>
    <p style="margin-top: -16px; width: 100%; text-align: center;"><i>Zadatak 2. - primjer sučelja</i></p>
</div>

> **Napomena**: Izgled korisničkog sučelja nije strogo definiran, ali treba sadržavati sve tražene elemente i biti funkcionalan.

<div class="page"></div>

## Zadatak 3. (8 bodova)

Zadan je sljedeći xml layout:

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingStart="40px"
    android:paddingLeft="40px"
    android:paddingTop="256px"
    android:paddingEnd="40px">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Self removing blue buttons!"
        android:textSize="24sp" />

    <Button
        android:id="@+id/button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:backgroundTint="#C3C3C3"
        android:text="Click Me"
        android:textColor="#000000" />

    <LinearLayout
        android:id="@+id/buttonContainer"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical" />
</LinearLayout>
```

Rekreirajte ga programski u `MainActivity` klasi koristeći Java kôd, bez korištenja XML layout datoteke. 
- Postavite elemente kao u zadanom XML-u. 
    - Pritiskom na glavno dugme ("Click Me"), dodajte plavo dugme s textom "`i`. Ukloni me" u `LinearLayout` container, gdje `i` predstavlja inkrement broja plavih dugmadi koja su dodana (*1, 2, 3, ...*), koji se povećava sa svakim novim dodanim dugmetom.
    - Svako plavo dugme treba imati event listener koji će ga izbrisati kada se na njega klikne

<div class="page"></div>

<div style="width: fit-content; display: flex; flex-direction: column;">
    <div style="display: flex; justify-content: center;">
        <img src="slike/zadatak_3.png"  width="450px" style="border: solid 1px lightgray; border-radius: 8px;"/>
    </div>
    <br/>
    <p style="margin-top: -16px; width: 100%; text-align: center;"><i>Zadatak 3. - GUI za rekreirati programski</i></p>
</div>

<div class="page"></div>

## Predajte sljedeće:

- Za svaki zadatak zasebno predajete mapu koja sadrži strukturu projekta:
    1. `app/manifests/` - sadrži `AndroidManifest.xml` datoteku
    2. `app/java/direktorij/` - sadrži `.java` datoteke
    3. `app/res/drawable/` - sadrži slike korištene u aplikaciji
    4. `app/res/layout/` - sadrži `.xml` datoteke za layoute
    5. `app/res/values/` - sadrži `colors.xml` & `strings.xml` datoteke

- **Zatim sve zip-ate u jednu datoteku i predajete tu `.zip` datoteku.**

<div style="width: fit-content; display: flex; flex-direction: column;">
    <div style="display: flex; justify-content: center;">
        <img src="slike/ZaPredatiPrimjer.png" style="border: solid 1px lightgray; border-radius: 8px;"/>
    </div>
    <br/>
    <p style="margin-top: -16px; width: 100%; text-align: center;"><i>Primjer mapa i datoteka za predati</i></p>
</div>

> **Napomena**: Svaki zadatak predajete kao zaseban projekt, ne predajete sve zadatke u jednom projektu.

</div>