		try {
			Class.forName("org.eclipse.ui.views.properties.IPropertySource"); //$NON-NLS-1$
		} catch(ClassNotFoundException e) {
			return new Class[0];
		}
		return new Class[] { IPropertySource.class };
	}
