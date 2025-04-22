		Job job = new Job(NLS.bind(UIText.RepositoriesView_CheckingOutMessage,
				ref.getName())) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IWorkspaceRunnable wsr = new IWorkspaceRunnable() {

					public void run(IProgressMonitor myMonitor)
							throws CoreException {
						op.execute(myMonitor);
					}
				};

				try {
					ResourcesPlugin.getWorkspace().run(wsr,
							ResourcesPlugin.getWorkspace().getRoot(),
							IWorkspace.AVOID_UPDATE, monitor);
				} catch (CoreException e1) {
					return new Status(IStatus.ERROR, Activator.getPluginId(),
							e1.getMessage(), e1);
				}

				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(Object family) {
				if (family.equals(JobFamilies.CHECKOUT))
					return true;
				return super.belongsTo(family);
			}
		};

		job.setUser(true);
		job.schedule();
