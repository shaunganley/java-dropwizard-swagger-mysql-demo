package org.kainos.ea.db;

import org.apache.commons.lang3.time.DateUtils;
import org.kainos.ea.cli.Login;
import org.kainos.ea.client.TokenExpiredException;

import java.sql.*;
import java.util.UUID;

import java.util.Date;

public class AuthDao {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public boolean validLogin(Login login) {
        try (Connection c = databaseConnector.getConnection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT Password FROM `User` where Username = '"
                    + login.getUsername() + "';");

            while (rs.next()) {
                return rs.getString("Password").equals(login.getPassword());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public String generateToken(String username) throws SQLException {
        String token = UUID.randomUUID().toString();
        Date expiry = DateUtils.addHours(new Date(), 8);

        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO Token (Username, Token, Expiry) VALUES (?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement);

        st.setString(1, username);
        st.setString(2, token);
        st.setTimestamp(3, new java.sql.Timestamp(expiry.getTime()));

        st.executeUpdate();

        return token;
    }

    public int getRoleIDFromToken(String token) throws SQLException, TokenExpiredException {
        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT RoleID, Expiry FROM `User` join `Token` using (Username)" +
                "where Token = '" + token + "';");

            while (rs.next()) {
                Timestamp expiry = rs.getTimestamp("Expiry");

                if(expiry.after(new Date())) {
                    return rs.getInt("RoleID");
                } else {
                    throw new TokenExpiredException();
                }
            }

        return -1;
    }
}
