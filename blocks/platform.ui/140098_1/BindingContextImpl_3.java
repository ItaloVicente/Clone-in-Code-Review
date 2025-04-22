		case CommandsPackageImpl.BINDING_CONTEXT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case CommandsPackageImpl.BINDING_CONTEXT__DESCRIPTION:
			return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
		case CommandsPackageImpl.BINDING_CONTEXT__CHILDREN:
			return children != null && !children.isEmpty();
