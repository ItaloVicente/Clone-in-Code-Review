		case ApplicationPackageImpl.STRING_TO_STRING_MAP:
			return (EObject) createStringToStringMap();
		case ApplicationPackageImpl.APPLICATION:
			return (EObject) createApplication();
		case ApplicationPackageImpl.ADDON:
			return (EObject) createAddon();
		case ApplicationPackageImpl.STRING_TO_OBJECT_MAP:
			return (EObject) createStringToObjectMap();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
