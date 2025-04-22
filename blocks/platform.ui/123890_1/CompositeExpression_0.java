
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		Expression[] children = getChildren();
		if (children.length > 0) {
			builder.append(" [children="); //$NON-NLS-1$
			builder.append(Arrays.toString(children));
			builder.append("]"); //$NON-NLS-1$
		}
		return builder.toString();
	}

