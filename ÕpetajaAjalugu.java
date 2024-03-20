import java.util.Scanner;
//MUUTSIN KOODI2!!

public class ÕpetajaAjalugu implements TeineSisend{
    public void sisesta(Scanner scanner) {
        String nimi;

        // Nime sisestus ja kinnitus
        do {
            System.out.println("Mis Su nimi on? vastus: ");
            nimi = scanner.nextLine();
            System.out.println("Sisestasid nimeks: " + nimi + ". Kas see on õige? (jah/ei)");
        } while (!scanner.nextLine().equalsIgnoreCase("jah"));

        // Siin vaja välja kutsuda 2 meetodit,
        // üks, mis loeb failist kõiki tehtud broneeringuid
        // teine, mis loeb failist kõiki vabu aegu
        // ning konkreetse õpetaja kohta failide sisu välja printida
    }

    // Siia on vaja kirjeldada 2 meetodit, mis näitavad kõiki varasemaid broneeringuid + allesolevaid aegu
    // Broneeritud: Kuupäev - kell - õpilane - aine
    // Vabad: Kuupäev - kell

    // Siia vaja meetodit arve koostamiseks
}
