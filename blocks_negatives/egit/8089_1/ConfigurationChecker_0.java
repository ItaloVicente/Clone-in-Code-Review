		if (!hidden) {
			if (FS.DETECTED.gitPrefix() == null) {
				MessageDialogWithToggle dialog = MessageDialogWithToggle
						.openInformation(
								PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow().getShell(),
								UIText.ConfigurationChecker_gitPrefixWarningTitle,
								UIText.ConfigurationChecker_gitPrefixWarningMessage,
								UIText.ConfigurationChecker_doNotShowGitPrefixWarningAgain,
								false, null, null);
				store.setValue(UIPreferences.SHOW_GIT_PREFIX_WARNING,
						!dialog.getToggleState());
			}
		}
