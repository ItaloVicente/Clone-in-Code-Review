	public List<MLifecycleContribution> getLifeCycleHandlers() {
		if (lifeCycleHandlers == null) {
			lifeCycleHandlers = new EObjectContainmentEList<MLifecycleContribution>(MLifecycleContribution.class, this, MenuPackageImpl.MENU__LIFE_CYCLE_HANDLERS);
		}
		return lifeCycleHandlers;
	}

