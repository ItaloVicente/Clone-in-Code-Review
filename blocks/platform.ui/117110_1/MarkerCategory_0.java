	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MarkerCategory [name="); //$NON-NLS-1$
		builder.append(name);
		builder.append(", severity="); //$NON-NLS-1$
		builder.append(severity);
		builder.append(", start="); //$NON-NLS-1$
		builder.append(start);
		builder.append(", end="); //$NON-NLS-1$
		builder.append(end);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

