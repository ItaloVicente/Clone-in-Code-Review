	public void rollback() throws Exception {
		ResetOperation rop = new ResetOperation(repo, TAG_NAME, ResetType.HARD);
		rop.execute(null);
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(PROJ1);
		project.getFile(PATCH_FILE).delete(true, null);
