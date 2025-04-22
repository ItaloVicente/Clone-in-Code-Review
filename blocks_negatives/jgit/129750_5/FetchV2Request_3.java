		return this.wantedRefs;
	}

	/**
	 * @return object ids received in the "want" and "want-ref" lines
	 */
	@NonNull
	Set<ObjectId> getWantIds() {
		return wantIds;
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
