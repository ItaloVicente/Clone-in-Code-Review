	private void setNullBackground(final Composite outerCircle) {
		for (Control c : outerCircle.getChildren()) {
			c.setBackground(null);
			if (c instanceof Composite) {
				setNullBackground((Composite) c);
			}
		}
	}

