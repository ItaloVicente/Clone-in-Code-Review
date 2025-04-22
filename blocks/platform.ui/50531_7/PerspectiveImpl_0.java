	public List<MHandler> getHandlers() {
		if (handlers == null) {
			handlers = new EObjectContainmentEList<MHandler>(MHandler.class, this, AdvancedPackageImpl.PERSPECTIVE__HANDLERS);
		}
		return handlers;
	}

