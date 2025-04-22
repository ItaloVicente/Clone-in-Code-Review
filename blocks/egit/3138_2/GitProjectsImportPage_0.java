	private void selectAllNewProjects() {
		for (TreeItem item : projectsList.getTree().getItems()) {
			ProjectRecord record = (ProjectRecord) item.getData();
			if (!isProjectInWorkspace(record.getProjectName()))
				projectsList.setChecked(item.getData(), true);
		}
