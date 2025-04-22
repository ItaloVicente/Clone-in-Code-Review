	/**
	 * @return the action selection
	 */
	public int getActionSelection() {
		if (actionAutoShare.getSelection())
			return ACTION_AUTO_SHARE;
		if (actionNothing.getSelection())
			return ACTION_NO_SHARE;
		return -1;
	}

