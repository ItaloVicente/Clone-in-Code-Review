				MessageDialogWithToggle dlg = MessageDialogWithToggle
						.openOkCancelConfirm(
								getViewSite().getShell(),
				UIText.RepositoriesView_CheckoutConfirmationTitle,
				MessageFormat.format(UIText.RepositoriesView_CheckoutConfirmationMessage,
										shortName),
										toggleMessage, false, store,
										UIPreferences.SHOW_CHECKOUT_CONFIRMATION);
