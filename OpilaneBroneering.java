import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
public class ÕpilaneBroneering implements TeineSisend{
    public void sisesta(Scanner scanner){
        String nimi;
        String kooliAste;
        String aine;
        int tundideArv;
        List<String> lubatudAined = Arrays.asList("matemaatika", "eesti keel", "keemia", "füüsika");
        List<String> lubatudKooliAstmed = Arrays.asList("gümnaasium", "põhikool");

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

        // Aine sisestus ja kontroll
        do {
            System.out.println("Mis aine [matemaatika/eesti keel/keemia/füüsika] tundi soovid? vastus: ");
            aine = scanner.nextLine().toLowerCase();
            if (!lubatudAined.contains(aine)) {
                System.out.println("Vigane sisestus, vali üks etteantud ainetest.");
            }
        } while (!lubatudAined.contains(aine));

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

        //Anett: Siia peaks tegema ka uue õpilase klassi faili ja sealt saadud meetodi abil salvestama õpilase broneeringud
        //IF ÕPILANE NOT IN ÕPILASTE BRONEERINGUTE FAIL
        //ÕPILASE AINED PEAKS OLEMA LISTINA
        Õpilane praegune_õpilane = new Õpilane(nimi, kooliAste, aine);



        // Selene: Siia teeme koodi, mis näitab õige aine õpetajate aegu ja hindu
        // Selene: Seejärel küsime ükshaaval õpetaja+tema aja valimist nii mitu korda kui sisestati tundide arvuks
        // Selene: Viimaks võiks ekraanile panna summa ja selle mingisse broneeringu kokkuvõttesse salvestada (hiljem arve jaoks vaja)

        // Hetkel näitab see kõikide sisestuste andmeid, lisame siia hiljem ka teised vastused
        System.out.println("Nimi: " + nimi);
        System.out.println("Õpib kooliastmes: " + kooliAste);
        System.out.println("Aine: " + aine);
        System.out.println("Tundide arv: " + tundideArv);
        System.out.println("Valitud õpetajad+ajad: puudu"); // Siia lisada sisu, kui see on valmis
        System.out.println("Broneeringu summa: puudu"); // Siia lisada sisu, kui see on valmis

        // Siia vaja koodi, mis kustutab õpetaja vabade aegade failist broneeritud read ära
        // ÕpetajadVabadAjad: Kuupäev - kell - õpetaja - tunnihind - kõik ained - õppeastmed

        // Siia vaja koodi, mis lisab broneeritud ajad õpetajate broneeringute faili
        // ÕpetajadBroneeritud: Kuupäev - kell - õpetaja - tunnihind - aine - õppeaste - õpilane

        // Siia vaja koodi, mis lisab broneeritud ajad õpilaste broneeringute ajaloo faili
        // ÕpilasedBroneeringud: Kuupäev - kell - õpetaja - aine - õpilane

        // PS! Need faili struktuurid on ettepanekud, kui teil parem idee, siis andke teada
    }



}
