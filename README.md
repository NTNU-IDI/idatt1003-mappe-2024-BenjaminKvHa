Konsollbasert Matlager- og Oppskriftsapplikasjon

Dette prosjektet er utviklet som en del av emnet "IDATT1003 Programmering 1" ved NTNU, og demonstrerer et objektorientert design for administrasjon av ingredienser og oppskrifter. Applikasjonen lar brukere:
- Legge til, fjerne og oppdatere ingredienser i et virtuelt lager.
- Opprette, endre og slette oppskrifter.
- Sjekke om en oppskrift kan lages basert på tilgjengelige ingredienser.
- Interagere via et tekstbasert brukergrensesnitt.

Funksjonalitet
- Administrasjon av ingredienser:
  Legg til nye ingredienser med navn, mengde, enhet, best-før-dato og pris per enhet. Fjern eller endre eksisterende ingredienser. Søk etter ingredienser ved navn.

- Oppskriftsadministrasjon:
  Opprett nye oppskrifter med detaljer som navn, beskrivelse, fremgangsmåte, antall porsjoner og nødvendige ingredienser. Rediger eller slett eksisterende oppskrifter. Søk etter oppskrifter ved navn.

- Tilgjengelighetssjekk:
  Se hvilke oppskrifter som kan lages med dagens lagerbeholdning.

Språk: Java

For å kjøre programmet må du ha Java installert. Du kjører programmet fra Main.java filen.

Bruk
Etter oppstart presenteres et tekstbasert grensesnitt med ulike menyvalg. Bruker kan følge instruksjonene på skjermen for å legge til ingredienser, opprette oppskrifter eller sjekke tilgjengelighet.

For eksempel:

Velg "1" for å legge til ingrediens
Velg "2" for å liste ingredienser
Velg "3" for å opprette ny oppskrift
... og så videre.
Feilmeldinger og tilbakemeldinger gis direkte i terminalen, med informasjon om hva som gikk galt og hvordan rette opp.

Enhetstester er implementert med JUnit 5.
