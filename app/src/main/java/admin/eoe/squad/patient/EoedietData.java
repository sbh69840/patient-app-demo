package admin.eoe.squad.patient;

/**
 * Created by admin on 19/07/2017.
 */

public class EoedietData {
    private String name;
    private String start;
    private String reintroduced;
    public EoedietData(){

    }
    public EoedietData(String name,String start,String reintroduced){
        this.name=name;
        this.start=start;
        this.reintroduced=reintroduced;
    }

    public String getName() {
        return name;
    }

    public String getStart() {
        return start;
    }

    public String getReintroduced() {
        return reintroduced;
    }
}
