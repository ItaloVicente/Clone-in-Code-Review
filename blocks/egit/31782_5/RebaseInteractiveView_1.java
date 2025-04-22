	private void createPopupMenu(final TreeViewer planViewer) {
		createContextMenuItems(planViewer);

		MenuManager manager = new MenuManager();
		manager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager menuManager) {
				boolean selectionNotEmpty = !planViewer.getSelection()
						.isEmpty();
				boolean rebaseNotStarted = !currentPlan
						.hasRebaseBeenStartedYet();
				boolean menuEnabled = selectionNotEmpty && rebaseNotStarted;
				for (PlanContextMenuAction item : contextMenuItems)
					item.setEnabled(menuEnabled);
			}
		});

		for (PlanContextMenuAction item : contextMenuItems)
			manager.add(item);

		Menu menu = manager.createContextMenu(planViewer.getControl());
		planViewer.getControl().setMenu(menu);
	}

	private void createContextMenuItems(final TreeViewer planViewer) {
		contextMenuItems = new ArrayList<PlanContextMenuAction>();

		contextMenuItems.add(new PlanContextMenuAction(
				UIText.RebaseInteractiveStepActionToolBarProvider_PickText,
				UIIcons.CHERRY_PICK, RebaseInteractivePlan.ElementAction.PICK,
				planViewer, actionToolBarProvider));
		contextMenuItems.add(new PlanContextMenuAction(
				UIText.RebaseInteractiveStepActionToolBarProvider_SkipText,
				UIIcons.REBASE_SKIP, RebaseInteractivePlan.ElementAction.SKIP,
				planViewer, actionToolBarProvider));
		contextMenuItems.add(new PlanContextMenuAction(
				UIText.RebaseInteractiveStepActionToolBarProvider_EditText,
				UIIcons.EDITCONFIG, RebaseInteractivePlan.ElementAction.EDIT,
				planViewer, actionToolBarProvider));
		contextMenuItems.add(new PlanContextMenuAction(
				UIText.RebaseInteractiveStepActionToolBarProvider_SquashText,
				UIIcons.SQUASH, RebaseInteractivePlan.ElementAction.SQUASH,
				planViewer, actionToolBarProvider));
		contextMenuItems.add(new PlanContextMenuAction(
				UIText.RebaseInteractiveStepActionToolBarProvider_FixupText,
				UIIcons.FIXUP, RebaseInteractivePlan.ElementAction.FIXUP,
				planViewer, actionToolBarProvider));
		contextMenuItems.add(new PlanContextMenuAction(
				UIText.RebaseInteractiveStepActionToolBarProvider_RewordText,
				UIIcons.REWORD, RebaseInteractivePlan.ElementAction.REWORD,
				planViewer, actionToolBarProvider));
