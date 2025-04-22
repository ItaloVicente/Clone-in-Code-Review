	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubContributionItem [visible="); //$NON-NLS-1$
		builder.append(visible);
		builder.append(", "); //$NON-NLS-1$
		if (innerItem != null) {
			builder.append("innerItem="); //$NON-NLS-1$
			builder.append(innerItem);
		}
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}
