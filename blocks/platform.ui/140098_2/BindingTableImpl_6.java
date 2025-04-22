		case CommandsPackageImpl.BINDING_TABLE__BINDINGS:
			getBindings().clear();
			return;
		case CommandsPackageImpl.BINDING_TABLE__BINDING_CONTEXT:
			setBindingContext((MBindingContext) null);
			return;
