		}
		return buffer.toString();
	}

	public static String asString(FontData value) {
		Assert.isNotNull(value);
		StringBuilder buffer = new StringBuilder();
		buffer.append(value.getName());
		buffer.append(SEPARATOR);
		int style = value.getStyle();
		boolean bold = (style & SWT.BOLD) == SWT.BOLD;
		boolean italic = (style & SWT.ITALIC) == SWT.ITALIC;
		if (bold && italic) {
			buffer.append(BOLD_ITALIC);
		} else if (bold) {
			buffer.append(BOLD);
		} else if (italic) {
			buffer.append(ITALIC);
		} else {
			buffer.append(REGULAR);
		}

		buffer.append(SEPARATOR);
		buffer.append(value.getHeight());
		return buffer.toString();
	}

