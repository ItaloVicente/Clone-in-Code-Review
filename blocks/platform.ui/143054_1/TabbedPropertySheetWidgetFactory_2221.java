		return combo;
	}

	public CCombo createCCombo(Composite parent) {
		return createCCombo(parent, SWT.FLAT | SWT.READ_ONLY);
	}

	public Group createGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(text);
		group.setBackground(getColors().getBackground());
		group.setForeground(getColors().getForeground());
		return group;
	}

	public Composite createFlatFormComposite(Composite parent) {
		Composite composite = createComposite(parent);
		FormLayout layout = new FormLayout();
		layout.marginWidth = ITabbedPropertyConstants.HSPACE + 2;
		layout.marginHeight = ITabbedPropertyConstants.VSPACE;
		layout.spacing = ITabbedPropertyConstants.VMARGIN + 1;
		composite.setLayout(layout);
		return composite;
	}

	public CLabel createCLabel(Composite parent, String text) {
		return createCLabel(parent, text, SWT.NONE);
	}

	public CLabel createCLabel(Composite parent, String text, int style) {
		final CLabel label = new CLabel(parent, style);
		label.setBackground(parent.getBackground());
		label.setText(text);
		return label;
	}

	@Override
