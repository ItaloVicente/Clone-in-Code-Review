		gerritConfiguration = new GerritConfigurationPage() {

			@Override
			public void setVisible(boolean visible) {
				if (visible)
					setSelection(cloneSource.getSelection());
				super.setVisible(visible);
			}
		};
		gerritConfiguration.setHelpContext(HELP_CONTEXT);
