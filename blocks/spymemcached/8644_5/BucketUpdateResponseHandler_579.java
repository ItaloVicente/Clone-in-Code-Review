    if (response.getStatus().getCode() == 200 && response.isChunked()) {
      readingChunks = true;
      finerLog("CHUNKED CONTENT {");
    } else {
      ChannelBuffer content = response.getContent();
      if (content.readable()) {
        finerLog("CONTENT {");
        finerLog(content.toString("UTF-8"));
        finerLog("} END OF CONTENT");
      }
