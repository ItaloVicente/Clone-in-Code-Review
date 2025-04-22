		return super.createButtonBar(parent);
	}

	@Override
	protected Control createHelpControl(Composite parent) {
		toolkit.adapt(parent, false, false);
		Control help = super.createHelpControl(parent);
		toolkit.adapt(help, false, false);
		return help;
