		IInputValidator validator = new IInputValidator() {
			public String isValid(String newText) {
				if (remoteNames.contains(newText))
					return NLS
							.bind(
									UIText.ConfigureRemoteCommand_RemoteAlreadyExistsMessage,
									newText);
				return null;
			}
		};
		InputDialog dlg = new InputDialog(getShell(event),
				UIText.ConfigureRemoteCommand_RemoteNameDialogTitle,
				UIText.ConfigureRemoteCommand_RemoteNameDialogMessage, null,
				validator);
		if (dlg.open() == Window.OK) {
			try {
				RemoteConfig rc = new RemoteConfig(config, dlg.getValue());
				rc.update(config);
				config.save();
			} catch (URISyntaxException e) {
				Activator.handleError(e.getMessage(), e, true);
				return null;
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
				return null;
			}
		}
