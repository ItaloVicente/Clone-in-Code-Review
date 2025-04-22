		if (baseClass == MLifecycleAware.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.LIFECYCLE_AWARE__LIFE_CYCLE_HANDLER: return MenuPackageImpl.MENU__LIFE_CYCLE_HANDLER;
				default: return -1;
			}
		}
