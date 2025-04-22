	 * @param accumulator
	 *            where to write statistics about the content of the pack.
	 * @param req
	 *            request in process
	 * @param allTags
	 *            refs to search for annotated tags to include in the pack if
	 *            the {@link #OPTION_INCLUDE_TAG} capability was requested.
	 * @param unshallowCommits
	 *            shallow commits on the client that are now becoming unshallow
	 * @param deepenNots
	 *            objects that the client specified using --shallow-exclude
	 * @param pckOut
	 *            output writer
	 * @throws IOException
	 *             if an error occurred while generating or writing the pack.
