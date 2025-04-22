		case MenuPackageImpl.HANDLED_MENU_ITEM__COMMAND:
			setCommand((MCommand) null);
			return;
		case MenuPackageImpl.HANDLED_MENU_ITEM__WB_COMMAND:
			setWbCommand(WB_COMMAND_EDEFAULT);
			return;
		case MenuPackageImpl.HANDLED_MENU_ITEM__PARAMETERS:
			getParameters().clear();
			return;
