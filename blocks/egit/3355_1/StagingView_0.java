	private void compareWith(OpenEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		if (selection.isEmpty())
			return;
		StagingEntry stagingEntry = (StagingEntry) selection.getFirstElement();
		switch (stagingEntry.getState()) {
		case ADDED:
		case CHANGED:
		case REMOVED:
		case UNTRACKED:
			runCommand(ActionCommands.COMPARE_WITH_HEAD_ACTION, selection);
			break;

		case MISSING:
		case MODIFIED:
		case PARTIALLY_MODIFIED:
		case CONFLICTING:
		default:
			runCommand(ActionCommands.COMPARE_WITH_INDEX_ACTION, selection);
		}
	}

