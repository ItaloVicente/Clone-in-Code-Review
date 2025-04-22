	public List<MDialog> getDialogs() {
		if (dialogs == null) {
			dialogs = new EObjectResolvingEList<MDialog>(MDialog.class, this, BasicPackageImpl.PART__DIALOGS);
		}
		return dialogs;
	}

