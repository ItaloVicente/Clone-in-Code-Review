		if (baseClass == MLifecycleAware.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.LIFECYCLE_AWARE__LIFE_CYCLE_HANDLERS: return ApplicationPackageImpl.APPLICATION__LIFE_CYCLE_HANDLERS;
				default: return -1;
			}
		}
