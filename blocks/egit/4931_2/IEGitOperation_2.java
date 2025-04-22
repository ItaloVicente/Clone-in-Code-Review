
	interface PreExecuteTask {

		void preExecute(Repository repository, IProgressMonitor monitor)
				throws CoreException;
	}

	interface PostExecuteTask {

		void postExecute(Repository repository, IProgressMonitor monitor)
				throws CoreException;
	}
