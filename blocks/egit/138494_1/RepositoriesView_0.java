	private void executeFetchCommand(FetchNode node) {
		CommonUtils.runCommand("org.eclipse.egit.ui.team.SimpleFetch", //$NON-NLS-1$
				new StructuredSelection(node));
	}

	private void executePushCommand(PushNode node) {
		CommonUtils.runCommand("org.eclipse.egit.ui.team.SimplePush", //$NON-NLS-1$
				new StructuredSelection(node));
	}

