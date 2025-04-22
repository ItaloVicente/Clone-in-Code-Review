public class AssumeUnchangedActionHandler extends RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource[] resources = getSelectedResources(event);
		if (resources.length == 0)
			return null;
		AssumeUnchangedOperation op = new AssumeUnchangedOperation(Arrays
				.asList(resources), true);
		JobUtil.scheduleUserJob(op, UIText.AssumeUnchanged_assumeUnchanged,
				JobFamilies.ASSUME_NOASSUME_UNCHANGED);
		return null;
