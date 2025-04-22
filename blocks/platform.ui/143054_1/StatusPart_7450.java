		int spacing = 8;
		int margins = 8;
		layout.marginBottom = margins;
		layout.marginTop = margins;
		layout.marginLeft = margins;
		layout.marginRight = margins;
		layout.horizontalSpacing = spacing;
		layout.verticalSpacing = spacing;
		parent.setLayout(layout);

		Label imageLabel = new Label(parent, SWT.NONE);
		imageLabel.setBackground(bgColor);
		Image image = getImage();
		if (image != null) {
			image.setBackground(bgColor);
			imageLabel.setImage(image);
