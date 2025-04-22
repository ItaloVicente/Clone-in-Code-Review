		MenuManager manager = new MenuManager();

		manager.add(new PlanContextMenuActionListener(
				UIText.RebaseInteractiveStepActionToolBarProvider_PickText,
				UIIcons.CHERRY_PICK, RebaseInteractivePlan.ElementAction.PICK,
				planViewer, actionToolBarProvider));
		manager.add(new PlanContextMenuActionListener(
				UIText.RebaseInteractiveStepActionToolBarProvider_SkipText,
				UIIcons.REBASE_SKIP, RebaseInteractivePlan.ElementAction.SKIP,
				planViewer, actionToolBarProvider));
		manager.add(new PlanContextMenuActionListener(
				UIText.RebaseInteractiveStepActionToolBarProvider_EditText,
				UIIcons.EDITCONFIG, RebaseInteractivePlan.ElementAction.EDIT,
				planViewer, actionToolBarProvider));
		manager.add(new PlanContextMenuActionListener(
				UIText.RebaseInteractiveStepActionToolBarProvider_SquashText,
				UIIcons.SQUASH, RebaseInteractivePlan.ElementAction.SQUASH,
				planViewer, actionToolBarProvider));
		manager.add(new PlanContextMenuActionListener(
				UIText.RebaseInteractiveStepActionToolBarProvider_FixupText,
				UIIcons.FIXUP, RebaseInteractivePlan.ElementAction.FIXUP,
				planViewer, actionToolBarProvider));
		manager.add(new PlanContextMenuActionListener(
				UIText.RebaseInteractiveStepActionToolBarProvider_RewordText,
				UIIcons.REWORD, RebaseInteractivePlan.ElementAction.REWORD,
				planViewer, actionToolBarProvider));

		Menu menu = manager.createContextMenu(planViewer.getControl());
		planViewer.getControl().setMenu(menu);
