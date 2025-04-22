	 * Checks whether this entry differs from a given entry from the
	 * {@link DirCache}.
	 *
	 * File status information is used and if status is same we consider the
	 * file identical to the state in the working directory. Native git uses
	 * more stat fields than we have accessible in Java.
