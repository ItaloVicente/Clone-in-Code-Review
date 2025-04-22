		int flags = 0;
		if (width) {
			if (minWidth != ISizeProvider.INFINITE) {
				flags |= SWT.MIN;
			}
			if (maxWidth != ISizeProvider.INFINITE) {
				flags |= SWT.MAX;
			}
			if (quantizedWidth != ISizeProvider.INFINITE || fixedArea != ISizeProvider.INFINITE) {
				flags |= SWT.FILL;
			}
			if (fixedArea != ISizeProvider.INFINITE) {
				flags |= SWT.WRAP;
			}
		} else {
			if (minHeight != ISizeProvider.INFINITE) {
				flags |= SWT.MIN;
			}
			if (maxHeight != ISizeProvider.INFINITE) {
				flags |= SWT.MAX;
			}
			if (quantizedHeight != ISizeProvider.INFINITE || fixedArea != ISizeProvider.INFINITE) {
				flags |= SWT.FILL;
			}
			if (fixedArea != ISizeProvider.INFINITE) {
				flags |= SWT.WRAP;
			}
		}

		return flags;
	}

	public String getSizeFlagsString() {
		StringBuilder result = new StringBuilder();
		result.append("/* (non-Javadoc)\n");
		result.append(" * @see org.eclipse.ui.ISizeProvider#getSizeFlags(boolean)\n");
		result.append(" */\n");
		result.append("public int getSizeFlags(boolean width) {\n");
		result.append("\tint flags = 0;\n");
		result.append("\tif (width) {\n");
		if (minWidth != ISizeProvider.INFINITE) {
			result.append("\t\tflags |= SWT.MIN;\n");
		}
		if (maxWidth != ISizeProvider.INFINITE) {
			result.append("\t\tflags |= SWT.MAX;\n");
		}
		if (quantizedWidth != ISizeProvider.INFINITE || fixedArea != ISizeProvider.INFINITE) {
			result.append("\t\tflags |= SWT.FILL;\n");
		}
		if (fixedArea != ISizeProvider.INFINITE) {
			result.append("\t\tflags |= SWT.WRAP;\n");
		}
		result.append("\t} else {\n");
		if (minHeight != ISizeProvider.INFINITE) {
			result.append("\t\tflags |= SWT.MIN;\n");
		}
		if (maxHeight != ISizeProvider.INFINITE) {
			result.append("\t\tflags |= SWT.MAX;\n");
		}
		if (quantizedHeight != ISizeProvider.INFINITE || fixedArea != ISizeProvider.INFINITE) {
			result.append("\t\tflags |= SWT.FILL;\n");
		}
		if (fixedArea != ISizeProvider.INFINITE) {
			result.append("\t\tflags |= SWT.WRAP;\n");
		}
		result.append("\t}\n");
		result.append("\treturn flags;\n");
		result.append("}\n\n");
		return result.toString();
	}

	private int getInt(Text text) {
		if (text.getText().equals("")) {
			return ISizeProvider.INFINITE;
		}

		try {
			return Integer.parseInt(text.getText());
		} catch (NumberFormatException e) {
			return ISizeProvider.INFINITE;
		}
	}

	protected void updateLayout() {
		firePropertyChange(IWorkbenchPartConstants.PROP_PREFERRED_SIZE);
	}

	private Text createText(Composite parent) {
		return new Text(parent, SWT.BORDER);
	}

	@Override
