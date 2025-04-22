
		String jobname = NLS.bind(UIText.BranchAction_checkingOut, refName);
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					new BranchOperation(repository, refName).execute(monitor);
				} catch (CoreException e) {
					return Activator.createErrorStatus(
							UIText.BranchAction_branchFailed, e);
				} finally {
					GitLightweightDecorator.refresh();
