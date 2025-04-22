		for (int i = 0; i < entries.size(); i++) {
			PositionEntry e = positionEntries.get(entries.get(i));
			e.offsetPosition = i;
			byOffset.add(e);
		}
	}

	public ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> getObjectSet() {
		ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> r = new ObjectIdOwnerMap<>();
		for (PositionEntry e : byOffset) {
			r.add(new ObjectIdOwnerMap.Entry(e) {
			});
		}
		return r;
