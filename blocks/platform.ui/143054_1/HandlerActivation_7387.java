		StringBuilder builder = new StringBuilder();
		builder.append("["); //$NON-NLS-1$
		if (handler != null) {
			builder.append(handler);
			builder.append(", "); //$NON-NLS-1$
		}
		if (commandId != null) {
			builder.append(" for '"); //$NON-NLS-1$
			builder.append(commandId);
			builder.append("', "); //$NON-NLS-1$
		}
		if (context != null) {
			builder.append(" in "); //$NON-NLS-1$
			builder.append(context);
			builder.append(", "); //$NON-NLS-1$
		}
		if (activeWhen != null) {
			builder.append("activeWhen="); //$NON-NLS-1$
			builder.append(activeWhen);
			builder.append(", "); //$NON-NLS-1$
		}
		builder.append("active="); //$NON-NLS-1$
		builder.append(active);
		builder.append(", sourcePriority="); //$NON-NLS-1$
		builder.append(sourcePriority);
		builder.append(", participating="); //$NON-NLS-1$
		builder.append(participating);
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
