		Composite c = createComposite(parent, SWT.NONE);
		return c;
	}

	public Composite createPlainComposite(Composite parent, int style) {
		Composite c = super.createComposite(parent, style);
		c.setBackground(parent.getBackground());
		paintBordersFor(c);
		return c;
	}

	public ScrolledComposite createScrolledComposite(Composite parent, int style) {
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent,
			style);
		return scrolledComposite;
	}

	public CCombo createCCombo(Composite parent, int comboStyle) {
		CCombo combo = new CCombo(parent, comboStyle);
		adapt(combo, true, false);
