	}

	protected void setupSelectionsBasedOnSelectedTypes() {
	}

	protected void updateSelections(final Map map) {

		Runnable runnable = () -> selectionGroup.updateSelections(map);

		BusyIndicator.showWhile(getShell().getDisplay(), runnable);
	}

	@Override
