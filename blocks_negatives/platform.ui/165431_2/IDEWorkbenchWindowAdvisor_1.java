		if (getWorkbench().getWorkbenchWindowCount() > 1) {
			return true;
		}
		return promptOnExit(getWindowConfigurer().getWindow().getShell());
	}

	/**
	 * Asks the user whether the workbench should really be closed. Only asks if
	 * the preference is enabled.
	 *
	 * @param parentShell
	 *            the parent shell to use for the confirmation dialog
	 * @return <code>true</code> if OK to exit, <code>false</code> if the user
	 *         canceled
	 * @since 3.6
	 */
	static boolean promptOnExit(Shell parentShell) {
		IPreferenceStore store = IDEWorkbenchPlugin.getDefault()
				.getPreferenceStore();
		boolean promptOnExit = store
				.getBoolean(IDEInternalPreferences.EXIT_PROMPT_ON_CLOSE_LAST_WINDOW);

		if (promptOnExit) {
			if (parentShell == null) {
				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (workbenchWindow != null) {
					parentShell = workbenchWindow.getShell();
				}
			}
			if (parentShell != null) {
				parentShell.setMinimized(false);
				parentShell.forceActive();
			}

			String message;

			String productName = null;
			IProduct product = Platform.getProduct();
			if (product != null) {
				productName = product.getName();
			}
			if (productName == null) {
				message = IDEWorkbenchMessages.PromptOnExitDialog_message0;
			} else {
				message = NLS.bind(
						IDEWorkbenchMessages.PromptOnExitDialog_message1,
						productName);
			}

			LinkedHashMap<String, Integer> buttonLabelToIdMap = new LinkedHashMap<>();
			buttonLabelToIdMap.put(IDEWorkbenchMessages.PromptOnExitDialog_button_label_exit, IDialogConstants.OK_ID);
			buttonLabelToIdMap.put(IDialogConstants.CANCEL_LABEL, IDialogConstants.CANCEL_ID);
			MessageDialogWithToggle dlg =
					new MessageDialogWithToggle(
							parentShell,
							IDEWorkbenchMessages.PromptOnExitDialog_shellTitle,
							null,
							message, MessageDialog.CONFIRM, buttonLabelToIdMap, 0,
							IDEWorkbenchMessages.PromptOnExitDialog_choice, false);
			dlg.open();
			if (dlg.getReturnCode() != IDialogConstants.OK_ID) {
				return false;
			}
			if (dlg.getToggleState()) {
				store
						.setValue(
								IDEInternalPreferences.EXIT_PROMPT_ON_CLOSE_LAST_WINDOW,
								false);
				IDEWorkbenchPlugin.getDefault().savePluginPreferences();
			}
		}

