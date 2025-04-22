		return rec.addRef();
	}

	public Object get(Object id) {
		RefRec rec = (RefRec) mapIdToRec.get(id);
		if (rec == null) {
