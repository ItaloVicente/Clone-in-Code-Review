        String body = null;
        try {
            body = transcoder.jsonObjectToString(designDocument.toJsonObject());
        } catch (Exception e) {
            throw new TranscodingException("Could not encode design document: ", e);
        }
