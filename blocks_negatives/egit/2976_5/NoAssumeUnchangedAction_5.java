public class NoAssumeUnchangedAction extends AbstractResourceOperationAction {
	protected IEGitOperation createOperation(final List<IResource> sel) {
		return sel.isEmpty() ? null : new AssumeUnchangedOperation(sel, false);
	}

	@Override
	protected String getJobName() {
		return UIText.AssumeUnchanged_noAssumeUnchanged;
