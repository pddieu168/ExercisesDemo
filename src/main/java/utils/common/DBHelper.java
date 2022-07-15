package utils.common;

import global.Const;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBHelper {
    public Connection connectDB() throws SQLException {

        String url = Const.DB_URL;
        Properties props = new Properties();
        props.setProperty("user", Const.DB_USERNAME);
        props.setProperty("password", Const.DB_PASSWORD);
        return DriverManager.getConnection(url, props);
    }

    public ResultSet executeQuery(String columnExpect, String tableExpect, String dataColumn) throws SQLException {
        Connection conn = connectDB();
        String query = "select array_to_string(array_agg(\"" + columnExpect + "\"),',') from " + tableExpect + " where (" + dataColumn + " Like 'Auto%')";
        Statement st = conn.createStatement();
        return st.executeQuery(query);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Connection conn = connectDB();
        Statement st = conn.createStatement();
        return st.executeQuery(query);
    }

    public ResultSet executeQuery(String columnExpect, String tableExpect, String condition1, String condition2) throws SQLException {
        Connection conn = connectDB();
        String query = "select array_to_string(array_agg(\"" + columnExpect + "\"),',') from " + tableExpect + " where(" + condition1 + ") or (" + condition2 + ")";
        Statement st = conn.createStatement();
        return st.executeQuery(query);
    }

    public List<Integer> generateIdListOfTable(String columnExpect, String tableExpect, String dataColumn) throws SQLException {
        String value = null;
        ArrayList list = new ArrayList();
        List<Integer> expectedList = new ArrayList<>();

        ResultSet rs = executeQuery(columnExpect, tableExpect, dataColumn);
        while (rs.next()) {
            value = rs.getString(1);
        }
        if (value != null) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(value);
            while (m.find()) {
                list.add(m.group());
            }

            List<String> stringlists = new ArrayList<>(list);
            for (String string : stringlists) {
                int number = Integer.parseInt(string);
                try {
                    expectedList.add(number);
                } catch (Exception ex) {
                    LogUtil.error(ex.toString());
                }
            }
            LogUtil.info(expectedList.toString());
        } else {
            expectedList = null;
        }
        return expectedList;
    }

    public List<Integer> generateIdListOfTable(String query) throws SQLException {
        String value = null;
        ArrayList list = new ArrayList();
        List<Integer> expectedList = new ArrayList<>();

        ResultSet rs = executeQuery(query);
        while (rs.next()) {
            value = rs.getString(1);
        }
        if (value != null) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(value);
            while (m.find()) {
                list.add(m.group());
            }

            List<String> stringlists = new ArrayList<>(list);
            for (String string : stringlists) {
                int number = Integer.parseInt(string);
                try {
                    expectedList.add(number);
                } catch (Exception ex) {
                    LogUtil.error(ex.toString());
                }
            }
            LogUtil.info("List Id:" + expectedList.toString());
        } else {
            expectedList = null;
        }
        return expectedList;
    }

    public List<Integer> generateIdListOfTable(String columnExpect, String tableExpect, String condition1, String condition2) throws SQLException {
        String value = null;
        ArrayList list = new ArrayList();
        List<Integer> expectedList = new ArrayList<>();

        ResultSet rs = executeQuery(columnExpect, tableExpect, condition1, condition2);
        while (rs.next()) {
            value = rs.getString(1);
        }
        if (value != null) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(value);
            while (m.find()) {
                list.add(m.group());
            }

            List<String> stringlists = new ArrayList<>(list);
            for (String string : stringlists) {
                int number = Integer.parseInt(string);
                try {
                    expectedList.add(number);
                } catch (Exception ex) {
                    LogUtil.error(ex.toString());
                }
            }
            LogUtil.info(expectedList.toString());
        } else {
            expectedList = null;
        }
        return expectedList;
    }
}
