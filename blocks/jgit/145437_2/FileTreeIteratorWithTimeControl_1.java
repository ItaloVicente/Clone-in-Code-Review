	public Instant getEntryLastModifiedInstant() {
		if (modTimes == null) {
			return EPOCH;
		}
		Instant cutOff = super.getEntryLastModifiedInstant().plusMillis(1);
		SortedSet<Instant> head = modTimes.headSet(cutOff);
		return head.isEmpty() ? EPOCH : head.last();
