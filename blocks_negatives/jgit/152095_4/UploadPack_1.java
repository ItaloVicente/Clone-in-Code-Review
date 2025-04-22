	private void sendPack(PackStatistics.Accumulator accumulator,
			FetchRequest req,
			@Nullable Collection<Ref> allTags,
			List<ObjectId> unshallowCommits,
			List<ObjectId> deepenNots,
			PacketLineOut pckOut) throws IOException {
		Set<String> caps = req.getClientCapabilities();
		boolean sideband = caps.contains(OPTION_SIDE_BAND)
				|| caps.contains(OPTION_SIDE_BAND_64K);
