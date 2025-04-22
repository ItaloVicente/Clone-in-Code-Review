		if (paths.size() > WARNING_THRESHOLD) {
			Shell shell = PlatformUI.getWorkbench()
					.getModalDialogShellProvider().getShell();
			if (!MessageDialog.openQuestion(shell,
					MessageFormat.format(
							UIText.IgnoreActionHandler_manyFilesToBeIgnoredTitle,
							Integer.toString(paths.size())),
					UIText.IgnoreActionHandler_manyFilesToBeIgnoredQuestion)) {
				return;
			}
		}
