	public List<MFrame> getDialogs() {
		if (dialogs == null) {
			dialogs = new EObjectContainmentEList<MFrame>(MFrame.class, this, BasicPackageImpl.WINDOW__DIALOGS);
		}
		return dialogs;
	}

