	public void setLockMessage(String msg) {
		lockMessage = msg;
	}

	public int getObjectCount() {
		return entryCount;
	}

	public PackedObjectInfo getObject(int nth) {
		return entries[nth];
	}

