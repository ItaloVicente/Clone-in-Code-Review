	private IProjectDescription createDescription(final boolean remove,
			String path, boolean insidews, IWorkspaceRoot root) {
		Path ppath = new Path(path);
		String projectName = ppath.lastSegment();
		URI locationURI;
		URI top;
		if (insidews) {
			top = root.getRawLocationURI();
			testRoot = URIUtil.toPath(top).toFile().getAbsoluteFile();
		} else {
			File tempName;
			try {
				tempName = testUtils.createTempDir("wssupplement");
				testRoot = tempName;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			top = URIUtil.toURI(tempName.getAbsolutePath());
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

