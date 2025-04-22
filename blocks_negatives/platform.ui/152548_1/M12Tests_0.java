		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				file2.delete(true, new NullProgressMonitor());
				file2.create(null, true, null);
			}
