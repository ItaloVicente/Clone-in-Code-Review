	private void display(final int cmp) {
		final StringBuilder m = new StringBuilder();
		m.append('\r');
		m.append(msg);
		m.append(": ");
		while (m.length() < 25)
			m.append(' ');
