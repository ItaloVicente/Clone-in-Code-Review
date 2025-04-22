	private BatchRefUpdate newBatchUpdate() {
		BatchRefUpdate u = refdir.newBatchUpdate();
		if (atomic) {
			assertTrue(u.isAtomic());
		} else {
			u.setAtomic(false);
		}
		return u;
	}

