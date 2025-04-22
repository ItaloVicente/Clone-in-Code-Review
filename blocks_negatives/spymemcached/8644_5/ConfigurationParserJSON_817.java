        for (int i = 0; i < poolsJA.length(); ++i) {
            try {
                JSONObject poolJO = poolsJA.getJSONObject(i);
                String name = (String) poolJO.get(NAME_ATTR);
                if (name == null || "".equals(name)) {
                    throw new ParseException("Pool's name is missing.", 0);
                }
                String uri = (String) poolJO.get(URI_ATTR);
                if (uri == null || "".equals(uri)) {
                    throw new ParseException("Pool's uri is missing.", 0);
                }
                String streamingUri = (String) poolJO.get(STREAMING_URI_ATTR);
                Pool pool = new Pool(name, new URI(uri), new URI(streamingUri));
                parsedBase.put(name, pool);
            } catch (JSONException e) {
                getLogger().error("One of the pool configuration can not be parsed.", e);
            } catch (URISyntaxException e) {
                getLogger().error("Server provided an incorrect uri.", e);
            }
