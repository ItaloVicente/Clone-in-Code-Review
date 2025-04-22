	private void createPersonLabel(Composite parent, ImageDescriptor image,
			String text) {
		Label imageLabel = new Label(parent, SWT.NONE);
		imageLabel.setImage(UIIcons.getImage(resources, image));

		Label textLabel = toolkit.createLabel(parent, text);
		textLabel.setForeground(
				toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
	}

