		StringJoiner sj = new StringJoiner(" - "); //$NON-NLS-1$

		String workspaceLocation = wbAdvisor.getWorkspaceLocation();
		if (workspaceLocation != null) {
			sj.add(workspaceLocation);
		}

