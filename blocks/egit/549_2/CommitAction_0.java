		} catch (CoreException e) {
			handle(
					new TeamException(UIText.CommitAction_errorComputingDiffs,
							e), UIText.CommitAction_errorDuringCommit,
					UIText.CommitAction_errorComputingDiffs);
			return;
