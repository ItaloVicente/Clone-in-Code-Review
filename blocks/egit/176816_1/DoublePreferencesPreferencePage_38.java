	@Override
	public void setValid(boolean b) {
		super.setValid(b);
		if (b) {
			setErrorMessage(null);
		}
	}

