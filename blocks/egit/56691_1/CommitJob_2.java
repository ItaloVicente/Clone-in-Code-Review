					openPushWizard(pushWizard);
			} catch (IOException e) {
				Activator.handleError(
						NLS.bind(UIText.CommitUI_pushFailedMessage, e), e,
						true);
			}
