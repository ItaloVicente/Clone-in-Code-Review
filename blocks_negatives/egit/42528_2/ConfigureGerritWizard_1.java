
	private void configurePushURI() {
		List<URIish> pushURIs = new ArrayList<URIish>(remoteConfig.getPushURIs());
		for (URIish urIish : pushURIs) {
			remoteConfig.removePushURI(urIish);
		}
		URIish pushURI = gerritConfiguration.getURI();
		remoteConfig.addPushURI(pushURI);
	}

	private void configurePushRefSpec() {
		String gerritBranch = gerritConfiguration.getBranch();
		List<RefSpec> pushRefSpecs = new ArrayList<RefSpec>(remoteConfig.getPushRefSpecs());
		for (RefSpec refSpec : pushRefSpecs) {
			remoteConfig.removePushRefSpec(refSpec);
		}
		remoteConfig.addPushRefSpec(new RefSpec(
	}

	private void configureFetchNotes() {
		List<RefSpec> fetchRefSpecs = remoteConfig.getFetchRefSpecs();
		for (RefSpec refSpec : fetchRefSpecs) {
			if(refSpec.matchSource(notesRef))
				return;
		}
	}

	private void configureCreateChangeId() {
		GerritUtil.setCreateChangeId(config);
	}

