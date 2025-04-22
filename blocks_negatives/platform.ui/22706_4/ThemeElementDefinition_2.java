	public void setAddedByCss(boolean addedByCss) {
		this.addedByCss = addedByCss;
	}

	public String getOverriddenLabel() {
		if (overriddenLabel == null) {
			ResourceBundle resourceBundle = ResourceBundle.getBundle(Theme.class.getName());
		}
		return overriddenLabel;
