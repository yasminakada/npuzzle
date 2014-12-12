N-puzzle for android
====================
Yasmina Kada
10001567
Minor Programmeren

Design choices
--------------
- De openingscreen wordt bij het openen van de app gestart. 
  Ook als de app ooit eerder geopend was.
- Tijdens het spelen heeft de speler de optie om het spel te shufflen (terug naar 0 moves)
  en te resetten (9999 moves) naar de winnende toestand. De speler kan het spel dan "sneller" winnen
  door een tegel heen en terug te verplaatsen. Hier is voor gekozen zodat alle schermen sneller kunnen worden getest.
  Het zal de speler strafpunten opleveren, aangezien het spel niet eerlijk is gewonnen.
- Shuffle is een knop en geen keuze in het menu. Hier is voor gekozen omdat dit gebruiksvriendelijker is.
  (Dit is een bewuste afwijking van de opdracht. Het betekent niet dat de kennis ontbrak om dit via het menu te doen)
- Wanneer de speler een puzzle heeft gewonnen, word het de opgeslagen gamestate verwijderd.
  Als de app wordt afgesloten en weer gestart, zal de speler weer de optie krijgen om een level en afbeelding te kiezen.
  Wordt de app niet afgesloten, kan de speler een nieuwe afbeelding kiezen en wordt een nieuw spel
  gestart met de vorige moeilijkheidsgraad.
  (Dit is een bewuste keuze. Ik vond dit een mooiere manier om de app le laten functioneren 
  ipv een default afbeelding en level na het winnen van een spel.)
- Een enkeling methodes (in BitmapConstruct) worden niet gebruikt, maar kunnen mogelijk in de toekomst van pas komen
  als ik de app zou willen gebruiken. Het gaat hier om de conversiemethodes van dp naar px en andersom.


##Side note
De app is getest op een tablet, maar zou ook moeten werken op een smartphone.
De app is ook getest in een emulator. Helaas crasht de emulator bij het laden van
bitmaps. Er is geprobeerd dit te verhelpen door bitmaps efficienter te laden, maar
dit heeft alleen gewerkt in de ImageSelection screen. In de puzzle screen crasht de
app voor alsnog.

