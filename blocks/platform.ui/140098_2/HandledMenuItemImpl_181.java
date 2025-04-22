		case MenuPackageImpl.HANDLED_MENU_ITEM__COMMAND:
			setCommand((MCommand) newValue);
			return;
		case MenuPackageImpl.HANDLED_MENU_ITEM__WB_COMMAND:
			setWbCommand((ParameterizedCommand) newValue);
			return;
		case MenuPackageImpl.HANDLED_MENU_ITEM__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends MParameter>) newValue);
			return;
