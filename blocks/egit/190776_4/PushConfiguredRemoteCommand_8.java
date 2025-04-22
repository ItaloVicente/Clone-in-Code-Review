		if (node instanceof RepositoryNode) {
			try {
				PushOperationUI.pushToUpstream(getShell(event),
						repository);
			} catch (IOException e) {
				throw new ExecutionException(e.getLocalizedMessage(), e);
			}
		} else {
			RemoteConfig config = getRemoteConfig(node);
			if (config == null) {
				MessageDialog.openInformation(getShell(event),
						UIText.SimplePushActionHandler_NothingToPushDialogTitle,
						UIText.SimplePushActionHandler_NothingToPushDialogMessage);
				return null;
			}
			new PushOperationUI(repository, config.getName(), false).start();
		}
