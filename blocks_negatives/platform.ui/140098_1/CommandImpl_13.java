			case CommandsPackageImpl.COMMAND__COMMAND_NAME:
				return COMMAND_NAME_EDEFAULT == null ? commandName != null : !COMMAND_NAME_EDEFAULT.equals(commandName);
			case CommandsPackageImpl.COMMAND__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case CommandsPackageImpl.COMMAND__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case CommandsPackageImpl.COMMAND__CATEGORY:
				return category != null;
			case CommandsPackageImpl.COMMAND__LOCALIZED_COMMAND_NAME:
				return LOCALIZED_COMMAND_NAME_EDEFAULT == null ? getLocalizedCommandName() != null : !LOCALIZED_COMMAND_NAME_EDEFAULT.equals(getLocalizedCommandName());
			case CommandsPackageImpl.COMMAND__LOCALIZED_DESCRIPTION:
				return LOCALIZED_DESCRIPTION_EDEFAULT == null ? getLocalizedDescription() != null : !LOCALIZED_DESCRIPTION_EDEFAULT.equals(getLocalizedDescription());
