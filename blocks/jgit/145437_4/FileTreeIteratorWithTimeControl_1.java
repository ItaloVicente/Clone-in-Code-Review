	public Instant getEntryLastModifiedInstant() {
		if (modTimes == null) {
			return EPOCH;
		}
		Instant cutOff = super.getEntryLastModifiedInstant().plusNanos(1);
		SortedSet<Instant> head = modTimes.headSet(cutOff);
		return head.isEmpty() ? EPOCH : head.last();
