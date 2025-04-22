		case CommandsPackageImpl.COMMAND__COMMAND_NAME:
			setCommandName(COMMAND_NAME_EDEFAULT);
			return;
		case CommandsPackageImpl.COMMAND__DESCRIPTION:
			setDescription(DESCRIPTION_EDEFAULT);
			return;
		case CommandsPackageImpl.COMMAND__PARAMETERS:
			getParameters().clear();
			return;
		case CommandsPackageImpl.COMMAND__CATEGORY:
			setCategory((MCategory) null);
			return;
