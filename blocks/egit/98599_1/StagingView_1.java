				selection -> unstage(selection), stageAction);
		unstagedViewer.addSelectionChangedListener(event -> {
			boolean hasSelection = !event.getSelection().isEmpty();
			if (hasSelection != stageAction.isEnabled()) {
				stageAction.setEnabled(hasSelection);
				unstagedToolBarManager.update(true);
			}
		});
