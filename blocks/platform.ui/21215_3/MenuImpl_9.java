		if (baseClass == MLifecycleAware.class) {
			switch (derivedFeatureID) {
				case MenuPackageImpl.MENU__LIFE_CYCLE_HANDLER: return ApplicationPackageImpl.LIFECYCLE_AWARE__LIFE_CYCLE_HANDLER;
				default: return -1;
			}
		}
