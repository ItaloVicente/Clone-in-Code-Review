
      try {
        response.setEntity(new BufferedHttpEntity(response.getEntity()));
      } catch(IOException ex) {
        throw new RuntimeException("Could not convert HttpEntity content.");
      }

      int statusCode = response.getStatusLine().getStatusCode();
