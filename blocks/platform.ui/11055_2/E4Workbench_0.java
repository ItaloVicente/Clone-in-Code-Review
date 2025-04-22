	public boolean restart() {
		this.restart = true;
		return close();
	}

	public boolean isRestart() {
		return restart;
	}

