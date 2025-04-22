			}
			if (e.getCause() instanceof AbortedByHookException) {
				showAbortedByHook(e.getCause());
			} else {
				return Activator.createErrorStatus(
						UIText.CommitAction_CommittingFailed, e);
			}
