public interface HttpOperation {

	public HttpRequest getRequest();

	OperationCallback getCallback();

	boolean isCancelled();

	boolean hasErrored();

	boolean isTimedOut();

	void cancel();

	void timeOut();

	OperationException getException();

	void handleResponse(HttpResponse response);
