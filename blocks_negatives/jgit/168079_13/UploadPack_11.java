	 * @param pm
	 *            progress monitor
	 * @param pckOut
	 *            PacketLineOut that shares the output with packOut
	 * @param packOut
	 *            packfile output
	 * @param req
	 *            request being processed
	 * @param accumulator
	 *            where to write statistics about the content of the pack.
	 * @param allTags
	 *            refs to search for annotated tags to include in the pack if
	 *            the {@link #OPTION_INCLUDE_TAG} capability was requested.
	 * @param unshallowCommits
	 *            shallow commits on the client that are now becoming unshallow
	 * @param deepenNots
	 *            objects that the client specified using --shallow-exclude
	 * @throws IOException
	 *             if an error occurred while generating or writing the pack.
