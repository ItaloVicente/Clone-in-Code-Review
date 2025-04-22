public class UntrackActionHandler extends RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource[] resources = getSelectedResources();
		if (resources.length == 0)
			return null;
		JobUtil.scheduleUserJob(new UntrackOperation(Arrays.asList(resources)),
				UIText.Untrack_untrack, JobFamilies.UNTRACK);
		return null;
