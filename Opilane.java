import java.util.List;

public class Õpilane {
    private String nimi;
    String kooliAste;

    public String getNimi() {
        return nimi;
    }

    public String getKooliAste() {
        return kooliAste;
    }

    public String getAine() {
        return aine;
    }

    String aine;

    public Õpilane(String nimi, String kooliAste, String aine) {
        this.nimi = nimi;
        this.kooliAste = kooliAste;
        this.aine = aine;
    }


}
