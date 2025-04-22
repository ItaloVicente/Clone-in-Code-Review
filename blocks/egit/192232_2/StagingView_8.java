		if (pushMode == PushMode.GERRIT) {
			pushSettings.setVisible(false);
			commitAndPushButton.setImage(getImage(UIIcons.GERRIT));
		} else {
			pushSettings.setVisible(true);
			pushSettings.setEnabled(commitAndPushButton.isEnabled());
			commitAndPushButton.setImage(getImage(UIIcons.PUSH));
		}
