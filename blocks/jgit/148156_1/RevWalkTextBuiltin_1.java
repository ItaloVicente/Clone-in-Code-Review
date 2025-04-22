	boolean not = false;

	@Option(name = "--not")
	void enableNot(boolean on) {
		if (on) {
			not = !not;
		}
	}

