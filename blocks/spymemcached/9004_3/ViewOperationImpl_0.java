public abstract class ViewOperationImpl extends HttpOperationImpl
  implements ViewOperation {

  public ViewOperationImpl(HttpRequest r, OperationCallback cb) {
    super(r, cb);
  }

  @Override
  public void handleResponse(HttpResponse response) {
    String json = getEntityString(response);
    int errorcode = response.getStatusLine().getStatusCode();
    try {
      OperationStatus status = parseViewForStatus(json, errorcode);
      ViewResponse vr = null;
      if (status.isSuccess()) {
        vr = parseResult(json);
      }

      ((ViewCallback) callback).gotData(vr);
      callback.receivedStatus(status);
    } catch (ParseException e) {
      exception = new OperationException(OperationErrorType.GENERAL,
          "Error parsing JSON");
    }
    callback.complete();
  }

  protected abstract ViewResponse parseResult(String json)
    throws ParseException;
