
		for (PlanContextMenuAction item : contextMenuItems)
			manager.add(item);

		Menu menu = manager.createContextMenu(planViewer.getControl());
		planViewer.getControl().setMenu(menu);
		planViewer.getControl().addKeyListener(new ContextMenuKeyListener());
