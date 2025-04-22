	public List<MDialog> getDialogs() {
		if (dialogs == null) {
			dialogs = new EObjectResolvingEList<MDialog>(MDialog.class, this, ApplicationPackageImpl.APPLICATION__DIALOGS);
		}
		return dialogs;
	}

