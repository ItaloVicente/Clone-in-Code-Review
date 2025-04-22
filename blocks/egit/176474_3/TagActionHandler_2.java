		String tagJobName = MessageFormat.format(UIText.TagAction_creating,
				tagName);
		Job job = new Job(tagJobName) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					operation.execute(monitor);
				} catch (CoreException e) {
					if (e.getCause() instanceof GpgConfigurationException) {
						showGpgProblem(e.getStatus());
						return Status.CANCEL_STATUS;
					}
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.TAG.equals(family)
						|| super.belongsTo(family);
			}
		};
		job.setRule(operation.getSchedulingRule());
		job.setUser(true);
