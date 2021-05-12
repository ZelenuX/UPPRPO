package com.zelenux.upprpo_server_test.observed;

import com.zelenux.upprpo_server_test.observed.data_transfer_objects.Data;
import com.zelenux.upprpo_server_test.observed.data_transfer_objects.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller
public class ObservedAcceptor {
    private static Integer parseInt(String str){
        if (str == null){
            return null;
        }
        else {
            return Integer.parseInt(str);
        }
    }
    private static Integer calcAvgParam(List<Map<String, Object>> structList, String parameterName){
        Integer avgParam = 0;
        int paramN = 0;
        if (structList != null){
            for (Map<String, Object> struct : structList){
                Integer curParam = (Integer)struct.get(parameterName);
                if (curParam != null){
                    ++paramN;
                    avgParam += curParam;
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
    public String registerDevice(@RequestBody Map<String, String> model){
        return observedController.registerDevice(new Device(model.get("device"), model.get("password")));
    }
    @PostMapping("/add_data")
    public String addData(@RequestBody Map<String, Object> model){
        try {
            List<Map<String, Object>> cpus = (List<Map<String, Object>>)model.get("cpus");
            List<Map<String, Object>> rams = (List<Map<String, Object>>)model.get("rams");
            return observedController.addData(new Data((String)model.get("device"), (String)model.get("password"),
                    calcAvgParam(cpus, "temp"),
                    calcAvgParam(cpus, "load"),
                    calcAvgParam(rams, "load")));
        }
        catch (NumberFormatException | ClassCastException e){
            return observedController.formatError();
        }
    }
}
