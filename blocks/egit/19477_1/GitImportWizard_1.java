	private GitCreateGeneralProjectPage createGeneralProjectPage = new GitCreateGeneralProjectPage() {
		public void setVisible(boolean visible) {
			if (visible)
				setPath(importWithDirectoriesPage.getPath());
			super.setVisible(visible);
		}
	};
