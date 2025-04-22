    /**
	 * Returns the pattern represented in this binding. This pattern should
	 * conform to the regular expression syntax described by the
	 * <code>java.util.regex.Pattern</code> class. If
	 * {@link #isEqualityPattern()} is <code>true</code> a Pattern will be
	 * generated based on the escaped version of the String returned by
	 * {@link #getString()}.
