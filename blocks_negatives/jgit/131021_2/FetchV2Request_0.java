	/**
	 * The value in a "deepen-since" line in the request, indicating the
	 * timestamp where to stop fetching/cloning.
	 *
	 * @return timestamp in seconds since the epoch, where to stop the shallow
	 *         fetch/clone. Defaults to 0 if not set in the request.
	 */
	int getDeepenSince() {
		return deepenSince;
	}

	/**
	 * @return refs received in "deepen-not" lines.
	 */
	@NonNull
	List<String> getDeepenNotRefs() {
		return deepenNotRefs;
	}

