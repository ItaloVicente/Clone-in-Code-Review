		final Object[] checkedElements = viewer.getCheckedElements();
		Map<IProject, File> ret = new HashMap<IProject, File>(checkedElements.length);
		for (Object ti : viewer.getCheckedElements()) {
			final IProject project = ((ProjectAndRepo)ti).getProject();
			String path = ((ProjectAndRepo)ti).getRepo();
			final IPath selectedRepo = Path.fromOSString(path);
