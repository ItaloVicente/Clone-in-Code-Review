		Set<String> remoteNames = repository.getConfig().getSubsections(ConfigConstants.CONFIG_REMOTE_SECTION);
		if (remoteNames.isEmpty())
			addRemotePage = new AddRemotePage(repository);

		pushBranchPage = new PushBranchPage(repository, commitToPush, ref) {
			@Override
			public void setVisible(boolean visible) {
				if (visible && addRemotePage != null) {
					setSelectedRemote(addRemotePage.getRemoteName(),
							addRemotePage.getSelection().getURI());
				}
				super.setVisible(visible);
			}
		};
		pushBranchPage.setShowNewRemoteButton(addRemotePage == null);
