
	/**
	 * This will try and make a given path relative to another.
	 * <p>
	 * For example, if this is called with the two following paths :
	 *
	 * <pre>
	 * <code>base = "c:\\Users\\jdoe\\eclipse\\git\\project"</code>
	 * <code>other = "c:\\Users\\jdoe\\eclipse\\git\\another_project\\pom.xml"</code>
	 * </pre>
	 *
	 * This will return "..\\another_project\\pom.xml".
	 * </p>
	 * <p>
	 * This method uses {@link File#separator} to split the paths into segments.
	 * </p>
	 * <p>
	 * <b>Note</b> that this will return the empty String if <code>base</code>
	 * and <code>other</code> are equal.
	 * </p>
	 *
	 * @param base
	 *            The path against which <code>other</code> should be
	 *            relativized. This will be assumed to denote the path to a
	 *            folder and not a file.
	 * @param other
	 *            The path that will be made relative to <code>base</code>.
	 * @return A relative path that, when resolved against <code>base</code>,
	 *         will yield the original <code>other</code>.
	 * @since 3.7
	 */
	public static String relativize(String base, String other) {
		if (base.equals(other))

		final boolean ignoreCase = !FS.DETECTED.isCaseSensitive();
		final String[] baseSegments = base.split(Pattern.quote(File.separator));
		final String[] otherSegments = other.split(Pattern
				.quote(File.separator));

		int commonPrefix = 0;
		while (commonPrefix < baseSegments.length
				&& commonPrefix < otherSegments.length) {
			if (ignoreCase
					&& baseSegments[commonPrefix]
							.equalsIgnoreCase(otherSegments[commonPrefix]))
				commonPrefix++;
			else if (!ignoreCase
					&& baseSegments[commonPrefix]
							.equals(otherSegments[commonPrefix]))
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
