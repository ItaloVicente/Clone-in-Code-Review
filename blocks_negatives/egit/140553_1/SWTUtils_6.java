		for (int i = 0; i < strings.length; i++) {
			final int length = converter.convertWidthInCharsToPixels(strings[i]
					.length());
			if (minimum < length)
				minimum = length;
		}
