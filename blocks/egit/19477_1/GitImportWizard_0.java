	private GitProjectsImportPage projectsImportPage = new GitProjectsImportPage() {
		public void setVisible(boolean visible) {
			if (visible)
				setProjectsList(importWithDirectoriesPage.getPath());
			super.setVisible(visible);
		}
	};
