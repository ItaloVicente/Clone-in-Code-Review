		appendState(State.OVERRIDDEN);
	}

	public int getState() {
		return state;
	}

	public void appendState(int state) {
		this.state |= state;
