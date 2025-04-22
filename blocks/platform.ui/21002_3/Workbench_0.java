	private static void setSearchContribution(MApplication app, boolean enabled) {
		for (MTrimContribution contribution : app.getTrimContributions()) {
			if ("org.eclipse.ui.ide.application.trimcontribution.QuickAccess".contains(contribution //$NON-NLS-1$
					.getElementId())) {
				contribution.setToBeRendered(enabled);
			}
		}
	}

