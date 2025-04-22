		updateEnablement();
	}

	private void updateEnablement() {
		UIUtils.setEnabledRecursively(pushConfigurationGroup,
				configureGerrit());
		UIUtils.setEnabledRecursively(uriGroup, configureGerrit());
