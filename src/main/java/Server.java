import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket = null;
    private BufferedReader input = null;
    private BufferedWriter output = null;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ServerWorker(clientSocket, this)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processMessage(String message, BufferedWriter output) throws Exception {
        // 处理客户端的请求信息，例如添加联系人、删除联系人、查询联系人等
        Data data = new Data();
        Scanner sc = new Scanner(System.in);
        String[] parts = message.split(" ");
        String requestType = parts[0];
        String[] requestParams = Arrays.copyOfRange(parts, 1, parts.length);
        if(requestType.equals("query")){
            Data.query();
        }else if(requestType.equals("add")){

            System.out.println("请输入您要添加到联系人名称：");
            String name = sc.next();

            System.out.println("请输入您要添加到联系人电话：");
            String phone = sc.next();
            System.out.println("请输入您要添加到联系人地址：");
            String address = sc.next();
            data.add(name,phone,address);
            //Data.query();
            System.out.println("添加成功");
            output.write("Success");
            output.newLine();
            output.flush();
        }else if(requestType.equals("delete")){
            System.out.println("请输入您要删除的联系人电话号码：");
            String phone = sc.next();
            int d = Data.delete(phone);
            if(d==1) {
                System.out.println("删除成功");
                Data.query();
                output.write("Success");
                output.newLine();
                output.flush();
            }
            else
                System.out.println("删除失败");
            /*String phone = requestParams[0];
            Data.delete(phone);
            output.write("Success");
            output.newLine();
            output.flush();*/
        }else if(requestType.equals("update")){
            System.out.println("请输入您要更新的联系人原手机号：");
            String phone = sc.next();
            ResultSet rs = Data.select(phone);
            int s = 0;
            if(rs.next()) {
                s = 1;
                rs = Data.select(phone);
                Data.find(rs);
            }
            while(s!=1){
                System.out.println("查无此人，请确认输入手机号是否正确");
                phone = sc.next();
                //s = Data.select(con, phone);
                rs =  Data.select(phone);
                //Data.find(rs);
                if(rs.next()) {
                    // Data.find(rs);
                    s = 1;
                    rs = Data.select(phone);
                    Data.find(rs);

                }
            }

            System.out.println("请输入更新后的联系人名称：");
            String name = sc.next();
            System.out.println("请输入联系人的新联系方式：");
            String phone1 = sc.next();
            System.out.println("请输入联系人的新地址");
            String address = sc.next();
            int u = Data.update(phone, name, phone1, address);
            //if (u == 1) {
            System.out.println("更新成功");
            output.write("Success");
            output.newLine();
            output.flush();
            Data.query();
        }else if(requestType.equals("select")){
            System.out.println("请输入查询联系人的手机号：");
            String phone = sc.next();
            ResultSet rs = Data.select(phone);
            int s = 0;
            if(rs.next()) {
                s = 1;
                rs = Data.select(phone);
                Data.find(rs);
            }
            while(s!=1){
                System.out.println("查无此人，请确认输入手机号是否正确");
                phone = sc.next();
                //s = Data.select(con, phone);
                rs =  Data.select(phone);
                //Data.find(rs);
                if(rs.next()) {
                    // Data.find(rs);
                    s = 1;
                    rs = Data.select(phone);
                    Data.find(rs);

                }
            }
            // Data.find(rs);
            System.out.println("查询成功");
        }else{
            output.write("Invalid request");
            output.newLine();
            output.flush();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
    }
}
