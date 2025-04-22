	@After
	public void closeWalk() {
		if (walk != null) {
			walk.close();
		}
	}

