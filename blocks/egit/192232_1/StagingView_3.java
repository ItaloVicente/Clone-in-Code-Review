		if (pushMode == PushMode.GERRIT) {
			pushSettingsBar.setVisible(false);
			commitAndPushButton.setImage(getImage(UIIcons.GERRIT));
		} else {
			pushSettingsBar.setVisible(true);
			pushSettingsBar.setEnabled(commitAndPushButton.isEnabled());
			commitAndPushButton.setImage(getImage(UIIcons.PUSH));
		}
