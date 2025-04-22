		return Boolean.parseBoolean(definingElement.getAttribute(ATT_ADAPTABLE));
	}

	public String getId() {
		return id;
	}

	public boolean getDefaultValue() {
		return defaultEnabled;
	}

	protected ActionExpression getEnablement() {
		if (!hasReadEnablement) {
			hasReadEnablement = true;
			initializeEnablement();
		}
		return enablement;
	}

	protected void initializeEnablement() {
