	public List<MLifecycleContribution> getLifeCycleHandler() {
		if (lifeCycleHandler == null) {
			lifeCycleHandler = new EObjectContainmentEList<MLifecycleContribution>(MLifecycleContribution.class, this, MenuPackageImpl.MENU__LIFE_CYCLE_HANDLER);
		}
		return lifeCycleHandler;
	}

