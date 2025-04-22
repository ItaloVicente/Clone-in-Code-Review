		sash.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.DRAG) {
				return;
			}
			int shift = event.x - sash.getBounds().x;
			GridData data = (GridData) rightControl.getLayoutData();
			int newWidthHint = data.widthHint + shift;
			if (newWidthHint < 20) {
				return;
			}
			Point computedSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
			Point currentSize = getShell().getSize();
			boolean customSize = !computedSize.equals(currentSize);
			data.widthHint = newWidthHint;
			setLastTreeWidth(newWidthHint);
			composite.layout(true);
			computedSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
			if (customSize) {
				computedSize.x = Math.max(computedSize.x, currentSize.x);
