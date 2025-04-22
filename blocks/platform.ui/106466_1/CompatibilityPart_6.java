
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompatibilityPart ["); //$NON-NLS-1$
		if (part != null) {
			builder.append("partId="); //$NON-NLS-1$
			builder.append(part.getElementId());
			builder.append(", properties="); //$NON-NLS-1$
			builder.append(part.getProperties());
			builder.append(", tags="); //$NON-NLS-1$
			builder.append(part.getTags());
		}
		if (wrapped != null) {
			builder.append(", wrapped="); //$NON-NLS-1$
			builder.append(wrapped.getClass());
		}
		if (legacyPart != null) {
			builder.append(", legacyPart="); //$NON-NLS-1$
			builder.append(legacyPart.getClass());
		}
		builder.append(", beingDisposed="); //$NON-NLS-1$
		builder.append(beingDisposed);
		builder.append(", alreadyDisposed="); //$NON-NLS-1$
		builder.append(alreadyDisposed);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

