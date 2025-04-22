		case CommandsPackageImpl.BINDING_TABLE__BINDINGS:
			getBindings().clear();
			getBindings().addAll((Collection<? extends MKeyBinding>) newValue);
			return;
		case CommandsPackageImpl.BINDING_TABLE__BINDING_CONTEXT:
			setBindingContext((MBindingContext) newValue);
			return;
