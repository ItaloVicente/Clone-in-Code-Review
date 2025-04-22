	public EClass getLifecycleAware() {
		return lifecycleAwareEClass;
	}


	public EReference getLifecycleAware_LifeCycleHandlers() {
		return (EReference)lifecycleAwareEClass.getEStructuralFeatures().get(0);
	}


	public EClass getLifecycleContribution() {
		return lifecycleContributionEClass;
	}


