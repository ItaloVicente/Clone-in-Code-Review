
	/**
	 * @return true if Gerrit configuration should be done
	 */
	public boolean configureGerrit() {
		return createGerritCheckbox == false || configureGerrit.getSelection();
	}

