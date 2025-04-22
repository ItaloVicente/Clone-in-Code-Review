			case CommandsPackageImpl.KEY_BINDING__KEY_SEQUENCE:
				setKeySequence(KEY_SEQUENCE_EDEFAULT);
				return;
			case CommandsPackageImpl.KEY_BINDING__COMMAND:
				setCommand((MCommand)null);
				return;
			case CommandsPackageImpl.KEY_BINDING__PARAMETERS:
				getParameters().clear();
				return;
