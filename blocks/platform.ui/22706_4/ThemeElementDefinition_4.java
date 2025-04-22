	public int getState() {
		return state;
	}

	public void appendState(int state) {
		this.state |= state;
	}

	public void removeState(int state) {
		this.state &= ~state;
	}
