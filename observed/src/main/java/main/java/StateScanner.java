package main.java;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.*;
import com.profesorfalken.jsensors.model.sensors.Fan;
import com.profesorfalken.jsensors.model.sensors.Load;
import com.profesorfalken.jsensors.model.sensors.Sensors;
import com.profesorfalken.jsensors.model.sensors.Temperature;

import java.io.File;
import java.lang.management.*;
import java.util.List;
import java.util.Vector;

public class StateScanner {
    Components components;
    List<Cpu> cpus;
    List<Disk> disks;
    List<Gpu> gpus;
    List<Mobo> mobos;
    public StateScanner(){
        components= JSensors.get.components();
        cpus = components.cpus;
        disks = components.disks;
        gpus = components.gpus;
        mobos = components.mobos;
    }
    public String getCPUsData(){
        StringBuilder cpuDataBuilder = new StringBuilder();
        cpuDataBuilder.append( " cpus : [");
        cpus.forEach(cpu->{
            cpuDataBuilder.append("{");
            cpuDataBuilder.append("\" cpu_name\" : \""+ cpu.name +"\"" );
            Sensors cpuSensors = cpu.sensors;
            if(cpuSensors!=null){
                List<Temperature> temps = cpuSensors.temperatures;
                if(temps!=null && temps.size()!=0){
                    cpuDataBuilder.append(", \"cpu_t\" : [");
                    temps.forEach(temperature -> {
                        cpuDataBuilder.append("\"" + String.valueOf(temperature.value)+"\",");
                    });
                    if(temps.size()!=0){
                        cpuDataBuilder.deleteCharAt(cpuDataBuilder.length()-1);
                    }
                    cpuDataBuilder.append("]");
                }
                List<Load> loads = cpuSensors.loads;
                if(loads!=null && loads.size()!=0){
                    cpuDataBuilder.append(", \"cpu_load\" : [");
                    loads.forEach(load -> {
                        cpuDataBuilder.append("\"" + String.valueOf(load.value)+"\",");
                    });
                    if(loads.size()!=0){
                        cpuDataBuilder.deleteCharAt(cpuDataBuilder.length()-1);
                    }
                    cpuDataBuilder.append("]");
                }
                List<Fan> fans =cpuSensors.fans;
                if(fans!=null && fans.size()!=0){
                    cpuDataBuilder.append(", \"cpu_fan\" : [");
                    fans.forEach(fan -> {
                        cpuDataBuilder.append("\"" + String.valueOf(fan.value)+"\",");
                    });
                    if(fans.size()!=0){
                        cpuDataBuilder.deleteCharAt(cpuDataBuilder.length()-1);
                    }
                    cpuDataBuilder.append("]");
                }
            }
            cpuDataBuilder.append("},");
        });
        if(cpus.size()!=0){
            cpuDataBuilder.deleteCharAt(cpuDataBuilder.length()-1);
        }
        cpuDataBuilder.append("]");
        return cpuDataBuilder.toString();
    }
    public String getDisksData(){
        StringBuilder diskDataBuilder = new StringBuilder();
        diskDataBuilder.append(" disks : [");
        disks.forEach(disk->{
              diskDataBuilder.append("{");
              diskDataBuilder.append("\"disk_name\" : \"" + disk.name +"\"");
              Sensors diskSensors = disk.sensors;
              if(diskSensors!=null) {
                  List<Temperature> temps = diskSensors.temperatures;
                  if(temps!=null && temps.size()!=0){
                      diskDataBuilder.append(", \"disk_t\" : [");
                      temps.forEach(temperature -> {
                          diskDataBuilder.append("\"" + String.valueOf(temperature.value)+"\",");
                      });
                      if(temps.size()!=0){
                          diskDataBuilder.deleteCharAt(diskDataBuilder.length()-1);
                      }
                      diskDataBuilder.append("]");
                  }
                  List<Load> loads = diskSensors.loads;
                  if(loads!=null && loads.size()!=0){
                      diskDataBuilder.append(", \"disk_load\" : [");
                      loads.forEach(load -> {
                          diskDataBuilder.append("\"" + String.valueOf(load.value)+"\",");
                      });
                      if(loads.size()!=0){
                          diskDataBuilder.deleteCharAt(diskDataBuilder.length()-1);
                      }
                      diskDataBuilder.append("]");
                  }
                  List<Fan> fans =diskSensors.fans;
                  if(fans!=null && fans.size()!=0){
                      diskDataBuilder.append(", \"disk_fan\" : [");
                      fans.forEach(fan -> {
                          diskDataBuilder.append("\"" + String.valueOf(fan.value)+"\",");
                      });
                      if(fans.size()!=0){
                          diskDataBuilder.deleteCharAt(diskDataBuilder.length()-1);
                      }
                      diskDataBuilder.append("]");
                  }
              }
              diskDataBuilder.append("},");
        });
        if(disks.size()!=0){
            diskDataBuilder.deleteCharAt(diskDataBuilder.length()-1);
        }
        diskDataBuilder.append("]");
        return diskDataBuilder.toString();
    }
    public String getGPUsData(){
        StringBuilder gpuDataBuilder = new StringBuilder();
        gpuDataBuilder.append( " gpus : [");
        gpus.forEach(gpu->{
            gpuDataBuilder.append("{");
            gpuDataBuilder.append("\" gpu_name\" : \""+ gpu.name +"\"" );
            Sensors gpuSensors = gpu.sensors;
            if(gpuSensors!=null){
                List<Temperature> temps = gpuSensors.temperatures;
                if(temps!=null && temps.size()!=0){
                    gpuDataBuilder.append(", \"gpu_t\" : [");
                    temps.forEach(temperature -> {
                        gpuDataBuilder.append("\"" + String.valueOf(temperature.value)+"\",");
                    });
                    if(temps.size()!=0){
                        gpuDataBuilder.deleteCharAt(gpuDataBuilder.length()-1);
                    }
                    gpuDataBuilder.append("]");
                }
                List<Load> loads = gpuSensors.loads;
                if(loads!=null && loads.size()!=0){
                    gpuDataBuilder.append(", \"gpu_load\" : [");
                    loads.forEach(load -> {
                        gpuDataBuilder.append("\"" + String.valueOf(load.value)+"\",");
                    });
                    if(loads.size()!=0){
                        gpuDataBuilder.deleteCharAt(gpuDataBuilder.length()-1);
                    }
                    gpuDataBuilder.append("]");
                }
                List<Fan> fans =gpuSensors.fans;
                if(fans!=null && fans.size()!=0){
                    gpuDataBuilder.append(", \"gpu_fan\" : [");
                    fans.forEach(fan -> {
                        gpuDataBuilder.append("\"" + String.valueOf(fan.value)+"\",");
                    });
                    if(fans.size()!=0){
                        gpuDataBuilder.deleteCharAt(gpuDataBuilder.length()-1);
                    }
                    gpuDataBuilder.append("]");
                }
            }
            gpuDataBuilder.append("},");
        });
        if(cpus.size()!=0){
            gpuDataBuilder.deleteCharAt(gpuDataBuilder.length()-1);
        }
        gpuDataBuilder.append("]");
        return gpuDataBuilder.toString();
    }
    public String getMobosData(){
        StringBuilder moboDataBuilder = new StringBuilder();
        moboDataBuilder.append( " mobos : [");
        mobos.forEach(mobo->{
            moboDataBuilder.append("{");
            moboDataBuilder.append("\" mobo_name\" : \""+ mobo.name +"\"" );
            Sensors moboSensors = mobo.sensors;
            if(moboSensors!=null){
                List<Temperature> temps = moboSensors.temperatures;
                if(temps!=null && temps.size()!=0){
                    moboDataBuilder.append(", \"mobo_t\" : [");
                    temps.forEach(temperature -> {
                        moboDataBuilder.append("\"" + String.valueOf(temperature.value)+"\",");
                    });
                    if(temps.size()!=0){
                        moboDataBuilder.deleteCharAt(moboDataBuilder.length()-1);
                    }
                    moboDataBuilder.append("]");
                }
                List<Load> loads = moboSensors.loads;
                if(loads!=null && loads.size()!=0){
                    moboDataBuilder.append(", \"mobo_load\" : [");
                    loads.forEach(load -> {
                        moboDataBuilder.append("\"" + String.valueOf(load.value)+"\",");
                    });
                    if(loads.size()!=0){
                        moboDataBuilder.deleteCharAt(moboDataBuilder.length()-1);
                    }
                    moboDataBuilder.append("]");
                }
                List<Fan> fans =moboSensors.fans;
                if(fans!=null && fans.size()!=0){
                    moboDataBuilder.append(", \"mobo_fan\" : [");
                    fans.forEach(fan -> {
                        moboDataBuilder.append("\"" + String.valueOf(fan.value)+"\",");
                    });
                    if(fans.size()!=0){
                        moboDataBuilder.deleteCharAt(moboDataBuilder.length()-1);
                    }
                    moboDataBuilder.append("]");
                }
            }
            moboDataBuilder.append("},");
        });
        if(cpus.size()!=0){
            moboDataBuilder.deleteCharAt(moboDataBuilder.length()-1);
        }
        moboDataBuilder.append("]");
        return moboDataBuilder.toString();
    }
}
