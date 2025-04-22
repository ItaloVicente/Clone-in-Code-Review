		Color gray = resourceManager.createColor(new RGB(222, 223, 224));

		Composite composite = new Composite(shell, SWT.NONE);
		RowLayout layout = new RowLayout();
		layout.marginTop = 0;
		layout.marginLeft = 0;
		layout.marginBottom = 0;
		composite.setLayout(layout);
		createButton(composite, gray, true, true);
		createButton(composite, gray, false, true);
		createButton(composite, gray, true, false);
		createButton(composite, gray, false, false);

		Point cSize = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		composite.setSize(cSize);
		shell.setBackground(gray);
		shell.setLocation(0, 0);
		shell.setSize(cSize);

		shell.open();
		GC gc = new GC(composite);
		int buttonX = cSize.x / 4;
		Image[] images = new Image[4];
		Display display = shell.getShell().getDisplay();

		for (int i = 0; i < 4; i++) {
			Image image = new Image(display, buttonX, buttonX);
			gc.copyArea(image, buttonX * i, 0);
			images[i] = getImage(resourceManager, gray, image);
		}

