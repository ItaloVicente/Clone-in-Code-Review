		MessageDialogWithToggle dialog = MessageDialogWithToggle
				.openYesNoQuestion(window.getShell(),
						ResourceMessages.NewProject_perspSwitchTitle, message,
						null /* use the default message for the toggle */,
						false /* toggle is initially unchecked */, store,
						IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE);
