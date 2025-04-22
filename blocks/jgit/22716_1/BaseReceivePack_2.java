	public long getPackSize() {
		if (packSize != null)
			return packSize.longValue();
		throw new IllegalStateException(JGitText.get().packSizeNotSetYet);
	}

