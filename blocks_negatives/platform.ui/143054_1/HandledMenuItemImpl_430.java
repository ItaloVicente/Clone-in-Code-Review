			case MenuPackageImpl.HANDLED_MENU_ITEM__COMMAND:
				if (resolve) return getCommand();
				return basicGetCommand();
			case MenuPackageImpl.HANDLED_MENU_ITEM__WB_COMMAND:
				return getWbCommand();
			case MenuPackageImpl.HANDLED_MENU_ITEM__PARAMETERS:
				return getParameters();
