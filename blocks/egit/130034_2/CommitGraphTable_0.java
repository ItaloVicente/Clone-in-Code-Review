		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(manager -> {
			c.setFocus();
			menuMgr.add(new Separator(IWorkbenchActionConstants.HISTORY_GROUP));
			menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			if (copy.isEnabled()) {
				menuMgr.add(new Separator());
				menuMgr.add(copy);
			}
		});
