	/**
	 * Null checker for a {@code @NonNull} parameter.
	 *
	 * <p>This is a briefer equivalent to
	 * <pre>
	 * if (arg == null) {
	 *   throw new NullPointerException();
	 * }
	 * </pre>
	 * with the added benefit that it does not trigger nullness warnings when
	 * {@code arg} is declared as {@code @NonNull}.
	 *
	 * @param arg a non-null object reference
	 * @return arg
	 * @throws NullPointerException if {@code arg} is null
	 * @since 5.4
	 */
	public static <T> T checkNotNull(T arg) {
		if (arg == null) {
			throw new NullPointerException();
		}
		return arg;
	}

