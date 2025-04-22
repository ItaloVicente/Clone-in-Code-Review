			case CommandsPackageImpl.COMMAND__COMMAND_NAME:
				setCommandName((String)newValue);
				return;
			case CommandsPackageImpl.COMMAND__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case CommandsPackageImpl.COMMAND__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends MCommandParameter>)newValue);
				return;
			case CommandsPackageImpl.COMMAND__CATEGORY:
				setCategory((MCategory)newValue);
				return;
