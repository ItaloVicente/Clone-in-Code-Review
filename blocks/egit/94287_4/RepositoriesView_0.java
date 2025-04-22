				MessageDialogWithToggle dlg;
				if (canUseJFace313Api) {
					dlg = showCheckoutConfirmationDialog(shortName, store);
				} else {
					dlg = MessageDialogWithToggle.openOkCancelConfirm(
							getViewSite().getShell(),
							UIText.RepositoriesView_CheckoutConfirmationTitle,
							MessageFormat.format(
									UIText.RepositoriesView_CheckoutConfirmationMessage,
									shortName),
							UIText.RepositoriesView_CheckoutConfirmationToggleMessage,
							false, store,
							UIPreferences.SHOW_CHECKOUT_CONFIRMATION);
				}
