      JSONArray allNodes = bucketJson.getJSONArray("nodes");
      for (int i = 0; i < allNodes.length(); i++) {
        JSONObject currentNode = allNodes.getJSONObject(i);
        Status status = parseNodeStatus(currentNode.getString("status"));
        String hostname = currentNode.getString("hostname");
        Map<Port, String> ports = extractPorts(
          currentNode.getJSONObject("ports"));
        nodes.add(new Node(status, hostname, ports));
