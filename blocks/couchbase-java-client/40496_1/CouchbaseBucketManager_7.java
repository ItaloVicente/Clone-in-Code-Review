                    JsonObject converted = null;
                    try {
                        converted = transcoder.stringToJsonObject(response.content());
                    } catch (Exception e) {
                        throw new TranscodingException("Could not decode design document.", e);
                    }
