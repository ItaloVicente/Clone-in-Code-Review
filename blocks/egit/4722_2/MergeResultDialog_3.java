
		Image image = getImage();
		if (image != null) {
			imageLabel = new Label(composite, SWT.NULL);
			image.setBackground(imageLabel.getBackground());
			imageLabel.setImage(image);
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING).span(2, 1)
			.applyTo(imageLabel);
		}

