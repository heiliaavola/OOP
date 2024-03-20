import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ÕpilaneBroneering implements TeineSisend {

    private final String vabadeAegadeFail = "ÕpetajadVabadAjad.txt";
    private final String õpilasteBroneeringuteFail = "ÕpilasedBroneeringud.txt";

    public void sisesta(Scanner scanner) {
        String nimi;
        String kooliAste;
        String aine;
        List<String> ained = new ArrayList<>();
        int tundideArv;
        List<String> lubatudAined = Arrays.asList("matemaatika", "eesti keel", "keemia", "füüsika");
        List<String> lubatudKooliAstmed = Arrays.asList("gümnaasium", "põhikool");
        String valitudÕpetajaNimi;
        boolean õpetajaValitud = false;

        // Nime sisestus ja kinnitus
        do {
            System.out.println("Mis Su nimi on? vastus: ");
            nimi = scanner.nextLine();
            System.out.println("Sisestasid nimeks: " + nimi + ". Kas see on õige? (jah/ei)");
        } while (!scanner.nextLine().equalsIgnoreCase("jah"));

        // Kooliastme sisestus ja kontroll
        do {
            System.out.println("Sisesta oma kooliaste [gümnaasium/põhikool] vastus: ");
            kooliAste = scanner.nextLine().toLowerCase();
            if (!lubatudKooliAstmed.contains(kooliAste)) {
                System.out.println("Vigane sisestus, palun vali kas 'gümnaasium' või 'põhikool'.");
            }
        } while (!lubatudKooliAstmed.contains(kooliAste));


        // Ainete sisestus ja automaatkontroll
        System.out.println("Sisesta ükshaaval milliseid õppeaineid õppida soovid? [matemaatika/eesti keel/keemia/füüsika] vastus: ");
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

        // Tundide arvu sisestus ja kinnitus
        do {
            System.out.println("Mitut 45 minutilist tundi broneerida soovid? vastus: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Palun sisesta täisarv:");
                scanner.next(); // Ignoreerib vale sisendi
            }
            tundideArv = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Sisestasid tundide arvuks: " + tundideArv + ". Kas see on õige? (jah/ei) vastus: ");
        } while (!scanner.nextLine().equalsIgnoreCase("jah"));


        // Hetkel näitab see kõikide sisestuste andmeid, lisame siia hiljem ka teised vastused - Anett 20.03: puudu veel ainult kogukulu
        System.out.println("Nimi: " + nimi);
        System.out.println("Õpib kooliastmes: " + kooliAste);
        System.out.print("Ained: " + String.join(", ", ained)); //ÜHE AINE PUHUL SIIA REAVAHETUST VAJA
        System.out.println("\nTundide arv: " + tundideArv);

        //Anett 19.03 + 20.03
        Õpilane praeguneÕpilane = new Õpilane(nimi, kooliAste, ained);

        //1.Leiame õpilasele sobivad õpetajad

        //1.a) Loetleme kõik olemasolevad õpetajad ja nende tunnid Failist Listi olemasolevadÕpetajad
        List<Õpetaja> olemasolevadÕpetajad = new ArrayList<>();
        try {
            olemasolevadÕpetajad = loeÕpetajadFailist();
        } catch (IOException e) {
            System.err.println("Failiga töötamisel tekkis viga: " + e.getMessage());
        }

        //1.b) Filtreerime sealt välja õpetajad, kes õpetavad sobivat kooliastet, sobivaid aineid ja kellel on piisavalt vabu aegu
        //Salvestame nad esmalt Listi sobivad õpetajad
        List<Õpetaja> sobivadÕpetajad = new ArrayList<>();
        for (Õpetaja õpetaja : olemasolevadÕpetajad) {
            if (õpetaja.getAined().containsAll(praeguneÕpilane.ained) && õpetaja.getÕppeastmed().contains(praeguneÕpilane.kooliAste) && õpetaja.getTunniajad().size() >= tundideArv) {
                sobivadÕpetajad.add(õpetaja);
            }
        }

        System.out.println("Sobivaks osutuvad õpetajad: ");
        for (Õpetaja õpetaja : sobivadÕpetajad) {
            System.out.println(õpetaja.getNimi() + "(tunnihind: " + õpetaja.getTunnihind() + "€)");
            System.out.println("Saadaolevad tunnid:");
            for (String aeg : õpetaja.getTunniajad()) {
                System.out.println(aeg);
            }
        }
        // 2. Õpetaja valik ja kinnitus
        do {
            System.out.println("Millise õpetaja valid? (Sisesta nimi) ");
            valitudÕpetajaNimi = scanner.nextLine();
            List<String> sobivateÕpetajateNimed = new ArrayList<>();
            for (Õpetaja õpetaja : sobivadÕpetajad) {
                sobivateÕpetajateNimed.add(õpetaja.getNimi());
            }
            if (!sobivateÕpetajateNimed.contains(valitudÕpetajaNimi)) {
                System.out.println("Sellist õpetajat ei leitud. Palun vali sobiv õpetaja.");
            } else {
                System.out.println("Sisestasid nimeks: " + valitudÕpetajaNimi + ". Kas see on õige? (jah/ei)");
                õpetajaValitud = scanner.nextLine().equalsIgnoreCase("jah");
            }
        } while (!õpetajaValitud);

        //3. Tunniaegade valik
        System.out.println("Vali ÜKSHAAVAL koos kuupäevaga endale sobivad ajad soovitud õpetajalt: ");
        System.out.println("Saadavalolevad ajad: ");
        Õpetaja valitudÕpetaja = null;
        List<String> valitudÕpetajaSaadavalTunniajad = new ArrayList<>();
        for (Õpetaja õpetaja : sobivadÕpetajad) {
            if (õpetaja.getNimi().equalsIgnoreCase(valitudÕpetajaNimi)) {
                valitudÕpetaja = õpetaja;
                for (String aeg : valitudÕpetaja.getTunniajad()) {
                    System.out.println(aeg);
                    valitudÕpetajaSaadavalTunniajad.add(aeg);
                }
                break;
            }
        }
        List<String> õpilaseValitudAjad = new ArrayList<>();
        for (int i = 0; i < tundideArv; i++) {
            String aeg = scanner.nextLine();
            if (valitudÕpetajaSaadavalTunniajad.contains(aeg)) { // Lisame kontrolli, kas antud aeg on saadaval
                õpilaseValitudAjad.add(aeg);
                valitudÕpetajaSaadavalTunniajad.remove(aeg); // Eemaldame valitud aja saadavalolevate aegade hulgast
                System.out.println("Aeg valitud!");
                if (i != tundideArv-1){
                System.out.println("Palun vali järgmine sobiv aeg: ");
                }
            } else {
                System.out.println("Antud aega pole saadaval. Palun vali sobiv aeg uuesti.");
                i--; // Vähendame loendurit, et õpilane saaks uuesti sisestada
            }
        }
        System.out.println("Valisid ajad: ");
        for (String s : õpilaseValitudAjad) {
            System.out.println(s);
        }

        //4. Salvestame õpilase broneeringu faili:
        try {
            salvestaÕpilasteBroneeringudFaili(praeguneÕpilane,valitudÕpetaja, õpilaseValitudAjad); //
        } catch (IOException e) {
            System.err.println("Failiga töötamisel tekkis viga: " + e.getMessage());
        }


        //System.out.println("Õpetajale jäid veel alles ajad: ");
        //for (String s : valitudÕpetajaSaadavalTunniajad) {
        //    System.out.println(s);
        //}

        //5. Uuendame faili ÕpetajadVabadAjad ehk eemaldame sealt selle broneeringu käigus broneeritud ajad:
        //5.a) Esmalt uuendame listi olemasolevadÕpetajad
        for (Õpetaja õpetaja : olemasolevadÕpetajad) {
            if (valitudÕpetaja.equals(õpetaja)) {
                if (valitudÕpetajaSaadavalTunniajad.isEmpty()) {
                    olemasolevadÕpetajad.remove(õpetaja); // Eemaldame õpetaja täielikult, kui saadavalolevate tundide loend on tühi - õpetajal said ajad otsa
                } else {
                    õpetaja.setTunniajad(valitudÕpetajaSaadavalTunniajad);
                }
                break; // Lõpeta pärast leidmise esinemist
            }
        }
        //5.b) kirjutame faili ÕpetajadVabadAjad üle uuendatud õpetajate listiga
        try {
            salvestaÕpetajadFaili(olemasolevadÕpetajad); // Salvestame kõik õpetajad (uuendatud aegadega) tagasi faili
        } catch (IOException e) {
            System.err.println("Failiga töötamisel tekkis viga: " + e.getMessage());
        }


        System.out.println("Broneeringu summa: puudu"); // Siia lisada sisu, kui see on valmis

    }


    //MEETOD Õpetajate vabade aegade lugemiseks FAILIST LISTI
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

    // MEETOD Õpilaste broneeringute salvestamiseks faili ÕpilasedBroneeringud.txt
    //KUJUL: õpilase nimi, õpetaja nimi, tunnihind, tunniajad
    //NÄITEKS: Liina , Raul , 20.0 , [2020-12-12 12:00]
    private void salvestaÕpilasteBroneeringudFaili (Õpilane praegune_õpilane, Õpetaja valitud_õpetaja, List<String> broneeritud_ajad) throws IOException {
        Path path = Paths.get(õpilasteBroneeringuteFail);
        String rida = praegune_õpilane.getNimi() + " , " + valitud_õpetaja.getNimi() + " , " + valitud_õpetaja.getTunnihind() + " , " + broneeritud_ajad;
        Files.write(path, Collections.singletonList(rida), StandardOpenOption.APPEND); // Kasutame APPEND-i, et lisada uued andmed faili lõppu
    }

    //MEETOD ÕpetajateVabadAjad faili üle kirjutamiseks, et salvestada sinna ajad, kust on eemaldatud broneeringu käigus broneeritud ajad
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

    //MEETOD Õpetaja ajaloo salvestamiseks
}


