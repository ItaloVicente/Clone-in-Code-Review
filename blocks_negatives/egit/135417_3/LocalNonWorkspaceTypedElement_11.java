	/** {@inheritDoc} */
	@Override
	public void setContent(byte[] contents) {
		fDirty = true;
		super.setContent(contents);
	}

