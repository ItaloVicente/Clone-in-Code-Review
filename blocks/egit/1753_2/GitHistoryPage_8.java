	@Override
	public boolean setInput(Object object) {
		if (object == getInput())
			return true;
		this.input = null;
		return super.setInput(object);
