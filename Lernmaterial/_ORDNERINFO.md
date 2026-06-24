# Ordner: Lernmaterial/

Java-Lerndokumente (Lernstoff, keine Abgabe).

| Datei | Inhalt |
|---|---|
| `Spickzettel_DoIT.docx` | **Haupt-Spickzettel (5 S.)**: Teil A IntelliJ (Symbole/Icons, Farben, Shortcuts, Gutter, Start), Teil B Java/JDBC-Code (static vs. Instanz, JDBC-Begriffe, DBConnection-Pattern, häufige Fehler), Teil C Architektur/Warum (Diagramm, Wer-kennt-wen, Maven, Singleton, DAO/PreparedStatement, Ablauf „Suchen", Fachgespräch). Zusammenführung aus SPICKZETTEL + IntelliJ_Spickzettel_DoIT + Konzept-Zusammenhänge. |
| `how_to_codereview.pdf` | Anleitung Code-Review |
**Lernblätter (A4-HTML, einheitlicher Stil — 17 Stück, alle hier):**
| `Code_lesen_Spickzettel.html` | Code Zeile für Zeile (Code links / Erklärung rechts), groß/klein, Klammern, was macht eine Methode zur Methode |
| `Generics_Spickzettel.html` | Generics `<T>` — Platzhalter aufstellen/ausfüllen, Diamant `<>`, zwei Typen |
| `Farben_Spickzettel.html` | Code-Farben (Darcula): welche Farbe = welches Element, woran erkennbar |
| `Roter_Faden_Java.html` | Übersicht + Guide: Zuständigkeiten und Abläufe (Klick→Ergebnis) der Lagerverwaltung |
| `Roter_Faden_Diagramme.html` | Visuelle Ablauf-/Architektur-Diagramme (SVG) zur Lagerverwaltung |
| `Static_vs_Instanz_Spickzettel.html` | Statischer Aufruf (Klasse, kein Objekt) vs. Instanz-Aufruf (Objekt mit `new`) — Regel „was steht vor dem Punkt", DoIT-Beispiele, Vergleichstabelle, Fallen; inkl. primitiv vs. Referenztyp/Objekt erkennen |
| `Methodensignatur_Spickzettel.html` | Methodensignatur (Kopf einer Methode): Rückgabetyp + Name + `()`, warum kein `=`, Ankündigung (Interface) vs. Umsetzung (Klasse), Signatur ≠ Aufruf, Überladung |
| `Kapselung_Spickzettel.html` | Kapselung: private Felder, Getter/Setter, Validierung, Zugriffsmodifikatoren-Tabelle (private/package-private/protected/public) |
| `Collections_Spickzettel.html` | Wrapperklassen (int↔Integer, Autoboxing), Collections-Überblick List/Set/Queue/Map, ArrayList + HashMap |
| `Vererbung_Polymorphie_Spickzettel.html` | Vererbung (extends/super), Überschreiben/@Override, Polymorphie, Überladung≠Überschreiben; DoIT nutzt meist Interface/Generics |
| `Annotationen_Spickzettel.html` | Annotationen @FXML + @Override, this/super — mit echtem DoIT-Code (MainController, Material) |
| `Fachwoerter_Zusammenhaenge_Spickzettel.html` | Übersicht: wie alle Fachwörter zusammenhängen (Gruppen + Begriffs-Index → welches Blatt erklärt was) |
| `Glossar_Fachwoerter_Spickzettel.html` | A–Z-Glossar in 9 Gruppen: jedes Fachwort + einfaches Synonym + kurze Erklärung (OOP, Variablen, static, Kapselung, Vererbung, Generics, Collections, Architektur/JavaFX, Werkzeuge) |
| `Wann_static_Spickzettel.html` | Entscheidungsfrage „Wann nehme ich `static`?" + drei Anwendungsfälle mit echtem DoIT-Code |
| `Ternaerer_Operator_Spickzettel.html` | Ternärer Operator `Bedingung ? WertA : WertB`: Kurzform für wertlieferndes if/else, ausgeschrieben gegenübergestellt, echte DoIT-Beispiele (null-Prüfung, Status) |
| `Ueberladung_Spickzettel.html` | Überladung (Overloading): gleicher Name/andere Parameter, zwei echte `Material`-Konstruktoren + Methoden-Beispiel, Eindeutigkeits-Kriterien, Abgrenzung zu Überschreiben |
| `Wann_static_Spickzettel.html` | Wann nehme ich `static`? — Entscheidungsfrage, Entscheidungsbaum, drei Anwendungsfälle (Konstante, Singleton, Hilfsmethode), Gegenbeispiel Instanz-Methode, Fallen |
| `Interface_Abstrakt_Enum_Spickzettel.html` | Drei Sonderformen neben der Klasse im Vergleich: Interface (Vertrag, `implements`, echtes `LeseDAO<T>`), abstrakte Klasse (halbfertiger Bauplan, `extends`; Hinweis: in DoIT nicht genutzt), enum (feste Werteliste, echtes `BewegungsTyp` EINGANG/AUSGANG), Vergleichstabelle, Fallen, Glossar |
| `Zugriffsmodifikatoren_Spickzettel.html` | Dedicated sheet: private/package-private/protected/public — Vergleichstabelle, je ein Code-Beispiel, vollständiges DAO-Muster-Beispiel, Fallen, Glossar. **Prüfungsthema.** |
| `JavaFX_Buttons_Spickzettel.html` | @FXML, fx:id, onAction="#...", setOnAction, Lambda, initialize(), ActionEvent — Klick-bis-Methode-Kette, Weg 1 (FXML-Attribut) + Weg 2 (setOnAction), vollständiges Controller-Beispiel mit DAO, Fallen, Glossar. **Prüfungsthema.** |

**Prüfungsunterlagen (alle Themen laut Frau Cramer):**
| Datei | Inhalt |
|---|---|
| `Pruefung_Theoretisch.html` | 31 Fragen mit aufklappbaren Antworten — 7 Blöcke: OOP, Zugriffsmodifikatoren, Vererbung, Abstrakte Klassen + Interface, Arrays/Listen, JavaFX Buttons, DAO-Muster |
| `Pruefung_Praktisch_Aufgaben.html` | Aufgabenblatt (A1–A6, 70 Punkte) — Tierpension-Szenario. Zu nutzen mit den Starterdateien in `Pruefung_Praktisch/`. |
| `Pruefung_Praktisch/` | Ordner mit 6 Java-Starter-Dateien für IntelliJ: `Tier.java` (abstrakt), `Pflegbar.java` (Interface), `Hund.java`, `Katze.java` (Vererbung), `TierDAO.java` (DAO + List + Array), `TierController.java` (JavaFX), `Main.java` (Konsole + instanceof). Alle mit `// TODO`-Markierungen. |

**Erklär-Stil:** Ein Thema = ein Lernblatt, gleiches Layout, alle Lernblätter hier. Regeln/Vorlage: `MD/Erklaer-Stil.md`. Blätter inhaltlich abgeglichen mit SRH-Kursblättern Java 01–06 (`WI/Sprachen/Java/IntelliJ/Java Blätter`).

Abgelöst → `Archiv/abgeloest_Lernmaterial_2026-06-10/`: SPICKZETTEL.docx, IntelliJ_Spickzettel_DoIT.docx, Java_Konzept_Zusammenhaenge.docx, Java_Handbuch_Start.docx (Inhalt steckt im Lernhandbuch/).
