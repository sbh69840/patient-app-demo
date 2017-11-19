package admin.eoe.squad.patient;

/**
 * Created by admin on 22/07/2017.
 */

public class MedicationData {
    private String name;
    private String quantity;
    private String time;
    private String date;
    public MedicationData(){
    }
    public MedicationData(String name,String quantity,String time,String date){
        this.name=name;
        this.quantity=quantity;
        this.time = time;
        this.date = date;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;

    }
}
