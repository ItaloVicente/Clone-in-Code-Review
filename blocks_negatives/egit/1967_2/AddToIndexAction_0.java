public class AddToIndexAction extends AbstractResourceOperationAction {
	private AddToIndexOperation operation = null;

	protected IEGitOperation createOperation(final List<IResource> sel) {
		if (sel.isEmpty()) {
			return null;
		} else {
			operation = new AddToIndexOperation(sel);
			return operation;
		}
	}
