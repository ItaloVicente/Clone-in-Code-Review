		myCreateGeneralProjectPage = new GitCreateGeneralProjectPage(myGitDir) {
			@Override
			public void setVisible(boolean visible) {
				setPath(mySelectionPage.getPath());
				super.setVisible(visible);
			}
		};
