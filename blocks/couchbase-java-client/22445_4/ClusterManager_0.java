    HttpResult result = sendRequest(request);
    if(result.getErrorCode() == 200) {
      return FlushResponse.OK;
    } else if(result.getErrorCode() == 400) {
      return FlushResponse.NOT_ENABLED;
    } else {
      throw new RuntimeException("Http Error: " + result.getErrorCode()
          + " Reason: " + result.getErrorPhrase() + " Details: "
          + result.getReason());
    }

