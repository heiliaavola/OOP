import java.util.Scanner;

public class ÕpilaneAjalugu implements TeineSisend{
    public void sisesta(Scanner scanner) {
        String nimi;

        // Nime sisestus ja kinnitus
        do {
            System.out.println("Mis Su nimi on? vastus: ");
            nimi = scanner.nextLine();
            System.out.println("Sisestasid nimeks: " + nimi + ". Kas see on õige? (jah/ei)");
        } while (!scanner.nextLine().equalsIgnoreCase("jah"));

        // Siin on vaja välja kutsuda meetod, mis näitab kõiki varasemaid broneeringuid
        // ning konkreetse õpilase kohta faili sisu välja printida
    }

    // Siia on vaja kirjeldada meetod, mis näitab kõiki varasemaid broneeringuid
    // Kuupäev - kell - õpetaja - aine - õpilane
}
