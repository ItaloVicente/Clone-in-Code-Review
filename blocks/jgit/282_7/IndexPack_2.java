
	private void addObjectAndTrack(PackedObjectInfo oe) {
		entries[entryCount++] = oe;
		if (needNewObjectIds())
			newObjectIds.add(oe);
	}
}
