			}
		});
	}

	private String getInitialBrowsePath() {
		File dir = new File(getWorkspaceLocation());
		while (dir != null && !dir.exists()) {
