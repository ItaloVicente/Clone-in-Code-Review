		Assert.isNotNull(value);
		StringBuilder buffer = new StringBuilder();
		buffer.append(value.getName());
		buffer.append(SEPARATOR);
		int style = value.getStyle();
		boolean bold = (style & SWT.BOLD) == SWT.BOLD;
		boolean italic = (style & SWT.ITALIC) == SWT.ITALIC;
		if (bold && italic) {
			buffer.append(JFaceResources.getString("BoldItalicFont")); //$NON-NLS-1$
		} else if (bold) {
			buffer.append(JFaceResources.getString("BoldFont")); //$NON-NLS-1$
		} else if (italic) {
			buffer.append(JFaceResources.getString("ItalicFont")); //$NON-NLS-1$
		} else {
			buffer.append(JFaceResources.getString("RegularFont")); //$NON-NLS-1$
		}
		buffer.append(SEPARATOR);
		buffer.append(value.getHeight());
		return buffer.toString();

	}
