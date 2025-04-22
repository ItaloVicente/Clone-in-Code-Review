	@AfterClass
	public static void afterClass() throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		project.close(null);
		project.delete(false, null);
