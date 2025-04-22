	public void setObjectInserter(ObjectInserter oi) {
		if (inserter != null)
			inserter.release();
		inserter = oi;
	}

