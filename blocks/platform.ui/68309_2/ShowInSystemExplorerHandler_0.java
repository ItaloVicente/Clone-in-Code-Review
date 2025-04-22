	private IResource getResourceByParameter(ExecutionEvent event) {
		String parameter = event.getParameter(RESOURCE_PATH_PARAMETER);
		if (parameter == null) {
			return null;
		}
		IPath path = new Path(parameter);
		IResource item = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(path);

		return item;
	}

