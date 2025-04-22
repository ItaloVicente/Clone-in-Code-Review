		case CommandsPackageImpl.KEY_BINDING__KEY_SEQUENCE:
			return getKeySequence();
		case CommandsPackageImpl.KEY_BINDING__COMMAND:
			if (resolve)
				return getCommand();
			return basicGetCommand();
		case CommandsPackageImpl.KEY_BINDING__PARAMETERS:
			return getParameters();
