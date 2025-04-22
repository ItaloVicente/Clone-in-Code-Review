	/**
	 *  @return the set of objects the incoming pack assumed for delta purposes
	 */
	public Set<ObjectId> getBaseObjectIds() {
		return baseIds == null ?
				Collections.<ObjectId>emptySet() : baseIds;
