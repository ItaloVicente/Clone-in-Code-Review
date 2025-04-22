        String streamingUri = (String) poolJO.get(STREAMING_URI_ATTR);
        Pool pool = new Pool(name, new URI(uri), new URI(streamingUri));
        parsedBase.put(name, pool);
      } catch (JSONException e) {
        getLogger().error("One of the pool configuration can not be parsed.",
            e);
      } catch (URISyntaxException e) {
        getLogger().error("Server provided an incorrect uri.", e);
      }
    }
    return parsedBase;
  }
