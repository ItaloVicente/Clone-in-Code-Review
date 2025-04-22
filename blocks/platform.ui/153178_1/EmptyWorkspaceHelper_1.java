	public interface EmptinessDetector {
		public void init();

		public void dispose();

		public boolean isEmpty();
	}

	public static final class WorkspaceEmptinessDetector implements EmptinessDetector, IResourceChangeListener {
		private final EmptyWorkspaceHelper helper;

		public WorkspaceEmptinessDetector(EmptyWorkspaceHelper helper) {
			this.helper = helper;
		}

		@Override
		public void init() {
			ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
		}

		@Override
		public void dispose() {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		}

		@Override
		public boolean isEmpty() {
			IProject[] projs = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			return projs.length == 0;
		}
