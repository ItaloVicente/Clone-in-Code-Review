
  private HttpResult sendRequest(HttpRequest request) {
    HttpParams params = new SyncBasicHttpParams();
    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setUserAgent(params, "Couchbase Java Client/1.1");
    HttpProtocolParams.setUseExpectContinue(params, true);
