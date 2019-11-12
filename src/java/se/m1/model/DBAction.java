package se.m1.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static se.m1.model.Constants.*;
import se.m1.model.beans.Employee;
import se.m1.model.beans.User;

/**
 *
 * A set of methods to interact with the database
 */
public class DBAction {
    Connection conn;
    Statement stmt;
    ResultSet rs;
    ArrayList<User> listUsers;
    ArrayList<Employee> listEmployees;
    PreparedStatement st;
    Employee specificEmployee;
    User validUser;

    /**
     *  Constructor that creates a connection to the database
     */
    public DBAction() {
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    /**
     * Creates a Statement object to communicate with the database
     * @return A Statement object
     */
    public Statement getStatement() {
        try {
            stmt = conn.createStatement();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return stmt;

    }

    /**
     * Queries the database with the query provided as a parameter
     * @param query
     *      The query to execute
     * @return A ResultSet containing the lines matching the query
     */
    public ResultSet getResultSet(String query) {
        try {
            stmt = getStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return rs;
    }

    /**
     * Queries the database to retrieve all credentials
     * @return An ArrayList of the users registered in the database
     */
    public ArrayList<User> getUsers() {
        listUsers = new ArrayList<>();
        try {
            rs = getResultSet(QUERY_SELECT_CREDENTIALS);
            while (rs.next()) {
                User userBean = new User();
                userBean.setUsername(rs.getString("LOGIN"));
                userBean.setPassword(rs.getString("PWD"));
                userBean.setRank(rs.getString("RANK"));

                listUsers.add(userBean);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listUsers;
    }

    /**
     * Queries the database to retrieve all the employees
     * @return An ArrayList of all the employees in the database
     */
    public ArrayList<Employee> getEmployees() {
        listEmployees = new ArrayList<>();
        try {
            rs = getResultSet(QUERY_SELECT_ALL_EMPLOYEES);
            while (rs.next()) {
                Employee oneEmployee = new Employee();
                oneEmployee.setId(rs.getInt("ID"));
                oneEmployee.setName(rs.getString("NAME"));
                oneEmployee.setFirstname(rs.getString("FIRSTNAME"));
                oneEmployee.setHomePhone(rs.getString("TELHOME"));
                oneEmployee.setMobilePhone(rs.getString("TELMOB"));
                oneEmployee.setProPhone(rs.getString("TELPRO"));
                oneEmployee.setAddress(rs.getString("ADRESS"));
                oneEmployee.setPostalCode(rs.getString("POSTALCODE"));
                oneEmployee.setCity(rs.getString("CITY"));
                oneEmployee.setMail(rs.getString("EMAIL"));
                listEmployees.add(oneEmployee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listEmployees;
    }
    
    /**
     * Retrieves a specific employee using the ID provided as a parameter
     * @param employeeId
     *      The ID of the employee to retrieve
     * @return An Employee object matching the one requested via ID
     */
    public Employee getOneEmployee(int employeeId) {
        specificEmployee = new Employee();
        try {
            st = conn.prepareStatement(QUERY_SELECT_EMPLOYEE + employeeId);
            rs = st.executeQuery();
            rs.next();
            
            specificEmployee.setId(rs.getInt("ID"));
            specificEmployee.setName(rs.getString("NAME"));
            specificEmployee.setFirstname(rs.getString("FIRSTNAME"));
            specificEmployee.setHomePhone(rs.getString("TELHOME"));
            specificEmployee.setMobilePhone(rs.getString("TELMOB"));
            specificEmployee.setProPhone(rs.getString("TELPRO"));
            specificEmployee.setAddress(rs.getString("ADRESS"));
            specificEmployee.setPostalCode(rs.getString("POSTALCODE"));
            specificEmployee.setCity(rs.getString("CITY"));
            specificEmployee.setMail(rs.getString("EMAIL"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return specificEmployee;
    }

    /**
     * Verifies if the credentials submitted by the user match a registered
     * user in the database, and returns a User object if there is a match
     * @param userInput
     *      The credentials entered by the user
     * @return A User object defining what account the user has logged into
     */
    public User checkCredentials(User userInput) {
        listUsers = getUsers();
        validUser = null;

        for (User userBase : listUsers) {
            if (userBase.getUsername().equals(userInput.getUsername())
                    && userBase.getPassword().equals(userInput.getPassword())) {
                validUser = userBase;
            }
        }
        return validUser;
    }
    
    /**
     * Adds an employee to the database
     * @param aEmployee
     *      The employee to add to the database
     */
    public void AddEmployee(Employee aEmployee)
    {
        try {
            st = conn.prepareStatement(QUERY_ADD_EMPLOYEES);
            st.setString(1, aEmployee.getName());
            st.setString(2, aEmployee.getFirstname());
            st.setString(3, aEmployee.getHomePhone());
            st.setString(4, aEmployee.getMobilePhone());
            st.setString(5, aEmployee.getProPhone());
            st.setString(6, aEmployee.getAddress());
            st.setString(7, aEmployee.getPostalCode());
            st.setString(8, aEmployee.getCity());
            st.setString(9, aEmployee.getMail());
            
            st.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Removes an employee from the database
     * @param employeeId
     *      The ID of the employee to remove
     */
    public void deleteEmployee(int employeeId) {

        try {
            st = conn.prepareStatement(QUERY_DELETE_EMPLOYEE + employeeId);           
            st.execute();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Updates information on a specific employee
     * @param aEmployee
     *      The employee data to update
     */
    public void updateEmployee(Employee aEmployee)
    {
         try {
            st = conn.prepareStatement(QUERY_UPDATE_EMPLOYEE);
            st.setString(1, aEmployee.getName());
            st.setString(2, aEmployee.getFirstname());
            st.setString(3, aEmployee.getHomePhone());
            st.setString(4, aEmployee.getMobilePhone());
            st.setString(5, aEmployee.getProPhone());
            st.setString(6, aEmployee.getAddress());
            st.setString(7, aEmployee.getPostalCode());
            st.setString(8, aEmployee.getCity());
            st.setString(9, aEmployee.getMail());
            st.setInt(10, aEmployee.getId());
            st.execute(); 
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
