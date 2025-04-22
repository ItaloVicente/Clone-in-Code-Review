				}
			});
		} else if (c == commentViewer.getControl()) {
			final MenuManager mgr = new MenuManager();
			c.setMenu(mgr.createContextMenu(c));
			c.addMenuDetectListener(new MenuDetectListener() {
				public void menuDetected(MenuDetectEvent e) {
					if (mgr.isEmpty()) {
						mgr.add(selectAllAction);
						mgr.add(copyAction);
						mgr.add(new Separator());
						mgr.add(wrapCommentAction);
						mgr.add(fillCommentAction);
