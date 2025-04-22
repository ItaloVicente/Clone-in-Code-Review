		String classPackageName;
		String classResourceName;
		int indexLastDot = className.lastIndexOf('.');
		if (indexLastDot < 0) {
			classPackageName = "/"; //$NON-NLS-1$
			classResourceName = className;
		} else {
			classPackageName = '/' + className.substring(0, indexLastDot).replace('.', '/');
			classResourceName = className.substring(indexLastDot + 1) + ".class"; //$NON-NLS-1$
		}
