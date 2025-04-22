public class TapDumpOperationImpl extends TapOperationImpl implements TapOperation {
	private final String id;

	TapDumpOperationImpl(String id, OperationCallback cb) {
		super(cb);
		this.id = id;
	}
