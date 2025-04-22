public class TestOperationImpl extends HttpOperationImpl implements
    TestOperation {

  public TestOperationImpl(HttpRequest r, TestCallback testCallback) {
    super(r, testCallback);
  }

  @Override
  public void handleResponse(HttpResponse response) {
    String json = getEntityString(response);
    int errorcode = response.getStatusLine().getStatusCode();
    if (errorcode == HttpURLConnection.HTTP_OK) {
      ((TestCallback) callback).getData(json);
      callback.receivedStatus(new OperationStatus(true, "OK"));
    } else {
      callback.receivedStatus(new OperationStatus(false, Integer
          .toString(errorcode)));
    }
    callback.complete();
  }
