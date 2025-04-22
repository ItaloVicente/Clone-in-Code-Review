		int newCount = rec.removeRef();
		if (newCount <= 0) {
			mapIdToRec.remove(id);
		}
		return newCount;
	}

	public List values() {
		int size = mapIdToRec.size();
		ArrayList list = new ArrayList(size);
		Iterator iter = mapIdToRec.values().iterator();
		while (iter.hasNext()) {
			RefRec rec = (RefRec) iter.next();
			list.add(rec.getValue());
		}
		return list;
	}
