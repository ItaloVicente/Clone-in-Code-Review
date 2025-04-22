public class Disconnect extends AbstractResourceOperationAction {
	protected IEGitOperation createOperation(final List<IResource> sel) {
		List<IProject> projects = new ArrayList<IProject>();
		for(IResource resource:sel)
			projects.add((IProject) resource);
		return new DisconnectProviderOperation(projects);
