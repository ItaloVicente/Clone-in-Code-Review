				if (source != null
						|| MessageDialog
								.openQuestion(
										getShell(),
										UIText.SimpleConfigureFetchDialog_InvalidRefDialogTitle,
										NLS
												.bind(
														UIText.SimpleConfigureFetchDialog_InvalidRefDialogMessage,
														spec.toString())))
					config.addFetchRefSpec(spec);

				updateControls();
			} catch (IllegalArgumentException ex) {
				MessageDialog
						.openError(
								getShell(),
								UIText.SimpleConfigureFetchDialog_NotRefSpecDialogTitle,
								UIText.SimpleConfigureFetchDialog_NotRefSpecDialogMessage);
			}
		} finally {
			clipboard.dispose();
