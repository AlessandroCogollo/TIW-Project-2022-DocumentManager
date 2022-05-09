package it.polimi.tiw.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import it.polimi.tiw.beans.*;

public class SubFolderDAO {
    private final Connection connection;

    public SubFolderDAO(Connection con) {
        this.connection = con;
    }

    public List<SubFolder> getAllSubFolderOfFolder(String username, String folderName) throws SQLException {

        List<SubFolder> allSubFoldersOfFolder = new ArrayList<>();

        try (PreparedStatement pstatement = connection.prepareStatement(
                "SELECT * FROM subfolder WHERE username = ? AND folderName = ?")) {
            pstatement.setString(1, username);
            pstatement.setString(2, folderName);
            try (ResultSet result = pstatement.executeQuery()) {
                while (result.next()) {
                    SubFolder newSubFolder = new SubFolder();
                    newSubFolder.setOwnerUsername(result.getString("username"));
                    newSubFolder.setFolderName(result.getString("foldername"));
                    newSubFolder.setSubFolderName(result.getString("subfoldername"));
                    newSubFolder.setDate(result.getDate("date"));
                    allFolders.add(newSubFolder);
                }
            }
        }
        return allSubFoldersOfFolder;
    }

    public boolean insertSubFolder(SubFolder subFolder) throws SQLException {
        String query = "INSERT into subfolder VALUES(?, ?, ?, ?)";
        PreparedStatement pstatement = null;
        int code = 0;

        try {
            pstatement = connection.prepareStatement(query);
            pstatement.setString(1, subFolder.getOwnerUsername());
            pstatement.setString(2, subFolder.getFolderName());
            pstatement.setString(3, subFolder.getSubFolderName());
            pstatement.setDate(4, (Date) subFolder.getDate());
            code = pstatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                pstatement.close();
            } catch (Exception e1) {
                throw e1;
            }
        }
        return (code==1 ? true : false);
    }
}
