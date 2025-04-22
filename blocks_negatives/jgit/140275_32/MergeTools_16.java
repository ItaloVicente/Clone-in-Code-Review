	 * @param gui
	 *            use the diff.guitool setting ?
	 * @return the default tool name
	 */
	public String getDefaultToolName(Optional<Boolean> gui) {
		return gui.orElse(Boolean.FALSE).booleanValue()
				? config.getDefaultGuiToolName()
				: config.getDefaultToolName();
	}

	/**
