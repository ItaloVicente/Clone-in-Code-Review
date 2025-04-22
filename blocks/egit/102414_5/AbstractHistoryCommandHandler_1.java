		}
		RevCommit commit = AdapterUtils.adapt(selection.getFirstElement(),
				RevCommit.class);
		if (commit == null) {
			throw new ExecutionException(
					UIText.AbstractHistoryCommandHandler_ActionRequiresOneSelectedCommitMessage);
		}
