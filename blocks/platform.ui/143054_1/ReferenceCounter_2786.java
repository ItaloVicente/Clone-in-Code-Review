		return rec.getValue();
	}

	public Set keySet() {
		return mapIdToRec.keySet();
	}

	public void put(Object id, Object value) {
		RefRec rec = new RefRec(id, value);
		mapIdToRec.put(id, rec);
	}

	public int getRef(Object id) {
		RefRec rec = (RefRec) mapIdToRec.get(id);
		if (rec == null) {
			return 0;
		}
		return rec.refCount;
	}

	public int removeRef(Object id) {
		RefRec rec = (RefRec) mapIdToRec.get(id);
		if (rec == null) {
