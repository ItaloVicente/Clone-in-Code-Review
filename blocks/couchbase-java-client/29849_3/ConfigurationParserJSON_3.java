
  private Status parseNodeStatus(String status) {
    if (status == null || status.isEmpty()) {
      return null;
    }

    try {
      return Status.valueOf(status);
    } catch (IllegalArgumentException e) {
      getLogger().error("Unknown status value: " + status);
      return null;
    }
  }

  private Map<Port, String> extractPorts(JSONObject portsJson)
    throws JSONException {
    Map<Port, String> ports = new HashMap<Port, String>();
    for (Port port : Port.values()) {
      String portValue = portsJson.getString(port.toString());
      if (portValue == null || portValue.isEmpty()) {
        continue;
      }
      ports.put(port, portValue);
    }
    return ports;
  }

