  @Override
  public void handleResponse(HttpResponse response) {
    String json = getEntityString(response);
    try {
      int errorcode = response.getStatusLine().getStatusCode();
      if (errorcode == HttpURLConnection.HTTP_OK) {
        List<View> views = parseDesignDocumentForViews(bucketName,
          designDocName, json);
        ((ViewsCallback) callback).gotData(views);
        callback.receivedStatus(new OperationStatus(true, "OK"));
      } else {
        callback.receivedStatus(new OperationStatus(false,
            Integer.toString(errorcode)));
      }
    } catch (ParseException e) {
      exception = new OperationException(OperationErrorType.GENERAL,
        "Error parsing JSON");
    }
    callback.complete();
  }
