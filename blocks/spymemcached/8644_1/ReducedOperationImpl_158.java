  @Override
  public void handleResponse(HttpResponse response) {
    String json = getEntityString(response);
    int errorcode = response.getStatusLine().getStatusCode();
    try {
      OperationStatus status = parseViewForStatus(json, errorcode);
      ViewResponseReduced vr = null;
      if (status.isSuccess()) {
        vr = parseReducedViewResult(json);
      }
