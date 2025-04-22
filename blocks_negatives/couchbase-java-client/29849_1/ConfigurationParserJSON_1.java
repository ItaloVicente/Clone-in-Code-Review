        String uri = (String) poolJO.get(URI_ATTR);
        if (uri == null || "".equals(uri)) {
          throw new ParseException("Pool's uri is missing.", 0);
        }
        String streamingUri = (String) poolJO.get(STREAMING_URI_ATTR);
        Pool pool = new Pool(name, new URI(uri), new URI(streamingUri));
