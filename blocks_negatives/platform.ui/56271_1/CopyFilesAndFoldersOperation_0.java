	protected void copy(IResource[] resources, IPath destination,
			IProgressMonitor subMonitor) throws CoreException {

		subMonitor
				.beginTask(
						IDEWorkbenchMessages.CopyFilesAndFoldersOperation_CopyResourcesTask,
						resources.length);
