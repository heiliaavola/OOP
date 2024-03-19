import java.util.Scanner;

public class Pealeht {
    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        String roll;
        String tegevus;

        // Rolli sisestus ja kontroll
        do {
            System.out.println("Kas oled [õpilane/õpetaja]? vastus:");
            roll = scanner.nextLine().toLowerCase(); // Muudame sisestuse väiketähtedeks, et tagada tõstutundetus
            if (!roll.equals("õpilane") && !roll.equals("õpetaja")) {
                System.out.println("Vigane sisestus. Vasta uuesti.");
            }
        } while (!roll.equals("õpilane") && !roll.equals("õpetaja"));

        // Tegevuse sisestus ja kontroll
        if (roll.equals("õpilane")) {
            do {
                System.out.println("Kas [broneering/ajalugu]? vastus:");
                tegevus = scanner.nextLine().toLowerCase(); // Sama tõstutundetuse tagamiseks
                if (!tegevus.equals("broneering") && !tegevus.equals("ajalugu")) {
                    System.out.println("Vigane sisestus. Vasta uuesti.");
                }
            } while (!tegevus.equals("broneering") && !tegevus.equals("ajalugu"));
        } else { // Õpetaja
            do {
                System.out.println("Kas [sisestus/ajalugu]? vastus:");
                tegevus = scanner.nextLine().toLowerCase();
                if (!tegevus.equals("sisestus") && !tegevus.equals("ajalugu")) {
                    System.out.println("Vigane sisestus. Vasta uuesti.");
                }
            } while (!tegevus.equals("sisestus") && !tegevus.equals("ajalugu"));
        }

        // Kiire kinnitus ekraanile, mis siiani sisestatud on
        System.out.println("(Sinu sisestatud andmed) Roll: " + roll + ", Tegevus: " + tegevus);


        // Siin luuakse rollile ja tegevusele vastavalt RollTegevus klass
        // Nende järgi saab edaspidi küsida õiget küsimusteplokki
        TeineSisend teineSisend = null;
        if (roll.equals("õpilane") && tegevus.equals("broneering")) {
            teineSisend = new ÕpilaneBroneering();
        } else if (roll.equals("õpilane") && tegevus.equals("ajalugu")){
            teineSisend = new ÕpilaneAjalugu();
        } else if (roll.equals("õpetaja") && tegevus.equals("sisestus")){
            teineSisend = new ÕpetajaSisestus();
        } else if (roll.equals("õpetaja") && tegevus.equals("ajalugu")){
            teineSisend = new ÕpetajaAjalugu();
        }

        if (teineSisend != null) {
            teineSisend.sisesta(scanner); // RollTegevus klassist küsimuste küsimine
        } else {
            System.out.println("Tundmatu roll või tegevus."); // kui roll/tegevus on valesti sisestatud
        }


    }

}
