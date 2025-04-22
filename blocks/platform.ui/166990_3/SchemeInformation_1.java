
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SchemeInformation ["); //$NON-NLS-1$
		if (name != null) {
			builder.append("name="); //$NON-NLS-1$
			builder.append(name);
			builder.append(", "); //$NON-NLS-1$
		}
		if (description != null) {
			builder.append("description="); //$NON-NLS-1$
			builder.append(description);
			builder.append(", "); //$NON-NLS-1$
		}
		builder.append("handled="); //$NON-NLS-1$
		builder.append(handled);
		builder.append(", "); //$NON-NLS-1$
		if (handlerInstanceLocation != null) {
			builder.append("handlerInstanceLocation="); //$NON-NLS-1$
			builder.append(handlerInstanceLocation);
		}
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}
