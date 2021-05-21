package com.zelenux.upprpo_server_test.observed;

import com.zelenux.upprpo_server_test.dataTransferObjects.Data;
import com.zelenux.upprpo_server_test.dataTransferObjects.Device;
import com.zelenux.upprpo_server_test.observed.exceptions.DeviceAlreadyExistsException;
import com.zelenux.upprpo_server_test.observed.exceptions.DeviceDoesNotExistException;
import com.zelenux.upprpo_server_test.observed.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.observed.exceptions.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ObservedAcceptor {
    private static Integer parseInt(String str){
        if (str == null){
            return null;
        }
        else {
            return Integer.parseInt(str);
        }
    }
    private static Integer calcAvgParam(List<Map<String, Object>> structList,
                                        String parameterName, boolean isParameterList){
        Integer avgParam = 0;
        int paramN = 0;
        if (structList != null){
            for (Map<String, Object> struct : structList){
                if (isParameterList){
                    List<Integer> curParamList = (List<Integer>) struct.get(parameterName);
                    if (curParamList != null && !curParamList.isEmpty()){
                        int avgParamInList = 0;
                        int paramInListN = 0;
                        for (Integer curParamInList : curParamList){
                            if (curParamInList != null){
                                avgParamInList += curParamInList;
                                ++paramInListN;
                            }
                        }
                        if (paramInListN != 0){
                            ++paramN;
                            avgParam += avgParamInList / paramInListN;
                        }
                    }
                }
                else {
                    Integer curParam = (Integer) struct.get(parameterName);
                    if (curParam != null) {
                        ++paramN;
                        avgParam += curParam;
                    }
                }
            }
        }
        if (paramN == 0){
            avgParam = null;
        }
        else {
            avgParam /= paramN;
        }
        return avgParam;
    }

    private ObservedController observedController;

    public ObservedAcceptor(@Autowired ObservedController observedController) {
        this.observedController = observedController;
    }

    @PostMapping("/register_observed")
    public String registerDevice(@RequestBody Map<String, String> model)
            throws DeviceAlreadyExistsException, WrongFormatException {
        return observedController.registerDevice(new Device(model.get("username"), model.get("password")));
    }

    @PostMapping("/add_data")
    public String addData(@RequestBody Map<String, Object> model)
            throws DeviceDoesNotExistException, WrongPasswordException, WrongFormatException {
        try {
            List<Map<String, Object>> cpus = (List<Map<String, Object>>)model.get("cpus");
            List<Map<String, Object>> rams = (List<Map<String, Object>>)model.get("rams");
            String response = observedController.addData(new Data((String)model.get("username"), (String)model.get("password"),
                    calcAvgParam(cpus, "cpu_t", true),
                    calcAvgParam(cpus, "cpu_load", true),
                    calcAvgParam(rams, "ram_load", true)));
            System.out.println("data added");
            return response;
        }
        catch (NumberFormatException | ClassCastException e){
            return observedController.formatError();
        }
    }
}
