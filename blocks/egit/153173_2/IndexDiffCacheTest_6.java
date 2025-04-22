	private void runInWorkspace(Callable<Void> action) throws CoreException {
		ResourcesPlugin.getWorkspace().run(m -> {
			try {
				action.call();
			} catch (Exception e) {
				throw new CoreException(
						Activator.error("Test preparation error", e));
			}
		}, project.getProject(), IWorkspace.AVOID_UPDATE, null);
	}

