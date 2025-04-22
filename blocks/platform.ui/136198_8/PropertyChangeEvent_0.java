
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append(" ["); //$NON-NLS-1$
		builder.append("property="); //$NON-NLS-1$
		builder.append(getProperty());
		builder.append(", "); //$NON-NLS-1$
		builder.append("new value="); //$NON-NLS-1$
		builder.append(getNewValue());
		builder.append(", "); //$NON-NLS-1$
		builder.append("old value="); //$NON-NLS-1$
		builder.append(getOldValue());
		builder.append(", "); //$NON-NLS-1$
		builder.append("source="); //$NON-NLS-1$
		builder.append(getSource());
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

