		return d.build();
	}

	private static void clearRefData(RefData.Builder d) {
		d.clearSymref();
		d.clearTarget();
		d.clearPeeled();
		d.clearIsPeeled();
	}

	private static void updateSequence(RefData.Builder d) {
		d.setSequence(d.getSequence() + 1);
