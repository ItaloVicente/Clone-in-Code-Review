		sash.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail != SWT.DRAG) {
					Rectangle clientArea = shell.getClientArea();
					int newWidth = clientArea.width - event.x - (sash.getSize().x + rightSeparator.getSize().x);
					if (newWidth != data.widthHint) {
						data.widthHint = newWidth;
						shell.layout();
					}
