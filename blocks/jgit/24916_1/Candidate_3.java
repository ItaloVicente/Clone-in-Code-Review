	boolean add(RevFlag flag) {
		if (sourceCommit.has(flag))
			return false;
		else {
			sourceCommit.add(flag);
			return true;
		}
	}

	void remove(RevFlag flag) {
		sourceCommit.remove(flag);
