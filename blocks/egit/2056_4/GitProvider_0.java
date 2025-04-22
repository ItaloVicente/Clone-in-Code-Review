		try {
			GitProjectData.delete(getProject());
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					Activator.getPluginId(), e.getMessage(), e));
		}
