		addListener(SWT.Dispose, event -> doDispose());
		addListener(SWT.MouseDown, event -> {
			if (event.button == 1) {
				setMark();
			}
		});
		addListener(SWT.Paint, event -> paintComposite(event.gc));
		addListener(SWT.Resize, event -> {
			Rectangle rect = getClientArea();
			button.setBounds(rect.width - imgBounds.width - 1, 1, imgBounds.width, rect.height - 2);
		});
		addListener(SWT.MouseEnter, event -> {
			HeapStatus.this.updateTooltip = true;
			updateToolTip();
		});
		addListener(SWT.MouseExit, event -> {
			HeapStatus.this.updateTooltip = false;
		});
		button.addListener(SWT.MouseDown, event -> {
			if (!isInGC) {
				arm(true);
			}
		});
		button.addListener(SWT.MouseExit, event -> arm(false));
		button.addListener(SWT.MouseUp, event -> {
			if (event.button == 1 && !isInGC) {
				arm(false);
				gc();
			}
		});
		button.addListener(SWT.Paint, event -> paintButton(event.gc));
