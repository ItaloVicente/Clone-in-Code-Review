		case MenuPackageImpl.HANDLED_ITEM__COMMAND:
			return command != null;
		case MenuPackageImpl.HANDLED_ITEM__WB_COMMAND:
			return WB_COMMAND_EDEFAULT == null ? wbCommand != null : !WB_COMMAND_EDEFAULT.equals(wbCommand);
		case MenuPackageImpl.HANDLED_ITEM__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
