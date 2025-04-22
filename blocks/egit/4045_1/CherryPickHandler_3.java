		});
	}

	private void showFailure(CherryPickResult result) {
		IStatus details = getErrorList(result.getFailingPaths());
		Activator.showErrorStatus(
				UIText.CherryPickHandler_CherryPickFailedMessage, details);
