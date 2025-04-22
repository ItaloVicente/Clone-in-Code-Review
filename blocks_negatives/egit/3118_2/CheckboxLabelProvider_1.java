		final Color greenScreen = resourceManager.createColor(new RGB(222, 223, 224));

		s.setBackground(greenScreen);

		final Button b = new Button(s, SWT.CHECK);
		b.setSelection(checked);
		b.setEnabled(enabled);
		b.setBackground(greenScreen);

		b.setLocation(0, 0);
		final Point bSize = b.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		bSize.x = Math.max(bSize.x, bSize.y);
		bSize.y = Math.max(bSize.x, bSize.y);
		b.setSize(bSize);
		s.setSize(bSize);
		s.open();

		final GC gc = new GC(b);
		final Image image = new Image(control.getShell().getDisplay(), bSize.x,
				bSize.y);
		gc.copyArea(image, 0, 0);
