		stagedViewer.addSelectionChangedListener(event -> {
			boolean hasSelection = !event.getSelection().isEmpty();
			if (hasSelection != unstageAction.isEnabled()) {
				unstageAction.setEnabled(hasSelection);
				stagedToolBarManager.update(true);
			}
		});
