	private MessageDialogWithToggle showCheckoutConfirmationDialog(
			String shortName, IPreferenceStore store) {
		if (canUseJFace313Api) {
			LinkedHashMap<String, Integer> buttonLabelToIdMap = new LinkedHashMap<>();
			buttonLabelToIdMap.put(
					UIText.RepositoriesView_CheckoutConfirmationOkButtonLabel,
					IDialogConstants.OK_ID);
			buttonLabelToIdMap.put(IDialogConstants.CANCEL_LABEL,
					IDialogConstants.CANCEL_ID);
			return MessageDialogWithToggle.open(MessageDialog.CONFIRM,
					getViewSite().getShell(),
					UIText.RepositoriesView_CheckoutConfirmationTitle,
					MessageFormat.format(
							UIText.RepositoriesView_CheckoutConfirmationMessage,
							shortName),
					UIText.RepositoriesView_CheckoutConfirmationToggleMessage,
					false, store, UIPreferences.SHOW_CHECKOUT_CONFIRMATION,
					SWT.NONE, buttonLabelToIdMap);
		} else {
			return MessageDialogWithToggle.openOkCancelConfirm(
					getViewSite().getShell(),
					UIText.RepositoriesView_CheckoutConfirmationTitle,
					MessageFormat.format(
							UIText.RepositoriesView_CheckoutConfirmationMessage,
							shortName),
					UIText.RepositoriesView_CheckoutConfirmationToggleMessage,
					false, store, UIPreferences.SHOW_CHECKOUT_CONFIRMATION);
		}
	}


