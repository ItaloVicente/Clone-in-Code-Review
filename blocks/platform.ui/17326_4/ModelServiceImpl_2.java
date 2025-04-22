
	private static void checkDeprecation(EClass eClass) {
		if (eClass == null)
			return;

		EAnnotation deprecated = eClass .getEAnnotation("http://www.eclipse.org/ui/2010/UIModel/application/deprecated"); //$NON-NLS-1$
		if (deprecated != null) {
			StringBuilder sb = new StringBuilder("The element '").append(eClass.getInstanceTypeName()).append("' is already deprecated!"); //$NON-NLS-1$ //$NON-NLS-2$

			String since = deprecated.getDetails().get("since"); //$NON-NLS-1$
			if (since != null) {
				sb.append(" (since version: ").append(since).append(')'); //$NON-NLS-1$
			}

			throw new IllegalArgumentException(sb.toString());
		}
	}

	private static void checkInstantiation(EClass eClass) {
		if (eClass == null)
			return;

		if (eClass.isInterface()) {
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is an interface!"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (eClass.isAbstract()) {
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is an abstract class!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
