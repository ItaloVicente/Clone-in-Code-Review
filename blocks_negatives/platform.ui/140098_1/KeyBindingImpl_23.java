			case CommandsPackageImpl.KEY_BINDING__KEY_SEQUENCE:
				setKeySequence((String)newValue);
				return;
			case CommandsPackageImpl.KEY_BINDING__COMMAND:
				setCommand((MCommand)newValue);
				return;
			case CommandsPackageImpl.KEY_BINDING__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends MParameter>)newValue);
				return;
