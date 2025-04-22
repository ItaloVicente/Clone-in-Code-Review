		if (baseClass == MLifecycleAware.class) {
			switch (derivedFeatureID) {
				case MenuPackageImpl.MENU__LIFE_CYCLE_HANDLERS: return ApplicationPackageImpl.LIFECYCLE_AWARE__LIFE_CYCLE_HANDLERS;
				default: return -1;
			}
		}
