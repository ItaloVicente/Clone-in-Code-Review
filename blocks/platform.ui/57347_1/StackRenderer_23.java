		ctf.addMenuDetectListener(e -> {
			Point absolutePoint = new Point(e.x, e.y);
			Point relativePoint = ctf.getDisplay().map(null, ctf,
					absolutePoint);
			CTabItem eventTabItem = ctf.getItem(relativePoint);

			if (eventTabItem == null) {
				Rectangle clientArea = ctf.getClientArea();
				if (!clientArea.contains(relativePoint)) {
					eventTabItem = ctf.getSelection();
