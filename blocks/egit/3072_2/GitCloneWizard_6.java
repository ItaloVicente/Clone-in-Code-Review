	private void doGerritConfiguration(final String remoteName,
			final CloneOperation op) {
		String gerritBranch = gerritConfiguration.getBranch();
		URIish pushURI = gerritConfiguration.getURI();
		if (gerritBranch != null && gerritBranch.length() > 0) {
			ConfigurePushAfterCloneTask push = new ConfigurePushAfterCloneTask(remoteName,
					"HEAD:refs/for/" + gerritBranch, pushURI); //$NON-NLS-1$
			op.addPostCloneTask(push);
		}
	}

