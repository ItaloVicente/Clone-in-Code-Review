	private Combo combo;

	private CCombo ccombo;

	public ComboViewer(Composite parent) {
		this(parent, SWT.READ_ONLY | SWT.BORDER);
	}

	public ComboViewer(Composite parent, int style) {
		this(new Combo(parent, style));
	}

	public ComboViewer(Combo list) {
		this.combo = list;
		hookControl(list);
	}

	public ComboViewer(CCombo list) {
		this.ccombo = list;
		hookControl(list);
	}

	@Override
