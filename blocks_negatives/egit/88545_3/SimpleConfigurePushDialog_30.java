			String content = (String) clipboard.getContents(TextTransfer
					.getInstance());
			if (content == null)
				MessageDialog
						.openConfirm(
								getShell(),
								UIText.SimpleConfigurePushDialog_EmptyClipboardDialogTitle,
								UIText.SimpleConfigurePushDialog_EmptyClipboardDialogMessage);
			try {
				RefSpec spec = new RefSpec(content);
				Ref source;
				try {
					source = repository.findRef(spec.getSource());
				} catch (IOException e1) {
					source = null;
				}
				if (source != null
						|| MessageDialog
								.openQuestion(
										getShell(),
										UIText.SimpleConfigurePushDialog_InvalidRefDialogTitle,
										NLS
												.bind(
														UIText.SimpleConfigurePushDialog_InvalidRefDialogMessage,
														spec.toString())))
					config.addPushRefSpec(spec);

				updateControls();
			} catch (IllegalArgumentException ex) {
				MessageDialog
						.openError(
								getShell(),
								UIText.SimpleConfigurePushDialog_NoRefSpecDialogTitle,
								UIText.SimpleConfigurePushDialog_NoRefSpecDialogMessage);
			}
		} finally {
			clipboard.dispose();
