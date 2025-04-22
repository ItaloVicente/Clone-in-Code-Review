		return null;
	}

	protected DialogPage getPage(){
		return page;
	}

	public IPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

	protected void init(String name, String text) {
		Assert.isNotNull(name);
		Assert.isNotNull(text);
		preferenceName = name;
		this.labelText = text;
	}

	public boolean isValid() {
		return true;
	}

	public void load() {
		if (preferenceStore != null) {
			isDefaultPresented = false;
			doLoad();
			refreshValidState();
		}
	}

	public void loadDefault() {
		if (preferenceStore != null) {
			isDefaultPresented = true;
			doLoadDefault();
			refreshValidState();
		}
	}

	public boolean presentsDefaultValue() {
		return isDefaultPresented;
	}

	protected void refreshValidState() {
	}

	public void setFocus() {
	}

	public void setLabelText(String text) {
		Assert.isNotNull(text);
		labelText = text;
		if (label != null) {
