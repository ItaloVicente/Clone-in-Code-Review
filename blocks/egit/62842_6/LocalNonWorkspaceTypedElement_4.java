	private void updateLinkResource(boolean wasBroken,
			java.nio.file.Path link) {
		boolean brokenNow = !Files.exists(link);
		if (brokenNow == wasBroken) {
			return;
		}
		IPath parentPath = path.removeLastSegments(1);
		@SuppressWarnings("null")
		final IContainer parent = ResourceUtil
				.getContainerForLocation(parentPath, true);
		if (parent != null) {
			WorkspaceJob job = new WorkspaceJob("Refreshing " + parentPath) { //$NON-NLS-1$
				@Override
				public IStatus runInWorkspace(IProgressMonitor m)
						throws CoreException {
					parent.refreshLocal(IResource.DEPTH_ONE, m);
					return Status.OK_STATUS;
				}
			};
			job.setRule(parent);
			job.setSystem(true);
			job.schedule();
		}
	}

