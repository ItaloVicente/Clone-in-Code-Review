		op = createOperation(getSelectedResources());
		if(op==null)
			return;
		String jobname = getJobName();
		Job job = new Job(jobname) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
