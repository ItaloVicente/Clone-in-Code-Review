	private Composite createPersonLabel(Composite parent, ImageDescriptor image,
			String text) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label imageLabel = new Label(composite, SWT.NONE);
		imageLabel.setImage(UIIcons.getImage(resources, image));

		Label textLabel = toolkit.createLabel(composite, text);
		textLabel.setForeground(
				toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		return composite;
	}

