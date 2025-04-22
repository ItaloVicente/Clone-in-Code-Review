				pushMode != null && pushMode == PushMode.GERRIT ? UIIcons.GERRIT
						: UIIcons.PUSH));
		commitAndPushButton
				.setText(canPushHeadOnly() ? UIText.StagingView_PushHEAD
						: canPushWithoutConfirmation(pushMode)
								? UIText.StagingView_CommitAndPush
								: UIText.StagingView_CommitAndPushWithEllipsis);
