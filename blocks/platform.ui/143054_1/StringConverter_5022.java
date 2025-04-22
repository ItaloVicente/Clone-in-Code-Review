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

	public static String asString(Point value) {
		Assert.isNotNull(value);
		StringBuilder buffer = new StringBuilder();
		buffer.append(value.x);
		buffer.append(',');
		buffer.append(value.y);
		return buffer.toString();
	}

	public static String asString(Rectangle value) {
		Assert.isNotNull(value);
		StringBuilder buffer = new StringBuilder();
		buffer.append(value.x);
		buffer.append(',');
		buffer.append(value.y);
		buffer.append(',');
		buffer.append(value.width);
		buffer.append(',');
		buffer.append(value.height);
		return buffer.toString();
	}

	public static String asString(RGB value) {
		Assert.isNotNull(value);
		StringBuilder buffer = new StringBuilder();
		buffer.append(value.red);
		buffer.append(',');
		buffer.append(value.green);
		buffer.append(',');
		buffer.append(value.blue);
		return buffer.toString();
	}

	public static String asString(boolean value) {
		return String.valueOf(value);
	}

	public static String removeWhiteSpaces(String s) {
		boolean found = false;
		int wsIndex = -1;
		int size = s.length();
		for (int i = 0; i < size; i++) {
			found = Character.isWhitespace(s.charAt(i));
			if (found) {
				wsIndex = i;
				break;
			}
		}
		if (!found) {
