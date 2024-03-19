import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ÕpetajaSisestus implements TeineSisend {
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

        // Tunniaegade sisestus ja kinnitus
        System.out.println("Sisesta ÜKSHAAVAL oma tundide algusajad, arvestades, et üks tund = 45 min (formaadis HH:MM) vastus: ");
        while (!(tunniaeg = scanner.nextLine()).equalsIgnoreCase("valmis")) {
            tunniajad.add(tunniaeg);
            System.out.println("Tunniaeg lisatud. Sisesta järgmine aeg või 'valmis': ");
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

        // FAILI LUGEMISE MEETOD: 1. LOO TÜHI ÕPETAJA TÜÜPI LIST 2. LOO IGA REA KOHTA UUS ÕPETAJA OBJEKT 3. LISA IGA ÕPETAJA OBJEKT LISTI
        // new Array<Õpetaja(nimi + aine + ajad+...)> õpetajad
        //FAIL ÕPETAJATE_SISU: "Anu Saun", põhikool, matemaatika/keemia, [10.04.2024 12.15]/13.00/13.45
        // loe kõik failis olevad andmed listi õpetajad FAILIST KÕIK VARASEM -> LISTI
        //LIST õpetajad[{Õpetaja1_nimi, Õpetaja1_ained, Õpetaja1_ajad}, {Õpetaja2_nimi, Õpetaja2_ained, Õpetaja2_ajad}]
        //for (õpetaja: õpetajad){
        //    nimi = õpetaja.getnimi();
        //    ...
        //}

        //Teeb uue Õpetaja Objekti (õpetaja klassi objekt) sisestuse põhjal
        //Kui listis on juba see õpetaja, ss lisab õpetajale ajad/ained
        //Kui ei ole seda õpetajat listis, siis paneb uue objekti listi
        //KUI SISESTUS LÄBI, siis kirjutab faili ÕPETAJATE_INFO üle
        //Õpetaja klassis meetod kirjuta faili - selle põhjal praegune sisestus õpetaja_sisestus faili


        // Siia on vaja koodi, mis salvestab kõik sisestused vabade aegade teksti faili
        // Ma arvan, et failis võiks igale reale salvestada ühe aja, koos õpetaja muu infoga
        // ÕpetajadVabadAjad: Kuupäev - kell - õpetaja - tunnihind - kõik ained - õppeastmed
        // siis ei teki probleemi uute aegade sisestamisega
    }

}

