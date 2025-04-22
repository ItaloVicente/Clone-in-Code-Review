		Color bg = cell.getBackground();
		composite = new Composite(cell, getStyle());
		composite.setBackground(bg);
		composite.setLayout(new ColorCellLayout());
		colorLabel = new Label(composite, SWT.LEFT);
		colorLabel.setBackground(bg);
		rgbLabel = new Label(composite, SWT.LEFT);
		rgbLabel.setBackground(bg);
		rgbLabel.setFont(cell.getFont());
		return composite;
	}

	@Override
