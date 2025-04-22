	public List<MFrame> getDialogs() {
		if (dialogs == null) {
			dialogs = new EObjectContainmentEList<MFrame>(MFrame.class, this, BasicPackageImpl.PART__DIALOGS);
		}
		return dialogs;
	}

