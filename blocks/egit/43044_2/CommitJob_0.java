			}
			if (e.getCause() instanceof AbortedByHookException) {
				showAbortedByHook(e.getCause());
				return Status.CANCEL_STATUS;
			} else {
				return Activator.createErrorStatus(
						UIText.CommitAction_CommittingFailed, e);
			}
