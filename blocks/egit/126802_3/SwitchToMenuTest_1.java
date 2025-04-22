	private void mockMultiProjectSelection(String... projNames) {

		List<IProject> projects = new ArrayList<>();
		for (String s : projNames) {
			projects.add(
					ResourcesPlugin.getWorkspace().getRoot().getProject(s));
		}
		mockSelection(new StructuredSelection(projects));
	}

