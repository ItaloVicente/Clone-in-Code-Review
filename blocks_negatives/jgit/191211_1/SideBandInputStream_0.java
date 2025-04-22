	 * Breaks provided string by line, trims each resultant string and returns
	 * them as a list. Note that a String that does not end with a line
	 * terminator will also be returned. Lines with no content after trim will
	 * be returned as empty Strings in the list.
	 *
	 * @implNote This method should be ported to <code>String</code> utility
	 *           methods when Java 11 arrives. Compare <a href=
	 *           569917 - Bump JGit minimum execution environment to Java SE
	 *           11</a>.
