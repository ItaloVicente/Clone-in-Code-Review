
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GridLayoutFactory.fillDefaults()\n"); //$NON-NLS-1$
		if (l.numColumns > 1) {
			builder.append("    .numColumns("); //$NON-NLS-1$
			builder.append(l.numColumns);
			builder.append(")\n"); //$NON-NLS-1$
		}
		if (l.makeColumnsEqualWidth) {
			builder.append("    .equalWidth(true)\n"); //$NON-NLS-1$
		}
		if (l.marginBottom != 0 || l.marginTop != 0 || l.marginLeft != 0 || l.marginRight != 0) {
			builder.append("    .extendedMargins("); //$NON-NLS-1$
			builder.append(l.marginLeft);
			builder.append(", "); //$NON-NLS-1$
			builder.append(l.marginRight);
			builder.append(", "); //$NON-NLS-1$
			builder.append(l.marginTop);
			builder.append(", "); //$NON-NLS-1$
			builder.append(l.marginBottom);
			builder.append(", "); //$NON-NLS-1$
			builder.append(")\n"); //$NON-NLS-1$
		}
		if (l.marginWidth != 0 || l.marginHeight != 0) {
			builder.append("    .margins("); //$NON-NLS-1$
			builder.append(l.marginWidth);
			builder.append(", "); //$NON-NLS-1$
			builder.append(l.marginHeight);
			builder.append(")\n"); //$NON-NLS-1$
		}
		Point defaultSpacing = LayoutConstants.getSpacing();
		if (defaultSpacing.x != l.horizontalSpacing || defaultSpacing.y != l.verticalSpacing) {
			builder.append("    .spacing("); //$NON-NLS-1$
			builder.append(l.horizontalSpacing);
			builder.append(", "); //$NON-NLS-1$
			builder.append(l.verticalSpacing);
			builder.append(")\n"); //$NON-NLS-1$
		}
		return builder.toString();
	}
