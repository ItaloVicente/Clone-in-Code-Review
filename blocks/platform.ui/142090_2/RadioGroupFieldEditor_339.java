		return 1;
	}

	public Composite getRadioBoxControl(Composite parent) {
		if (radioBox == null) {

			Font font = parent.getFont();

			if (useGroup) {
				Group group = new Group(parent, SWT.NONE);
				group.setFont(font);
				String text = getLabelText();
				if (text != null) {
