			case CommandsPackageImpl.COMMAND__COMMAND_NAME:
				return getCommandName();
			case CommandsPackageImpl.COMMAND__DESCRIPTION:
				return getDescription();
			case CommandsPackageImpl.COMMAND__PARAMETERS:
				return getParameters();
			case CommandsPackageImpl.COMMAND__CATEGORY:
				if (resolve) return getCategory();
				return basicGetCategory();
			case CommandsPackageImpl.COMMAND__LOCALIZED_COMMAND_NAME:
				return getLocalizedCommandName();
			case CommandsPackageImpl.COMMAND__LOCALIZED_DESCRIPTION:
				return getLocalizedDescription();
