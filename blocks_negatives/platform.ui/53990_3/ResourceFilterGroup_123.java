		filterView.getTree().addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				MenuManager mgr = new MenuManager();
				mgr.add(addSubFilterAction);
				mgr.add(addSubGroupFilterAction);
				mgr.add(new Separator());
				mgr.add(new EditFilterAction());
				mgr.add(new RemoveFilterAction());
				filterView.getControl().setMenu(
						mgr.createContextMenu(filterView.getControl()));
			}
