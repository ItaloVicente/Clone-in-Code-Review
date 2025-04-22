		if (baseClass == MLifecycleAware.class) {
			switch (baseFeatureID) {
				case ApplicationPackageImpl.LIFECYCLE_AWARE__LIFE_CYCLE_HANDLERS: return MenuPackageImpl.MENU__LIFE_CYCLE_HANDLERS;
				default: return -1;
			}
		}
