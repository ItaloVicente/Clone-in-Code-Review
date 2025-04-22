	int getStreamFileThreshold() {
		if (db == null)
			return ObjectLoader.STREAM_THRESHOLD;
		return db.getStreamFileThreshold();
	}

