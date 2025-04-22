	private void updateBranchItems() {
		String branchTextBefore = branchText.getText();
		branchText.removeAll();
		addBranchItems();
		branchText.setText(branchTextBefore);
	}

	private void addBranchItems() {
		String remote = remoteText.getText();
		try {
			if (remote.equals(".") || remote.length() == 0) //$NON-NLS-1$
				addBranchItemsForLocal();
			else
				addBranchItemsForRemote(remote);
		} catch (IOException e) {
			Activator.logError(
					UIText.BranchConfigurationDialog_ExceptionGettingRefs, e);
		} catch (URISyntaxException e) {
			Activator.logError(
					UIText.BranchConfigurationDialog_ExceptionGettingRefs, e);
		}
	}

	private void addBranchItemsForLocal() throws IOException {
		Collection<Ref> localRefs = myRepository.getRefDatabase()
				.getRefs(Constants.R_HEADS).values();
		for (Ref ref : localRefs)
			branchText.add(ref.getName());
	}

	private void addBranchItemsForRemote(String remote) throws IOException,
			URISyntaxException {
		RemoteConfig remoteConfig = new RemoteConfig(myConfig, remote);
		List<RefSpec> fetchSpecs = remoteConfig.getFetchRefSpecs();
		if (fetchSpecs.isEmpty()) {
			addBranchItemsForLocal();
			return;
		}

		Collection<Ref> allRefs = myRepository.getRefDatabase()
				.getRefs(Constants.R_REFS).values();
		for (Ref ref : allRefs) {
			for (RefSpec fetchSpec : fetchSpecs) {
				if (fetchSpec.matchDestination(ref)) {
					RefSpec source = fetchSpec.expandFromDestination(ref);
					String refNameOnRemote = source.getSource();
					branchText.add(refNameOnRemote);
				}
			}
		}
	}

