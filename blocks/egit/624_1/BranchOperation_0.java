		IWorkspaceRunnable action = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask(NLS.bind(
						CoreText.BranchOperation_performingBranch, refName), 6);
				lookupRefs();
				monitor.worked(1);
