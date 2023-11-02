
    //package com.lz.Data;

import java.sql.*;

    public class Data {
        private static Connection con ;
        public Data() throws Exception{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/friend?serverTimezone=GMT%2B8";
            String username = "root";
            String password = "20021201";
             con = DriverManager.getConnection(url,username,password);
        }

        public static void query() throws SQLException {
            Statement st = con.createStatement();
            String s = "select * from fr";
            //return sql.executeQuery(s);
            ResultSet rs = st.executeQuery(s);
            find(rs);
        }
        public static ResultSet select(String phone) throws SQLException{
            Statement st = con.createStatement();
            String s = "select * from fr where phonenumber='" + phone+"'";

            ResultSet rs  = st.executeQuery(s);
            return rs;


        }

        public static void find(ResultSet rs) throws SQLException {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int j = 1; j <= columnsNumber; j++) {
                    //if (j > 1) System.out.print(",  ");
                    String columnValue = rs.getString(j);
                    System.out.print(rsmd.getColumnName(j)+":"+columnValue);
                    System.out.println("");
                }
                System.out.println(" ");

            }
           // rs.next() = rs;
        }

        public static void add(String name,String phonenumber,String address) throws SQLException {
            Statement sql = con.createStatement();
            String s = "insert into fr values('" + name + "','"
                    + phonenumber + "','" + address + "')";
            sql.executeUpdate(s);
        }

        public static int delete(String phonenumber) throws SQLException {
            Statement sql = con.createStatement();
            String s = "delete from fr where phonenumber='" + phonenumber+"'";

            int a = sql.executeUpdate(s);
            return a;
        }

        public static int update(String p,String name,String phonenumber,String address) throws SQLException {
            Statement st = con.createStatement();
            String sql = "update fr set `name`='"
                    + name + "',`phonenumber`='" + phonenumber + "',`address`='"
                    + address + "' where phonenumber= '"+p+"'";
            int a = st.executeUpdate(sql);
            return a;
        }
    }

