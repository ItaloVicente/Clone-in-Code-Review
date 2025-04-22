		tabFolder.addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				Point absolutePoint = new Point(e.x, e.y);
				Point relativePoint = tabFolder.getDisplay().map(null, tabFolder, absolutePoint);
				CTabItem eventTabItem = tabFolder.getItem(relativePoint);

				if (eventTabItem == null) {
					Rectangle clientArea = tabFolder.getClientArea();
					if (!clientArea.contains(relativePoint)) {
						eventTabItem = tabFolder.getSelection();
					}
				}
