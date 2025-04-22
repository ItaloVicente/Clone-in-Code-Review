public class MultiGetOperationCallback extends MultiOperationCallback
	implements GetOperation.Callback {

	public MultiGetOperationCallback(OperationCallback original, int todo) {
		super(original, todo);
	}
