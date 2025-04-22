	/**
	 * Sets the given help context computer on the given menu.
	 * <p>
	 * Use this method when the help contexts cannot be computed in advance.
	 * Help contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param menu
	 *            the menu on which to register the computer
	 * @param computer
	 *            the computer to determine the help contexts for the control
	 *            when F1 help is invoked
	 * @deprecated context computers are no longer supported, clients should
	 *             implement their own help listener
	 */
	@Deprecated
	public void setHelp(Menu menu, IContextComputer computer) {
		menu.setData(HELP_KEY, computer);
		menu.removeHelpListener(getHelpListener());
		menu.addHelpListener(getHelpListener());
	}

