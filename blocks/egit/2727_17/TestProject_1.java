	public File getWorkspaceSupplement() {
		return workspaceSupplement;
	}

	private IProjectDescription createDescription(final boolean remove,
			String path, boolean insidews, IWorkspaceRoot root, File workspaceSupplement) {
		Path ppath = new Path(path);
		String projectName = ppath.lastSegment();
		URI locationURI;
		URI top;
		if (insidews) {
			top = root.getRawLocationURI();
		} else {
			top = URIUtil.toURI(workspaceSupplement.getAbsolutePath());
		}
		if (!insidews || !ppath.lastSegment().equals(path)) {
			locationURI = URIUtil.toURI(URIUtil.toPath(top).append(path));
		} else
			locationURI = null;
		IProjectDescription description = ResourcesPlugin.getWorkspace()
				.newProjectDescription(projectName);

		description.setName(projectName);
		description.setLocationURI(locationURI);
		return description;
	}

