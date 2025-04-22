		String message;
		Map<String, MergeFailureReason> reasons = result != null ? result
				.getFailingPaths() : null;
		if (reasons != null && !reasons.isEmpty())
			message = MessageFormat.format(UIText.RevertFailureDialog_Message,
					commit.abbreviate(7).name());
		else
			message = MessageFormat.format(
					UIText.RevertFailureDialog_MessageNoFiles, commit
							.abbreviate(7).name());

