	public long getEntryLastModified() {
		if (modTimes == null)
			return 0;
		Long cutOff = Long.valueOf(super.getEntryLastModified() + 1);
		SortedSet<Long> head = modTimes.headSet(cutOff);
		return head.isEmpty() ? 0 : head.last().longValue();
