	/**
	 * @return object ids in the "want" (and "want-ref") lines of the request
	 */
	@NonNull
	Set<ObjectId> getWantsIds() {
		return wantsIds;
	}

	/**
	 * Shallow commits the client already has.
	 *
	 * These are sent by the client in "shallow" request lines.
	 *
	 * @return set of commits the client has declared as shallow.
	 */
	@NonNull
	Set<ObjectId> getClientShallowCommits() {
		return clientShallowCommits;
	}

