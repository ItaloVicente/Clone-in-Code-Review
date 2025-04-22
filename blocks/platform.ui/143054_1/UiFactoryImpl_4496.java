		case UiPackageImpl.CORE_EXPRESSION:
			return (EObject) createCoreExpression();
		case UiPackageImpl.IMPERATIVE_EXPRESSION:
			return (EObject) createImperativeExpression();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
