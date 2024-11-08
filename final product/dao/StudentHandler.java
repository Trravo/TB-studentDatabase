package dao;

import utils.SQLUtil;
import bo.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentHandler {

    private SQLUtil sqlUtil;

    public StudentHandler() {
        sqlUtil = new SQLUtil();
    }
 
    public int addStudent(int samID, String name, String email, String date_of_birth, String Major, String Minor, double gpa, int mtrID) {
        String peeTemplate = "INSERT INTO person (samID, name, email, date_of_birth) VALUES (?,?,?,?)";
        //String pee = String.format(peeTemplate, samID, name, email, date_of_birth);
        String cmdTemplate = "INSERT INTO Student (samID , Major, Minor, gpa, mtrID) VALUES (?, ?, ?, ?, ?)";
        //String cmd = String.format(cmdTemplate, samID, Major, Minor, gpa, mtrID);
        try (PreparedStatement personStatement = sqlUtil.getConnection().prepareStatement(peeTemplate); PreparedStatement studentStatement = sqlUtil.getConnection().prepareStatement(cmdTemplate);) {
            //parameters to insert into person table
            personStatement.setInt(1, samID);
            personStatement.setString(2, name);
            personStatement.setString(3, email);
            personStatement.setString(4, date_of_birth);

            //parameters to insert into Student table
            studentStatement.setInt(1, samID);
            studentStatement.setString(2, Major);
            studentStatement.setString(3, Minor);
            studentStatement.setDouble(4, gpa);
            studentStatement.setInt(5, mtrID);

            //insertion transaction
            sqlUtil.getConnection().setAutoCommit(false);
            personStatement.executeUpdate();
            studentStatement.executeUpdate();
            sqlUtil.getConnection().commit();
            sqlUtil.getConnection().setAutoCommit(true);
            return 1;//mark success
        } catch (SQLException ex) {
            Logger.getLogger(StudentHandler.class.getName()).log(Level.SEVERE, null, ex);
            return 0;//mark failure 
        }
    }
  public int updateStudent(int samID, String name, String email, String date_of_birth, String Major, String Minor, double gpa, int mtrID) {
    String peeTemplate = "UPDATE person SET name=?, email=?, date_of_birth=? WHERE samID=?";
    String cmdTemplate = "UPDATE student SET Major=?, Minor=?, GPA=?, mtrID=? WHERE samID=?";
    try (PreparedStatement personStatement = sqlUtil.getConnection().prepareStatement(peeTemplate);
         PreparedStatement studentStatement = sqlUtil.getConnection().prepareStatement(cmdTemplate)) {
        // Parameters to update person table
        personStatement.setString(1, name);
        personStatement.setString(2, email);
        personStatement.setString(3, date_of_birth);
        personStatement.setInt(4, samID);

        // Parameters to update student table
        studentStatement.setString(1, Major);
        studentStatement.setString(2, Minor);
        studentStatement.setDouble(3, gpa);
        studentStatement.setInt(4, mtrID);
        studentStatement.setInt(5, samID);

        // Insertion transaction
        sqlUtil.getConnection().setAutoCommit(false);
        personStatement.executeUpdate();
        studentStatement.executeUpdate();
        sqlUtil.getConnection().commit();
        return 1; // Mark success
    } catch (SQLException ex) {
        Logger.getLogger(StudentHandler.class.getName()).log(Level.SEVERE, null, ex);
        return 0; // Mark failure
    }
}

    public List<Student> loadStudents(String keyword) {

        List<Student> students = new ArrayList<>();
        String cmdTemplate = "SELECT person.samID, person.name, person.email, person.date_of_birth, student.Major, student.Minor, student.gpa, student.mtrID "
                + "FROM person "
                + "INNER JOIN student ON person.samID = student.samID "
                + " WHERE person.name like '%s'";

        String cmd = String.format(cmdTemplate, "%" + keyword + "%");
        ResultSet rs = sqlUtil.executeQuery(cmd);
        try {
            while (rs != null && rs.next()) {
                int samID = rs.getInt("samID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String date_of_birth = rs.getString("date_of_birth");
                String Major = rs.getString("Major");
                String Minor = rs.getString("Minor");
                double gpa = rs.getDouble("gpa");
                int mtrID = rs.getInt("mtrID");
              
                Student student = new Student(samID, name, email, date_of_birth, Major, Minor, gpa, mtrID);
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;
    }

    public int deleteStudent(int samID) {
      
        String cmdTemplate ="DELETE from person where samID=%s" ;
        String cmd = String.format(cmdTemplate, samID);
        return sqlUtil.executeUpdate(cmd);
    }

    public List<Student> getStudents(String keyword) {
        List<Student> students = new ArrayList<>();
        String cmdTemplate = "SELECT s.samID, p.name, p.email, p.date_of_birth, s.Major, s.Minor, s.gpa, s.mtrID"
                + "FROM Student s "
                + "INNER JOIN person p on s.samID = p.samID";

        try (PreparedStatement preparedStatement = sqlUtil.getConnection().prepareStatement(cmdTemplate); ResultSet rs = preparedStatement.executeQuery()) {
            int samID = rs.getInt("samID");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String date_of_birth = rs.getString("date_of_birth");
            String Major = rs.getString("Major");
            String Minor = rs.getString("Minor");
            double gpa = rs.getDouble("gpa");
            int mtrID = rs.getInt("mtrID");

            Student student = new Student(samID, name, email, date_of_birth, Major, Minor, gpa, mtrID);
            students.add(student);
        } catch (SQLException ex) {
            Logger.getLogger(StudentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;
    }

}
