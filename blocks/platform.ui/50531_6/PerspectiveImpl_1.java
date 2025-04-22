		if (baseClass == MHandlerContainer.class) {
			switch (derivedFeatureID) {
				case AdvancedPackageImpl.PERSPECTIVE__HANDLERS: return CommandsPackageImpl.HANDLER_CONTAINER__HANDLERS;
				default: return -1;
			}
		}
