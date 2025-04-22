	@SuppressWarnings("restriction")
	@Override
	public void execute(IAction action) {
		final IResource[] resources = getSelectedResources();
		if (resources.length == 0)
			return;

		WorkspaceJob job = new WorkspaceJob(UIText.IgnoreAction_jobName) {
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				monitor.beginTask(UIText.IgnoreAction_taskName, resources.length);
				try {
					for (IResource resource : resources) {


						if (!Team.isIgnoredHint(resource)) {
							addIgnore(monitor, resource);
						}
						monitor.worked(1);
					}
					monitor.done();
				} catch (CoreException e) {
					throw e;
				} catch (Exception e) {
					throw new CoreException(
							new Status(
									IStatus.ERROR,
									"org.eclipse.egit.ui", UIText.IgnoreAction_error, e)); //$NON-NLS-1$
				}
				return Status.OK_STATUS;
			}

			private void addIgnore(IProgressMonitor monitor, IResource resource)
					throws UnsupportedEncodingException, CoreException {
				IContainer container = resource.getParent();
				IFile gitignore = container.getFile(new Path(
						Constants.GITIGNORE_FILENAME));
				ByteArrayInputStream entryBytes = asStream(entry);

				if (gitignore.exists())
					gitignore.appendContents(entryBytes, true, true, monitor);
				else
					gitignore.create(entryBytes, true, monitor);
			}

			private ByteArrayInputStream asStream(String entry)
					throws UnsupportedEncodingException {
				return new ByteArrayInputStream(entry
						.getBytes(Constants.CHARACTER_ENCODING));
			}
		};
		job.schedule();
	}

	@SuppressWarnings("restriction")
	@Override
	public boolean isEnabled() {
		if (getProjectsInRepositoryOfSelectedResources().length == 0)
			return false;

		IResource[] resources = getSelectedResources();
		for (IResource resource : resources) {
			if (!Team.isIgnoredHint(resource))
				return true;
		}
		return false;
