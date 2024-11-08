package bo;

import java.util.Vector;
import dao.MentorHandler;


public class Mentor {

    private int samID;
    private String name;
    private String email;
    private String date_of_birth;
    private String username;
    private String password;

    public Mentor(int samID, String name, String email, String date_of_birth, String username, String password){
        this.samID = samID;
        this.name = name;
        this.email = email;
        this.date_of_birth = date_of_birth;
        this.username = username;
        this.password = password;
    }

       public Vector<Object> getRow(){
        Vector<Object> ret = new Vector<>();
        ret.add(this.samID);
        ret.add(this.name);
        ret.add(this.email);
        ret.add(this.date_of_birth);
        ret.add(this.username);
        ret.add(this.password);
        return ret;
    }
    
    public int getsamID() {
        return samID;
    }

    public void setsamID(int samID) {
        this.samID = samID;
    }

    public String getname() {
        return name;
    }

    public void setname(String mtrName) {
        this.name = mtrName;
    }
    public String getdate_of_birth(){
        return date_of_birth;
    }

    public void setdate_of_birth(String date_of_birth){
        this.date_of_birth=date_of_birth;
    }
    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }
    
   
}
