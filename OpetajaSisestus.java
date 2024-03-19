import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ÕpetajaSisestus implements TeineSisend {
    private final String vabadeAegadeFail = "ÕpetajadVabadAjad.txt";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void sisesta(Scanner scanner) {
        String nimi;
        double tunnihind = 0.0;
        List<String> lubatudAined = Arrays.asList("matemaatika", "eesti keel", "keemia", "füüsika");
        List<String> ained = new ArrayList<>();
        List<String> õppeastmed = new ArrayList<>();
        List<String> tunniajad = new ArrayList<>();
        String aine, õppeaste, tunniaeg;

        // Nime sisestus ja kinnitus
        do {
            System.out.println("Mis Su nimi on? vastus: ");
            nimi = scanner.nextLine();
            System.out.println("Sisestasid nimeks: " + nimi + ". Kas see on õige? (jah/ei)");
        } while (!scanner.nextLine().equalsIgnoreCase("jah"));

        // Tunnihinna sisestus ja automaatkontroll
        while (true) {
            System.out.println("Mis on su akadeemilise tunni hind (€/45 min)? vastus: (number! nt 2.0 või 2.5)");
            if (scanner.hasNextDouble()) {
                tunnihind = scanner.nextDouble();
                scanner.nextLine(); // Puhastab reavahetuse pärast double sisendit
                break;
            } else {
                System.out.println("Sisesta palun korrektne number.");
                scanner.nextLine(); // Ignoreerib vigase sisendi
            }
        }

        // Ainete sisestus ja automaatkontroll
        System.out.println("Sisesta ükshaaval milliseid õppeaineid õpetad? [matemaatika/eesti keel/keemia/füüsika] vastus: ");
        while (!(aine = scanner.nextLine()).equalsIgnoreCase("valmis")) {
            aine = aine.toLowerCase(); // Paneme vastused lowercase'i
            if (!lubatudAined.contains(aine)) {
                System.out.println("Ainet ei leitud süsteemi ainete nimekirjast. Palun sisesta aine uuesti. vastus: ");
            } else if (ained.contains(aine)) {
                System.out.println("See aine on juba lisatud. Sisesta järgmine aine või kirjuta 'valmis'. vastus: ");
            } else {
                ained.add(aine);
                System.out.println("Aine lisatud. Sisesta järgmine aine või kirjuta 'valmis': ");
            }
        }

        // Õppeastme sisestus ja automaatkontroll
        while (true) {
            System.out.println("Millist õppeastet [põhikool/gümnaasium/mõlemad] õpetad? vastus:");
            õppeaste = scanner.nextLine().toLowerCase();
            if (õppeaste.equals("gümnaasium") || õppeaste.equals("põhikool")) {
                õppeastmed.add(õppeaste);
                break;
            } else if (õppeaste.equals("mõlemad")) {
                õppeastmed.add("gümnaasium");
                õppeastmed.add("põhikool");
                break;
            } else {
                System.out.println("Vigane sisestus. Palun sisesta [põhikool/gümnaasium/mõlemad].");
            }
        }

        // Tunniaegade sisestus
        System.out.println("Sisesta ÜKSHAAVAL oma tundide algusajad (kuupäev ja kellaaeg formaadis yyyy-MM-dd HH:mm), kirjuta 'valmis', kui oled lõpetanud: ");
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("valmis")) {
            try {
                LocalDateTime tunniAeg = LocalDateTime.parse(input, dateTimeFormatter);
                tunniajad.add(tunniAeg.format(dateTimeFormatter));
                System.out.println("Tunniaeg lisatud. Sisesta järgmine aeg või 'valmis': ");
            } catch (Exception e) {
                System.out.println("Vigane formaat, palun sisesta kuupäev ja kellaaeg formaadis yyyy-MM-dd HH:mm");
            }
            input = scanner.nextLine();
        }

        // Sisestuste kokkuvõte
        System.out.println("Nimi: " + nimi);
        System.out.println("Tunnihind: " + tunnihind + " €");
        System.out.print("Ained: " + String.join(", ", ained));
        System.out.println();
        System.out.print("Õppeastmed: " + String.join(", ", õppeastmed));
        System.out.println();
        System.out.print("Tunniajad: " + String.join(", ", tunniajad));
        System.out.println();

        try {
            List<Õpetaja> olemasolevadÕpetajad = loeÕpetajadFailist();
            Õpetaja uusÕpetaja = leiaVõiLooÕpetaja(olemasolevadÕpetajad, nimi, tunnihind, ained, õppeastmed, tunniajad);
            if (!olemasolevadÕpetajad.contains(uusÕpetaja)) {
                olemasolevadÕpetajad.add(uusÕpetaja); // Lisame uue õpetaja, kui ta ei ole juba nimekirjas
            }
            salvestaÕpetajadFaili(olemasolevadÕpetajad); // Salvestame kõik õpetajad (uuendatud või uued) tagasi faili
        } catch (IOException e) {
            System.err.println("Failiga töötamisel tekkis viga: " + e.getMessage());
        }
    }

    // Meetod olemasoleva õpetaja leidmiseks või uue loomiseks
    private Õpetaja leiaVõiLooÕpetaja(List<Õpetaja> õpetajad, String nimi, double tunnihind, List<String> ained, List<String> õppeastmed, List<String> tunniajad) {
        for (Õpetaja olemasolev : õpetajad) {
            if (olemasolev.getNimi().equalsIgnoreCase(nimi)) {
                // Uuendame olemasoleva õpetaja andmeid
                olemasolev.setTunnihind(tunnihind);
                olemasolev.setAined(new ArrayList<>(ained)); // Vajadusel täiendage loogikat, et lisada ainult uued ained
                olemasolev.setÕppeastmed(new ArrayList<>(õppeastmed)); // Sama õppeastmetele
                olemasolev.setTunniajad(new ArrayList<>(tunniajad)); // Ja tunniaegadele
                return olemasolev;
            }
        }
        // Kui õpetaja ei ole olemas, loome uue
        return new Õpetaja(nimi, tunnihind, ained, õppeastmed, tunniajad);
    }

    // Loetleb olemasolevad andmed failist
    private List<Õpetaja> loeÕpetajadFailist() throws IOException {
        List<Õpetaja> õpetajad = new ArrayList<>();
        Path path = Paths.get(vabadeAegadeFail);
        if (Files.exists(path)) {
            List<String> read = Files.readAllLines(path);
            for (String rida : read) {
                String[] osad = rida.split(",");
                String nimi = osad[0];
                double tunnihind = Double.parseDouble(osad[1]);
                List<String> ained = Arrays.asList(osad[2].split("/"));
                List<String> õppeastmed = Arrays.asList(osad[3].split("/"));
                List<String> tunniajad = new ArrayList<>(Arrays.asList(osad[4].split("/")));
                Õpetaja õpetaja = new Õpetaja(nimi, tunnihind, ained, õppeastmed, tunniajad);
                õpetajad.add(õpetaja);
            }
        }
        return õpetajad;
    }

    // Salvestab andmed faili
    private void salvestaÕpetajadFaili(List<Õpetaja> õpetajad) throws IOException {
        Path path = Paths.get(vabadeAegadeFail);
        List<String> kirjutatavadRead = new ArrayList<>();
        for (Õpetaja õpetaja : õpetajad) {
            String rida = õpetaja.getNimi() + "," +
                    õpetaja.getTunnihind() + "," +
                    String.join("/", õpetaja.getAined()) + "," +
                    String.join("/", õpetaja.getÕppeastmed()) + "," +
                    String.join("/", õpetaja.getTunniajad());
            kirjutatavadRead.add(rida);
        }
        Files.write(path, kirjutatavadRead);
    }
}
/* Tehtud lisad 19_03_24 Heili
1. Lisatud ajaformaat DateTimeFormatter "yyyy-MM-dd HH:mm", edaspidi kasutame läbivalt seda.
2. Meetod 'loeÕpetajadFailist' loeb olemasolevad andmed failist 'ÕpetajadVabadAjad.txt', konverteerib need 'Õpetaja' objektideks ja koostab nende põhjal listi.
3. Kui õpetaja on juba olemas (sama nimi), siis saab lisada infot (tunnihinna uuendamine, ained ja õppeastmed - uued ained ja õppeastmed lisatakse olemasolevatele, vältides dubleerimist, tunniajad)
4. Kui õpetaja on uus ja pole temanimelist veel failis, siis luuakse uus Õpetaja objekt ja lisatakse see andmete listi.
5. Kõik muudatused salvestab meetod 'salvestaÕpetajadFaili' tekstifaili õÕpetajadVabadAjad.txt'
 */
