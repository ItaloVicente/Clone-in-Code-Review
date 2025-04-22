	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GridDataFactory.fillDefaults()\n"); //$NON-NLS-1$
		if (data.exclude) {
			builder.append("    .exclude(true)\n"); //$NON-NLS-1$
		}

		if (data.grabExcessHorizontalSpace || data.grabExcessVerticalSpace) {
			builder.append("    .grab("); //$NON-NLS-1$
			builder.append(data.grabExcessHorizontalSpace);
			builder.append(", "); //$NON-NLS-1$
			builder.append(data.grabExcessVerticalSpace);
			builder.append(")\n"); //$NON-NLS-1$
		}

		if (data.horizontalAlignment != SWT.FILL || data.verticalAlignment != SWT.FILL) {
			builder.append("    .align("); //$NON-NLS-1$
			builder.append(getAlignmentString(data.horizontalAlignment));
			builder.append(", "); //$NON-NLS-1$
			builder.append(getAlignmentString(data.verticalAlignment));
			builder.append(")\n"); //$NON-NLS-1$
		}

		if (data.horizontalIndent != 0 || data.verticalIndent != 0) {
			builder.append("    .indent("); //$NON-NLS-1$
			builder.append(data.horizontalIndent);
			builder.append(", "); //$NON-NLS-1$
			builder.append(data.verticalIndent);
			builder.append(")\n"); //$NON-NLS-1$
		}

		if (data.horizontalSpan != 1 || data.verticalSpan != 1) {
			builder.append("    .span("); //$NON-NLS-1$
			builder.append(data.horizontalSpan);
			builder.append(", "); //$NON-NLS-1$
			builder.append(data.verticalSpan);
			builder.append(")\n"); //$NON-NLS-1$
		}

		if (data.minimumHeight != 1 || data.minimumWidth != 1) {
			builder.append("    .minSize("); //$NON-NLS-1$
			builder.append(data.minimumWidth);
			builder.append(", "); //$NON-NLS-1$
			builder.append(data.minimumHeight);
			builder.append(")\n"); //$NON-NLS-1$
		}

		if (data.widthHint != SWT.DEFAULT || data.heightHint != SWT.DEFAULT) {
			builder.append("    .hint("); //$NON-NLS-1$
			builder.append(getHintString(data.widthHint));
			builder.append(", "); //$NON-NLS-1$
			builder.append(getHintString(data.heightHint));
			builder.append(")\n"); //$NON-NLS-1$
		}

		return builder.toString();
	}

	private String getHintString(int widthHint) {
		if (widthHint == -1) {
			return "SWT.DEFAULT"; //$NON-NLS-1$
		}
		return Integer.toString(widthHint);
	}

	private String getAlignmentString(int alignment) {
		switch (alignment) {
		case SWT.BEGINNING:
			return "SWT.BEGINNING"; //$NON-NLS-1$
		case SWT.END:
			return "SWT.END"; //$NON-NLS-1$
		case SWT.LEFT:
			return "SWT.LEFT"; //$NON-NLS-1$
		case SWT.RIGHT:
			return "SWT.RIGHT"; //$NON-NLS-1$
		case SWT.TOP:
			return "SWT.TOP"; //$NON-NLS-1$
		case SWT.BOTTOM:
			return "SWT.BOTTOM"; //$NON-NLS-1$
		case SWT.CENTER:
			return "SWT.CENTER"; //$NON-NLS-1$
		case SWT.FILL:
			return "SWT.FILL"; //$NON-NLS-1$
		default:
			return Integer.toString(alignment);
		}
	}
