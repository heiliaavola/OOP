import java.util.List;

public class Õpetaja {
    private String nimi;
    private double tunnihind;

    private List<String> ained;
    private List<String> õppeastmed;
    private List<String> tunniajad;

    // Konstruktor
    public Õpetaja(String nimi, double tunnihind, List<String> ained, List<String> õppeastmed, List<String> tunniajad) {
        this.nimi = nimi;
        this.tunnihind = tunnihind;
        this.ained = ained;
        this.õppeastmed = õppeastmed;
        this.tunniajad = tunniajad;
    }

    // Getterid
    public String getNimi() {
        return nimi;
    }

    public double getTunnihind() {
        return tunnihind;
    }

    public List<String> getAined() {
        return ained;
    }

    public List<String> getÕppeastmed() {
        return õppeastmed;
    }

    public List<String> getTunniajad() {
        return tunniajad;
    }

    // Setterid (panin praegu kõik, hiljem vaatame, kas ja mida on vaja)
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setTunnihind(double tunnihind) {
        this.tunnihind = tunnihind;
    }

    public void setAined(List<String> ained) {
        this.ained = ained;
    }

    public void setÕppeastmed(List<String> õppeastmed) {
        this.õppeastmed = õppeastmed;
    }

    public void setTunniajad(List<String> tunniajad) {
        this.tunniajad = tunniajad;
    }

    @Override
    public String toString() {
        return "Õpetaja{" +
                "nimi='" + nimi + '\'' +
                ", tunnihind=" + tunnihind +
                ", ained=" + ained +
                ", õppeastmed=" + õppeastmed +
                ", tunniajad=" + tunniajad +
                '}';
    }


    public double broneeringu_maksumus(int tundide_arv){
        if (tundide_arv < 5){
            return (this.tunnihind * tundide_arv);
        }
        else{ //Iga 5. tund 10% odavam
            int soodusega_tunde = (int) Math.floor(tundide_arv/5);
            double maksumus = (tundide_arv - soodusega_tunde)*this.tunnihind + soodusega_tunde*(this.tunnihind*0.9);
            return maksumus;
        }
    }
}
