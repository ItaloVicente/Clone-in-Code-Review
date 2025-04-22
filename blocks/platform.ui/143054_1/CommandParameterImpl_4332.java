		case CommandsPackageImpl.COMMAND_PARAMETER__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case CommandsPackageImpl.COMMAND_PARAMETER__TYPE_ID:
			return TYPE_ID_EDEFAULT == null ? typeId != null : !TYPE_ID_EDEFAULT.equals(typeId);
		case CommandsPackageImpl.COMMAND_PARAMETER__OPTIONAL:
			return optional != OPTIONAL_EDEFAULT;
