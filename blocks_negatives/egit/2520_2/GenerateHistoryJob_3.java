
			if (monitor.isCanceled()) {
				page.setErrorMessage(NLS
						.bind(UIText.GenerateHistoryJob_CancelMessage, page
								.getName()));
				return Status.CANCEL_STATUS;
			}
			updateUI();
