        String hostname = nodeJO.get("hostname").toString();
        JSONObject portsJO = nodeJO.getJSONObject("ports");
        Map<Port, String> ports = new HashMap<Port, String>();
        for (Port port : Port.values()) {
          String portValue = portsJO.get(port.toString()).toString();
          if (portValue == null || portValue.isEmpty()) {
            continue;
          }
          ports.put(port, portValue);
