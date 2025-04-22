				if (!rebaseContinueButton.getEnabled()
						&& rebaseContinueEnabled) {
					hintMessage = UIText.StagingView_rebaseContinueHint;
					updateMessage();
				} else if (rebaseContinueButton.getEnabled()
						&& !rebaseContinueEnabled) {
					hintMessage = null;
					updateMessage();
				}
