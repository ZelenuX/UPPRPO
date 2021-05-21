package com.zelenux.upprpo_server_test.utils;

import java.util.Collection;

public class JSONBuilder {
    private StringBuilder stringBuilder;
    private JSONBuilderInProcess jsonBuilderInProcess;

    public JSONBuilder(){
        stringBuilder = new StringBuilder();
        jsonBuilderInProcess = new JSONBuilderInProcess(stringBuilder);
    }
    public JSONBuilderInProcess startJSON(){
        stringBuilder.setLength(0);
        stringBuilder.append("{");
        return jsonBuilderInProcess;
    }

    public static class JSONBuilderInProcess{
        private StringBuilder stringBuilder;
        private boolean firstElementPlaced = false;
        private JSONBuilderInProcess(StringBuilder stringBuilder){
            this.stringBuilder = stringBuilder;
        }
        private void processFirstElementPlaced(){
            if (firstElementPlaced){
                stringBuilder.append(",");
            }
            else {
                firstElementPlaced = true;
            }
        }

        public JSONBuilderInProcess add(String name, String value){
            processFirstElementPlaced();
            stringBuilder.append("\"").append(name).append("\":\"").append(value).append("\"");
            return this;
        }
        public JSONBuilderInProcess add(String name, Integer value){
            processFirstElementPlaced();
            stringBuilder.append("\"").append(name).append("\":").append(value);
            return this;
        }
        public JSONBuilderInProcess add(String name, JSONAdder element){
            processFirstElementPlaced();
            stringBuilder.append("\"").append(name).append("\":");
            element.addJSON(stringBuilder, true);
            return this;
        }
        public JSONBuilderInProcess addInternals(JSONAdder element){
            processFirstElementPlaced();
            element.addJSON(stringBuilder, false);
            return this;
        }
        public JSONBuilderInProcess addArray(String arrayName, JSONAdder... elements){
            processFirstElementPlaced();
            stringBuilder.append("\"").append(arrayName).append("\":[");
            if (elements.length > 0) {
                elements[0].addJSON(stringBuilder, true);
                for (int i = 1; i < elements.length; i++) {
                    stringBuilder.append(",");
                    elements[i].addJSON(stringBuilder, true);
                }
            }
            stringBuilder.append("]");
            return this;
        }

        public String finishJSON(){
            firstElementPlaced = false;
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }
}
