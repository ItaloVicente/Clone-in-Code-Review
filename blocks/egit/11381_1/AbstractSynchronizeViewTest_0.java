	@After
	public void deleteEmptyProject() throws Exception {
		IProject prj = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(EMPTY_PROJECT);
		if (prj.exists())
			prj.delete(false, false, null);
	}

