
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("E4HandlerProxy ["); //$NON-NLS-1$
		builder.append("handler="); //$NON-NLS-1$
		builder.append(handler);
		if (command != null) {
			builder.append(", "); //$NON-NLS-1$
			builder.append("command="); //$NON-NLS-1$
			builder.append(command);
		}
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

