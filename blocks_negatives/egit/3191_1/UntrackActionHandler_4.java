public class Untrack extends AbstractResourceOperationAction {
	protected IEGitOperation createOperation(final List<IResource> sel) {
		return sel.isEmpty() ? null : new UntrackOperation(sel);
	}

	@Override
	protected String getJobName() {
		return UIText.Untrack_untrack;