//ANETT 19.03 - 20.03
//Kirjutasin koodi, mille põhjal luuakse ÕpilaneBroneering klassis õpilase fail
//ÕpetajadVabadAjad failist loetakse sisse andmed õpetajate ja nende vabade aegade kohta ja salvestatakse need listi olemasolevadÕpetajad (loeÕpetajadFailist) - sama meetod, mis Heili kirjutas ÕpetajaSisestus klassi
//Seejärel võrreldakse õpilase sisestatud soove olemasolevate õpetajate tundide jm andmetega ja luuakse list sobivadõpetajad
//Sealt väljastatakse nii sobivate õpetajate nimed, tunnihinnad kui ka saadavalolevad ajad kasutajale (ehk broneerivale õpilasele)
//NB! Siit veel täielikult puudu see osa, et mis saab, kui pole ühtegi sobivat õpetajat
//Broneerijal lastakse valida endale sobiv õpetajad ja ajad
//Seejärel loetakse broneeringuandemd faili ÕpilaneBroneering (igal real uus broneering formaadis: õpilase nimi, õpetaja nimi, tunnihind, tunniajad) - selle jaoks siin uus meetod
//Uuendatakse listi olemasolevadÕpetajad, eemaldades sealt broneeritud ajad (kui õpetajal saavad ajad otsa, siis eemaldatakse õpetaja täielikult, muidu läks kood järgmisel jooksutamisel katki)
//Fail ÕpetajadVabadAjad kirjutatakse üle listi olemasolevadÕpetajad põhjal - sama meetod (salvestaÕpetajadFaili), mis Heili kirjutas ÕpetajaSisestus klassis

// Siit puudu veel MEETOD Õpetaja ajaloo salvestamiseks ja kulude arvestus