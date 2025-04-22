
	public static String relativize(String base
		if (base.equals(other))

		final String[] baseSegments = base.split(Pattern.quote(File.separator));
		final String[] otherSegments = other.split(Pattern
				.quote(File.separator));

		int commonPrefix = 0;
		while (commonPrefix < baseSegments.length
				&& commonPrefix < otherSegments.length) {
			if (baseSegments[commonPrefix].equals(otherSegments[commonPrefix]))
				commonPrefix++;
			else
				break;
		}

		final StringBuilder builder = new StringBuilder();
		for (int i = commonPrefix; i < baseSegments.length; i++)
		for (int i = commonPrefix; i < otherSegments.length; i++) {
			builder.append(otherSegments[i]);
			if (i < otherSegments.length - 1)
				builder.append(File.separator);
		}
		return builder.toString();
	}
