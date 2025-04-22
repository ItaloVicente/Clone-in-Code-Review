			case MenuPackageImpl.HANDLED_TOOL_ITEM__COMMAND:
				setCommand((MCommand)null);
				return;
			case MenuPackageImpl.HANDLED_TOOL_ITEM__WB_COMMAND:
				setWbCommand(WB_COMMAND_EDEFAULT);
				return;
			case MenuPackageImpl.HANDLED_TOOL_ITEM__PARAMETERS:
				getParameters().clear();
				return;
