		if (!addPaths.isEmpty()) {
			Job addJob = new Job(UIText.StagingView_AddJob) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						AddCommand add = git.add();
						for (String addPath : addPaths)
							add.addFilepattern(addPath);
						add.call();
					} catch (NoFilepatternException e1) {
					} catch (JGitInternalException e1) {
						Activator.handleError(e1.getCause().getMessage(),
								e1.getCause(), true);
					} catch (Exception e1) {
						Activator.handleError(e1.getMessage(), e1, true);
					}
					return Status.OK_STATUS;
				}

				@Override
				public boolean belongsTo(Object family) {
					return family == JobFamilies.ADD_TO_INDEX;
				}
			};

			schedule(addJob, true);
		}

		if (!rmPaths.isEmpty()) {
			Job removeJob = new Job(UIText.StagingView_RemoveJob) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						RmCommand rm = git.rm().setCached(true);
						for (String rmPath : rmPaths)
							rm.addFilepattern(rmPath);
						rm.call();
					} catch (NoFilepatternException e) {
					} catch (JGitInternalException e) {
						Activator.handleError(e.getCause().getMessage(),
								e.getCause(), true);
					} catch (Exception e) {
						Activator.handleError(e.getMessage(), e, true);
					}
					return Status.OK_STATUS;
				}

				@Override
				public boolean belongsTo(Object family) {
					return family == JobFamilies.REMOVE_FROM_INDEX;
				}
			};

			schedule(removeJob, true);
		}
