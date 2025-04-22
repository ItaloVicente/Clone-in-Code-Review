	 * Returns whether or not a target is matched as being ignored by
	 * any patterns in this directory.
	 * <br>
	 * Will return false if the file is not a descendant of this directory.
	 * <br>
	 *
	 * @param target
	 *			  Absolute path to the file. This makes stripping common path elements easier.
	 * @return
	 * 			  true if target is ignored, false if the target is explicitly not
	 * 			  ignored or if no rules exist for the target.
	 * @throws IOException
	 * 			  Failed to parse rules
	 *
