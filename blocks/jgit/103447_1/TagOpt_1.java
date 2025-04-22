
	@Override
	public String toConfigValue() {
		return option;
	}

	@Override
	public boolean matchConfigValue(String in) {
		return option.equals(in) || in == null && option.isEmpty();
	}
