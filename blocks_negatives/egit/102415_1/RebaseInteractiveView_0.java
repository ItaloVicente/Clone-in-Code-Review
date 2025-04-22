		manager.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager menuManager) {
				boolean selectionNotEmpty = !planViewer.getSelection()
						.isEmpty();
				boolean rebaseNotStarted = !currentPlan
						.hasRebaseBeenStartedYet();
				boolean menuEnabled = selectionNotEmpty && rebaseNotStarted;
				for (PlanContextMenuAction item : contextMenuItems)
					item.setEnabled(menuEnabled);
