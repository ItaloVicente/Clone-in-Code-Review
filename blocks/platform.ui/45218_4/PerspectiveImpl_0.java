	public List<MFrame> getDialogs() {
		if (dialogs == null) {
			dialogs = new EObjectContainmentEList<MFrame>(MFrame.class, this, AdvancedPackageImpl.PERSPECTIVE__DIALOGS);
		}
		return dialogs;
	}

