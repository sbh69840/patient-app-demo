package admin.eoe.squad.patient;

/**
 * Created by admin on 26/06/2017.
 */

public class Patient {
    private String first_name,last_name,date,weeks;
    private int weeks1;
    private String[] diet,medicines,supplements;
    public Patient(){

    }
    public Patient(String first_name,String last_name){
        this.first_name=first_name;
        this.last_name = last_name;

    }
    public Patient(String date){
        this.date=date;
    }
    public Patient(String weeks,int weeks1){
        this.weeks=weeks;
        this.weeks1=weeks1;
    }
    public Patient(String[] diet,int dietint){
        this.diet = diet;
    }
    public Patient(String[] medicines,long medicineslong){
        this.medicines = medicines;
    }
    public Patient(String[] supplements,double medicineslong){
        this.supplements = supplements;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String[] getSupplements() {
        return supplements;
    }

    public String[] getMedicines() {

        return medicines;
    }

    public String[] getDiet() {

        return diet;
    }

    public String getWeeks() {

        return weeks;
    }

    public String getDate() {
        return date;
    }

    public String getLast_name() {
        return last_name;
    }
}
