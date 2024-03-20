import java.util.List;

public class Õpilane {
    String nimi;
    String kooliAste;
    List<String> ained;

    public Õpilane(String nimi, String kooliAste, List<String> ained) {
        this.nimi = nimi;
        this.kooliAste = kooliAste;
        this.ained = ained;
    }

    public String getNimi() {
        return nimi;
    }

    public String getKooliAste() {
        return kooliAste;
    }

    public List<String> getAined() {
        return ained;
    }
}
