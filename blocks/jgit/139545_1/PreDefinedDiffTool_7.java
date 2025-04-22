		return Utils.quotePath(path) + " " + super.getCommand();
	}

	@Override
	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
