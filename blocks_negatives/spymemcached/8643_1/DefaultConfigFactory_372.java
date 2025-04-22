
    private List<String> populateServers(JSONArray servers) throws JSONException {
        List<String> serverNames = new ArrayList<String>();
        for (int i = 0; i < servers.length(); i++) {
            String server = servers.getString(i);
            serverNames.add(server);
        }
        return serverNames;
