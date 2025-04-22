	private void setRefAssist(RemoteConfig config) {
		this.assist = new RefContentAssistProvider(
				PushBranchPage.this.repository, config.getURIs().get(0),
				getShell());
	}

