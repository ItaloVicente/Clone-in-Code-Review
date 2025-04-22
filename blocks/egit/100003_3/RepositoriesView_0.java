				MessageDialogWithToggle dialog = new MessageDialogWithToggle(
						getViewSite().getShell(),
						UIText.RepositoriesView_CheckoutConfirmationTitle, null,
						MessageFormat.format(
								UIText.RepositoriesView_CheckoutConfirmationMessage,
								Repository.shortenRefName(refName)),
						MessageDialog.QUESTION,
						new String[] {
								UIText.RepositoriesView_CheckoutConfirmationDefaultButtonLabel,
								IDialogConstants.CANCEL_LABEL },
						0,
						UIText.RepositoriesView_CheckoutConfirmationToggleMessage,
						false);
				int result = dialog.open();
				if (result != Window.OK
						&& result != IDialogConstants.INTERNAL_ID) {
