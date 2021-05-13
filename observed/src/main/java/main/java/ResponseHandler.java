package main.java;

import java.net.http.HttpResponse;

public class ResponseHandler {
    public static  MyEntry<ResponseType,Object> processResponse(HttpResponse<String> response){
        //ResponseType type = ResponseType.OK;
        String result=null;
        switch (response.statusCode()){
            case 200:{
                return new MyEntry<>(ResponseType.OK,response.body());
            }
            case 400:{
                return new MyEntry<>(ResponseType.BAD_REQUEST,response.body());
            }
            case 409:{
                switch (response.body()){
                    case "device_name_already_exists":{
                        return new MyEntry<>(ResponseType.BAD_CREDITS,response.body());
                    }
                    case "unknown_device":{
                        return new MyEntry<>(ResponseType.UNKNOWN_DEVICE,response.body());
                    }
                    case "wrong password":{
                        return new MyEntry<>(ResponseType.WRONG_PASSWORD,response.body());
                    }
                }
            }
        }

        return new MyEntry<>(ResponseType.OK,result);
    }
}
enum  ResponseType{
    OK,
    BAD_REQUEST,
    BAD_CREDITS,
    WRONG_PASSWORD,
    UNKNOWN_DEVICE
}
