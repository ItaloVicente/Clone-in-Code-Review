				String[] msg = { message };
				boolean[] doChangeId = { false };
				PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
					Shell parentShell = PlatformUI.getWorkbench()
							.getModalDialogShellProvider().getShell();
					CommitMessageEditorDialog dialog = new CommitMessageEditorDialog(
							parentShell, repo, msg[0], mode, commentChar,
							UIText.CommitMessageEditorDialog_OkButton,
							UIText.SquashHandler_EditMessageDialogCancelButton);
					if (dialog.open() == Window.OK) {
						msg[0] = dialog.getCommitMessage();
						doChangeId[0] = dialog.isWithChangeId();
					}
				});

				String edited = msg[0];
