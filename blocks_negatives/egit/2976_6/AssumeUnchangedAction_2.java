public class AssumeUnchangedAction extends AbstractResourceOperationAction {
	protected IEGitOperation createOperation(final List<IResource> sel) {
		return sel.isEmpty() ? null : new AssumeUnchangedOperation(sel, true);
	}

	@Override
	protected String getJobName() {
		return UIText.AssumeUnchanged_assumeUnchanged;
