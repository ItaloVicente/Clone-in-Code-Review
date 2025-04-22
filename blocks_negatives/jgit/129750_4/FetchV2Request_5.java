	/**
	 * Options that tune the expected response from the server, like
	 * "thin-pack", "no-progress" or "ofs-delta"
	 *
	 * These are options listed and well-defined in the git protocol
	 * specification
	 *
	 * @return options found in the request lines
	 */
	@NonNull
	Set<String> getClientCapabilities() {
		return clientCapabilities;
	}

