		if (baseClass == MLifecycleAware.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.LIFECYCLE_AWARE__LIFE_CYCLE_HANDLER: return ApplicationPackageImpl.APPLICATION__LIFE_CYCLE_HANDLER;
				default: return -1;
			}
		}
