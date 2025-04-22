	private void setRefAssist(RemoteConfig config) {
		if (config != null && config.getURIs().size() > 0) {
			this.assist = new RefContentAssistProvider(
					PushBranchPage.this.repository, config.getURIs().get(0),
					getShell());
		}
	}

