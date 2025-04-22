	public final int getLengthA() {
		return endA - beginA;
	}

	public final int getLengthB() {
		return endB - beginB;
	}

	public final Edit before(Edit cut) {
		return new Edit(beginA
	}

	public final Edit after(Edit cut) {
		return new Edit(cut.endA
	}

