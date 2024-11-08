package dao;

import utils.SQLUtil;
import bo.Mentor;
import bo.Student;
import dao.StudentHandler;
import utils.PasswordEncrypter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PasswordEncrypter;
//hi
public class MentorHandler {
    private SQLUtil sqlUtil;

    public MentorHandler() {
     this.sqlUtil = new SQLUtil();
    } 

    public int addMentor(int samID, String name, String email, String date_of_birth, String username, String password){
        PasswordEncrypter encrypter = new PasswordEncrypter();
        String peeTemplate = "INSERT INTO person (samID, name, email, date_of_birth) VALUES (?,?,?,?)";
        //String pee = String.format(peeTemplate, samID, name, email, date_of_birth);
        String cmdTemplate = "INSERT INTO mentor (samID , username, password) VALUES (?, ?, ?)";
        //String cmd = String.format(cmdTemplate, samID, Major, Minor, gpa, mtrID);
        String encryptedPass = password;
        encryptedPass = encrypter.encryptPassword(password);
        try (PreparedStatement personStatement = sqlUtil.getConnection().prepareStatement(peeTemplate); 
             PreparedStatement mentorStatement = sqlUtil.getConnection().prepareStatement(cmdTemplate);) {
            //parameters to insert into person table
            personStatement.setInt(1, samID);
            personStatement.setString(2, name);
            personStatement.setString(3, email);
            personStatement.setString(4, date_of_birth);

            //parameters to insert into mentor table
            mentorStatement.setInt(1, samID);
            mentorStatement.setString(2, username);
            mentorStatement.setString(3, encryptedPass);
            //insertion transaction
            sqlUtil.getConnection().setAutoCommit(false);
            personStatement.executeUpdate();
            mentorStatement.executeUpdate();
            sqlUtil.getConnection().commit();
            sqlUtil.getConnection().setAutoCommit(true);
            return 1;//mark success
        } catch (SQLException ex) {
            Logger.getLogger(MentorHandler.class.getName()).log(Level.SEVERE, null, ex);
            return 0;//mark failure 
        }
    }
    public int updatementor(int samID, String name, String email, String date_of_birth, String username, String password) {
        PasswordEncrypter encrypter  = new PasswordEncrypter();
        String peeTemplate = "UPDATE person SET name=?, email =?, date_of_birth=? WHERE samID=?";
        //String pee = String.format(peeTemplate, samID, name, email, date_of_birth);
        String cmdTemplate = "UPDATE mentor SET username=?, password=? WHERE samID = ?";
        //String cmd = String.format(cmdTemplate, samID, Major, Minor, gpa, mtrID);
        String encryptedPassword = encrypter.encryptPassword(password);
        try (PreparedStatement personStatement = sqlUtil.getConnection().prepareStatement(peeTemplate); 
             PreparedStatement mentorStatement = sqlUtil.getConnection().prepareStatement(cmdTemplate);) {
            //parameters to update person table
            
            personStatement.setString(1, name);
            personStatement.setString(2, email);
            personStatement.setString(3, date_of_birth);
            personStatement.setInt(4, samID);
            //parameters to update mentor table
             
             mentorStatement.setString(1, username);
             mentorStatement.setString(2, encryptedPassword);
             mentorStatement.setInt(3,samID);

            //update transaction
            sqlUtil.getConnection().setAutoCommit(false);
            personStatement.executeUpdate();
            mentorStatement.executeUpdate();
            sqlUtil.getConnection().commit();
            return 1;//mark success
        } catch (SQLException ex) {
            Logger.getLogger(MentorHandler.class.getName()).log(Level.SEVERE, null, ex);
            return 0;//mark failure 
        }
    }

    public List<Mentor> loadMentors(String keywordmtr) {
        
        List<Mentor> mentors = new ArrayList<>();
        String cmdTemplate = "SELECT person.samID, person.name, person.email, person.date_of_birth, mentor.username, mentor.password "
                + "FROM person "
                + "INNER JOIN mentor ON person.samID = mentor.samID "
                + "WHERE person.name LIKE '%s'";
        
        try (PreparedStatement stmt = sqlUtil.getConnection().prepareStatement(cmdTemplate)) 
        {
            String cmd = String.format(cmdTemplate, "%" + keywordmtr + "%");
            ResultSet rs = sqlUtil.executeQuery(cmd);
            while (rs.next()) {
                int samID = rs.getInt("samID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String date_of_birth = rs.getString("date_of_birth");
                String username = rs.getString("username");
                String password = rs.getString("password");
              
                Mentor mentor = new Mentor(samID, name, email, date_of_birth, username, password);
                mentors.add(mentor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MentorHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mentors;
    }

    public int deletementor(int samID) {
        String cmdTemplate ="Delete from mentor where samID = %s" ;
        String cmd = String.format(cmdTemplate, samID);
        return sqlUtil.executeUpdate(cmd);
    }


    public List<Mentor> getmentors(String keyword) {
        List<Mentor> mentors = new ArrayList<>();
        String cmdTemplate = "SELECT s.samID, p.name, p.email, p.date_of_birth, s.Major, s.Minor, s.gpa, s.mtrID"
                + "FROM mentor s "
                + "INNER JOIN person p on s.samID = p.samID";

        try (PreparedStatement preparedStatement = sqlUtil.getConnection().prepareStatement(cmdTemplate); ResultSet rs = preparedStatement.executeQuery()) {
            int samID = rs.getInt("samID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String date_of_birth = rs.getString("date_of_birth");
                String username = rs.getString("username");
                String password = rs.getString("password");

            Mentor mentor = new Mentor(samID, name, email, date_of_birth, username, password);
            mentors.add(mentor);
        } catch (SQLException ex) {
            Logger.getLogger(MentorHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mentors; 
     

    }
    

    public Mentor login(String username, String password) {
        Mentor mentor = null;
        //Encryption
        password = PasswordEncrypter.encryptPassword(password);
        String stm = String.format("SELECT * from mentor JOIN person WHERE username='%s' AND password='%s'", username, password);
        ResultSet rsMentor = sqlUtil.executeQuery(stm);

        try {
            if (rsMentor != null && rsMentor.next()) {
                int mtrId = rsMentor.getInt("samID");
                String mtrName = rsMentor.getString("name");
                String email = null;
                String date_of_birth=rsMentor.getString("date_of_birth");
                mentor = new Mentor(mtrId, mtrName, email, date_of_birth, username, password);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mentor;
    }
   
    
}
