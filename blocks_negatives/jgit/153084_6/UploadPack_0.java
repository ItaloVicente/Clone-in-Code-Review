	/**
	 * Send the requested objects to the client.
	 *
	 * @param pckOut
	 *            PacketLineOut that shares the output with packOut
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
	 */
	private void sendPack(PacketLineOut pckOut,
			FetchRequest req,
			PackStatistics.Accumulator accumulator,
			@Nullable Collection<Ref> allTags, List<ObjectId> unshallowCommits,
			List<ObjectId> deepenNots) throws IOException {
		if (wantAll.isEmpty()) {
			preUploadHook.onSendPack(this, wantIds, commonBase);
		} else {
			preUploadHook.onSendPack(this, wantAll, commonBase);
