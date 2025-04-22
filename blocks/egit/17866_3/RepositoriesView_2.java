	private void executeOpenCommandWithConfirmation(String refName) {
		if (!BranchOperationUI.checkoutWillShowQuestionDialog(refName)) {
			String shortName = Repository.shortenRefName(refName);

			IPreferenceStore store = Activator.getDefault()
					.getPreferenceStore();

			if (store.getBoolean(UIPreferences.SHOW_CHECKOUT_CONFIRMATION)) {
				String toggleMessage = UIText.RepositoriesView_CheckoutConfirmationToggleMessage;
				MessageDialogWithToggle dlg = MessageDialogWithToggle
						.openOkCancelConfirm(
								getViewSite().getShell(),
				UIText.RepositoriesView_CheckoutConfirmationTitle,
				MessageFormat.format(UIText.RepositoriesView_CheckoutConfirmationMessage,
										shortName),
										toggleMessage, false, store,
										UIPreferences.SHOW_CHECKOUT_CONFIRMATION);
				if (dlg.getReturnCode() != Window.OK)
					return;
			}
		}
		executeOpenCommand();
	}

