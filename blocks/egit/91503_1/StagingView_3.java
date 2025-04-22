				if (!rebaseContinueButton.getEnabled()
						&& rebaseContinueEnabled) {
					hintMessage = "all conflicts fixed: continue rebase"; //$NON-NLS-1$
					updateMessage();
				} else if (rebaseContinueButton.getEnabled()
						&& !rebaseContinueEnabled) {
					hintMessage = null;
					updateMessage();
				}
