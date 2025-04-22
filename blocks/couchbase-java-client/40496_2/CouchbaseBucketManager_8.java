                    JsonObject converted = null;
                    try {
                        converted = transcoder.stringToJsonObject(response.content().toString(CharsetUtil.UTF_8));
                    } catch (Exception e) {
                        throw new TranscodingException("Could not decode design document.", e);
                    }
