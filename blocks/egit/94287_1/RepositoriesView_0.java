				MessageDialogWithToggle dlg = null;
				boolean canUseNewApi = Platform.getBundle("org.eclipse.jface") //$NON-NLS-1$
						.getVersion()
						.compareTo(Version.valueOf("3.13.0.qualifier")) >= 0; //$NON-NLS-1$

				if(canUseNewApi) {
					LinkedHashMap<String, Integer> buttonLabelToIdMap = new LinkedHashMap<>();
					buttonLabelToIdMap.put(
							UIText.RepositoriesView_CheckoutConfirmationOkButtonLabel,
							IDialogConstants.OK_ID);
					buttonLabelToIdMap.put(IDialogConstants.CANCEL_LABEL,
							IDialogConstants.CANCEL_ID);
					dlg = MessageDialogWithToggle.open(MessageDialog.CONFIRM,
							getViewSite().getShell(),
							UIText.RepositoriesView_CheckoutConfirmationTitle,
							MessageFormat.format(
									UIText.RepositoriesView_CheckoutConfirmationMessage,
									shortName),
							toggleMessage, false, store,
							UIPreferences.SHOW_CHECKOUT_CONFIRMATION, SWT.NONE,
							buttonLabelToIdMap);

				} else {
					dlg = MessageDialogWithToggle.openOkCancelConfirm(
							getViewSite().getShell(),
							UIText.RepositoriesView_CheckoutConfirmationTitle,
							MessageFormat.format(
									UIText.RepositoriesView_CheckoutConfirmationMessage,
									shortName),
							toggleMessage, false, store,
							UIPreferences.SHOW_CHECKOUT_CONFIRMATION);
				}

