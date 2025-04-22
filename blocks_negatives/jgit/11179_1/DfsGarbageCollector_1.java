	private long getObjectsBefore() {
		if (objectsBefore == 0) {
			for (DfsPackFile p : packsBefore)
				objectsBefore += p.getPackDescription().getObjectCount();
		}
		return objectsBefore;
