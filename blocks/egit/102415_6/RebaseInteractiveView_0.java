		Control c = planViewer.getControl();
		manager.setRemoveAllWhenShown(true);
		manager.addMenuListener(m -> {
			c.setFocus();
			boolean selectionNotEmpty = !planViewer.getSelection().isEmpty();
			boolean rebaseNotStarted = currentPlan != null
					&& !currentPlan.hasRebaseBeenStartedYet();
			boolean menuEnabled = selectionNotEmpty && rebaseNotStarted;
			if (menuEnabled) {
				for (PlanContextMenuAction item : contextMenuItems) {
					m.add(item);
				}
