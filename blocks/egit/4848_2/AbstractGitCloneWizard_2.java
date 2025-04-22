	private void configureFetchSpec(CloneOperation op,
			GitRepositoryInfo gitRepositoryInfo, String remoteName) {
		for (String fetchRefSpec : gitRepositoryInfo.getFetchRefSpecs()) {
			op.addPostCloneTask(new ConfigureFetchAfterCloneTask(remoteName, fetchRefSpec));
		}
	}

	private void configurePush(CloneOperation op,
			GitRepositoryInfo gitRepositoryInfo, String remoteName) {
		for (PushInfo pushInfo : gitRepositoryInfo.getPushInfos()) {
			try {
				URIish uri = pushInfo.pushUri != null ? new URIish(pushInfo.pushUri) : null;
				ConfigurePushAfterCloneTask task = new ConfigurePushAfterCloneTask(
						remoteName, pushInfo.pushRefSpec, uri);
				op.addPostCloneTask(task);
			} catch (URISyntaxException e) {
				Activator.handleError(UIText.GitCloneWizard_failed, e, true);
			}
		}
	}

	private void configureRepositoryConfig(CloneOperation op, GitRepositoryInfo gitRepositoryInfo) {
		for (RepositoryConfigProperty p : gitRepositoryInfo.getRepositoryConfigProperties()) {
			SetRepositoryConfigPropertyTask task = new SetRepositoryConfigPropertyTask(p.section, p.subsection, p.name, p.value);
			op.addPostCloneTask(task);
		}
	}

