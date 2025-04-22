	public synchronized void addPostCloneTask(PostCloneTask task) {
		if (postCloneTasks == null)
			postCloneTasks = new ArrayList<PostCloneTask>();
		postCloneTasks.add(task);
	}

	public interface PostCloneTask  {

		void execute(Repository repository, IProgressMonitor monitor) throws CoreException;
	}
