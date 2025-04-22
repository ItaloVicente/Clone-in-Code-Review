            Observable<JsonObject> error = response
                .error()
                .map(new Func1<String, JsonObject>() {
                    @Override
                    public JsonObject call(String input) {
                        try {
                            return TRANSCODER.stringToJsonObject(input);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode View JSON: " + input, e);
                        }
                    }
                });

