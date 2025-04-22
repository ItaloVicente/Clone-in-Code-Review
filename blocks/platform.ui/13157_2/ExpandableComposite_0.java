	public void setToolTipText(String string) {
		super.setToolTipText(string);
		if (textLabel instanceof Label) {
			((Label) textLabel).setToolTipText(string);
		} else if (textLabel instanceof Hyperlink) {
			((Hyperlink) textLabel).setToolTipText(string);
		}
	}

