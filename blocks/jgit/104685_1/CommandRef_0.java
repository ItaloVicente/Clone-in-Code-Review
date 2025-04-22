
	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "CommandRef [impl=" + impl + "
				+ CLIText.get().resourceBundle().getString(usage) + "
				+ common + "]";
	}
