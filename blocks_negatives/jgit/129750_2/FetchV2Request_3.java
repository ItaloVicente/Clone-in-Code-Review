	 * @return object ids in the "have" lines of the request
	 */
	@NonNull
	List<ObjectId> getPeerHas() {
		return this.peerHas;
	}

	/**
	 * @return list of references in the "want-ref" lines of the request
