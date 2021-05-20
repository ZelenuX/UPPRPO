package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    String username;
    String password;
    ServerConnector connector;
    String uri="http://127.0.0.1:8080/add_data";
    String registerUri="http://127.0.0.1:8080/register_observed";
    Scanner read;
    public static void main(String[] args){
        new Controller(args);
    }
    public Controller(String[] args){
        read = new Scanner(System.in);
        StateScanner scanner =  new StateScanner();
        connector = new ServerConnector();
        MyEntry<ResponseType, Object> handler;
        if(args.length==3 && args[0].equals("-c")){
            username = args[1];
            password = args[2];

        }else {
            register();
        }
        Timer step = new Timer();
        step.schedule(new Tick(),0,1000);

    }
    public void register(){
        boolean unregistered = true;
        while (unregistered) {
            System.out.println("Enter username");
            username = read.nextLine();
            System.out.println("Enter password");
            password = read.nextLine();
            String registerRequest = "{ \"username\" : \"" + username + "\", \"password\" : \"" + password + "\"}";
            HttpResponse response = connector.register(registerRequest, registerUri);
            MyEntry<ResponseType, Object> handler = ResponseHandler.processResponse(response);
            if(handler.getKey() == ResponseType.OK){
                unregistered =false;
            }else if(handler.getKey() == ResponseType.BAD_CREDITS){
                System.out.println("Username already taken. try again");
            }else{
                System.out.println(handler.getValue());
                System.exit(1);
            }
        }
    }
    class Tick extends TimerTask{

        @Override
        public void run() {
            StateScanner scanner =  new StateScanner();
            String cpusData = scanner.getCPUsData();
            String gpusData = scanner.getGPUsData();
            String disksData = scanner.getDisksData();
            String mobosData = scanner.getMobosData();

            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("{ \"username\" : \""+username+"\",");
            messageBuilder.append("{ \"password\" : \""+password+"\",");
            messageBuilder.append(cpusData);
            messageBuilder.append(",");
            messageBuilder.append(gpusData);
            messageBuilder.append(",");
            messageBuilder.append(disksData);
            messageBuilder.append(",");
            messageBuilder.append(mobosData);
            messageBuilder.append("}");

            HttpResponse response = connector.register(messageBuilder.toString(), uri);
            MyEntry<ResponseType, Object> handler = ResponseHandler.processResponse(response);
            switch (handler.getKey()){
                case WRONG_PASSWORD:{
                    System.out.println("Wrong password, do you want to reenter it? y/n");
                    String res = read.nextLine();
                    if(res.charAt(0)=='y'){
                        System.out.println("Enter password");
                        password = read.nextLine();
                    } else if(res.charAt(0)=='n'){
                        System.exit(1);
                    }else{
                        System.exit(1);
                    }
                    break;
                }
                case UNKNOWN_DEVICE:{
                    System.out.println("Wrong username/password, do you want to reenter it? y/n");
                    String res = read.nextLine();
                    if(res.charAt(0)=='y'){
                        System.out.println("Enter username");
                        username = read.nextLine();
                        System.out.println("Enter password");
                        password = read.nextLine();
                    } else if(res.charAt(0)=='n'){
                        System.out.println("Do you want to register it? y/n");
                        res = read.nextLine();
                        if(res.charAt(0)=='y'){
                            register();
                        }else{
                            System.exit(1);
                        }
                    } else{
                        System.exit(1);
                    }
                    break;
                }
                case BAD_REQUEST:{
                    System.out.println(response.body());
                    System.exit(1);
                }
            }
        }
    }
}
