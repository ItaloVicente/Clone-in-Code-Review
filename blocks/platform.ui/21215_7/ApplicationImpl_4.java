	public List<MLifecycleContribution> getLifeCycleHandlers() {
		if (lifeCycleHandlers == null) {
			lifeCycleHandlers = new EObjectContainmentEList<MLifecycleContribution>(MLifecycleContribution.class, this, ApplicationPackageImpl.APPLICATION__LIFE_CYCLE_HANDLERS);
		}
		return lifeCycleHandlers;
	}

