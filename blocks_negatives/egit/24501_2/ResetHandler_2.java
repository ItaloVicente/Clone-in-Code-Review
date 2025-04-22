		String type = event.getParameter(HistoryViewCommands.RESET_MODE);
		final ResetType resetType;

			resetType = ResetType.HARD;
			resetType = ResetType.MIXED;
			resetType = ResetType.SOFT;
		} else {
		}

		switch (resetType) {
		case HARD:
			if (!MessageDialog.openQuestion(HandlerUtil
					.getActiveShellChecked(event),
					UIText.ResetTargetSelectionDialog_ResetQuestion,
					UIText.ResetTargetSelectionDialog_ResetConfirmQuestion))
				return null;

			jobName = UIText.HardResetToRevisionAction_hardReset;
			break;
		case SOFT:
			jobName = UIText.SoftResetToRevisionAction_softReset;
			break;
		case MIXED:
			jobName = UIText.MixedResetToRevisionAction_mixedReset;
			break;
		}

		ResetOperation operation = new ResetOperation(repo, commit.getName(), resetType);
		JobUtil.scheduleUserWorkspaceJob(operation, jobName, JobFamilies.RESET);
