		case CommandsPackageImpl.BINDING_TABLE__BINDINGS:
			return getBindings();
		case CommandsPackageImpl.BINDING_TABLE__BINDING_CONTEXT:
			if (resolve)
				return getBindingContext();
			return basicGetBindingContext();
