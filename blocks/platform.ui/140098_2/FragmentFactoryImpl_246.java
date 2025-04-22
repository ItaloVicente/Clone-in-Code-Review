		case FragmentPackageImpl.MODEL_FRAGMENTS:
			return (EObject) createModelFragments();
		case FragmentPackageImpl.STRING_MODEL_FRAGMENT:
			return (EObject) createStringModelFragment();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
