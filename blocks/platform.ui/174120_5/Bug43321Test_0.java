
		try {
			FileUtil.deleteProject(testProject);
		} catch (CoreException e) {
			fail(e.toString());
		}
