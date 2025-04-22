
	@Override
	public int hashCode() {
		if (uri != null)
			return uri.hashCode();
		else if (config != null)
			return config.hashCode();
		return 31;
	}
