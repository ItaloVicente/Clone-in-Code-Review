		IWorkspaceRunnable action = monitor -> {
			try {
				reword(monitor);
			} catch (IOException e) {
				throw new TeamException(e.getMessage(), e);
			}
		};
