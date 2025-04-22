		if (!(target instanceof IProject)) {
			return false;
		}
		IProject proj = (IProject) target;
		if (name.equals(NATURE)) {
			try {
				return proj.isAccessible() && proj.hasNature(value);
			} catch (CoreException e) {
				return false;
			}
		} else if (name.equals(OPEN)) {
			value = value.toLowerCase();
			return (proj.isOpen() == value.equals("true"));//$NON-NLS-1$
		}
		return super.testAttribute(target, name, value);
	}
