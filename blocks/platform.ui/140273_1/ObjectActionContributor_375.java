			ssel = newSelection;
		}

		final IStructuredSelection selection = ssel;

		for (int i = 0; i < currentContribution.actions.size(); i++) {
			ActionDescriptor ad = (ActionDescriptor) currentContribution.actions.get(i);
			if (!actionIdOverrides.contains(ad.getId())) {
				currentContribution.contributeMenuAction(ad, menu, true);
				if (ad.getAction() instanceof ObjectPluginAction) {
					final ObjectPluginAction action = (ObjectPluginAction) ad.getAction();
					ISafeRunnable runnable = new ISafeRunnable() {
