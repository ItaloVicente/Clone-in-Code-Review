
    @Override
    public Config create(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            return parseJSON(jsonObject);
        } catch (JSONException e) {
            throw new ConfigParsingException("Exception parsing JSON data: "
                    + data, e);
        }
