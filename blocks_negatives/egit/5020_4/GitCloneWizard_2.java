		gerritConfiguration = new GerritConfigurationPage() {

			@Override
			public void setVisible(boolean visible) {
				if (visible)
					setSelection(getRepositorySelection().getURI());
				super.setVisible(visible);
			}
		};
		gerritConfiguration.setHelpContext(HELP_CONTEXT);
