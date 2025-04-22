	private void toggleDecoForSettingsImportButtons(Button button, ControlDecoration deco) {
		if (!button.getSelection()) {
			deco.hide();
			return;
		}

		if (Files.exists(Paths.get(getWorkspaceLocation()), LinkOption.NOFOLLOW_LINKS)) {
			deco.show();
		} else {
			deco.hide();
		}
	}
