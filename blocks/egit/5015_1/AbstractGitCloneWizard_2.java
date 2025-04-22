	private void configureFetchSpec(CloneOperation op,
			GitRepositoryInfo gitRepositoryInfo, String remoteName) {
		for (String fetchRefSpec : gitRepositoryInfo.getFetchRefSpecs())
			op.addPostCloneTask(new ConfigureFetchAfterCloneTask(remoteName, fetchRefSpec));
	}

	private void configurePush(CloneOperation op,
			GitRepositoryInfo gitRepositoryInfo, String remoteName) {
		for (PushInfo pushInfo : gitRepositoryInfo.getPushInfos()) {
			try {
				URIish uri = pushInfo.getPushUri() != null ? new URIish(
						pushInfo.getPushUri()) : null;
				ConfigurePushAfterCloneTask task = new ConfigurePushAfterCloneTask(
						remoteName, pushInfo.getPushRefSpec(), uri);
				op.addPostCloneTask(task);
			} catch (URISyntaxException e) {
				Activator.handleError(UIText.GitCloneWizard_failed, e, true);
			}
		}
	}

	private void configureRepositoryConfig(CloneOperation op, GitRepositoryInfo gitRepositoryInfo) {
		for (RepositoryConfigProperty p : gitRepositoryInfo.getRepositoryConfigProperties()) {
			SetRepositoryConfigPropertyTask task = new SetRepositoryConfigPropertyTask(
					p.getSection(), p.getSubsection(), p.getName(),
					p.getValue());
			op.addPostCloneTask(task);
		}
	}

