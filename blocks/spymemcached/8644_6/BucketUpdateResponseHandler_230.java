    if (readingChunks) {
      HttpChunk chunk = (HttpChunk) event.getMessage();
      if (chunk.isLast()) {
        readingChunks = false;
      } else {
        String curChunk = chunk.getContent().toString("UTF-8");
        if (curChunk.matches("\n\n\n\n")) {
          setLastResponse(partialResponse.toString());
          partialResponse = null;
          getLatch().countDown();
          if (monitor != null) {
            monitor.invalidate();
          }
