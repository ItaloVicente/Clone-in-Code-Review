		sash.addListener(SWT.Selection, event -> {
			if (event.detail != SWT.DRAG) {
				Rectangle clientArea1 = shell.getClientArea();
				int newWidth = clientArea1.width - event.x - (sash.getSize().x + rightSeparator.getSize().x);
				if (newWidth != data.widthHint) {
					data.widthHint = newWidth;
					shell.layout();
