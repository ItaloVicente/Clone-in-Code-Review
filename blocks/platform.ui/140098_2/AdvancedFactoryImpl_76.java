		case AdvancedPackageImpl.PLACEHOLDER:
			return (EObject) createPlaceholder();
		case AdvancedPackageImpl.PERSPECTIVE:
			return (EObject) createPerspective();
		case AdvancedPackageImpl.PERSPECTIVE_STACK:
			return (EObject) createPerspectiveStack();
		case AdvancedPackageImpl.AREA:
			return (EObject) createArea();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
