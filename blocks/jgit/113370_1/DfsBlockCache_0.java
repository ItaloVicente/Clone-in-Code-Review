	public boolean hasBlock0(DfsStreamKey key) {
		HashEntry e1 = table.get(slot(key
		DfsBlock v = scan(e1
		return v != null && v.contains(key
	}

