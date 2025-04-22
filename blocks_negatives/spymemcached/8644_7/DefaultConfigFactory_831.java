    private void populateServers(CacheConfig config, JSONArray nodes) throws JSONException {
	List<String> serverNames = new ArrayList<String>();
	for (int i = 0; i < nodes.length(); i++) {
	    JSONObject node = nodes.getJSONObject(i);
	    String webHostPort = node.getString("hostname");
	    String[] splitHostPort = webHostPort.split(":");
	    JSONObject portsList = node.getJSONObject("ports");
	    int port = portsList.getInt("direct");
	    serverNames.add(splitHostPort[0] + ":" + port);
	}
	config.setServers(serverNames);
    }
