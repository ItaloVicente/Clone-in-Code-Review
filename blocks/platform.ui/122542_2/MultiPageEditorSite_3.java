
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(context);
		builder.append(", active="); //$NON-NLS-1$
		builder.append(active);
		return builder.toString();
	}

