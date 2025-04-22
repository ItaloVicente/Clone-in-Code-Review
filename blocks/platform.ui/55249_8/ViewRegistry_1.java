		} else {
			IExtension declaringExtension = element.getDeclaringExtension();
			String name = declaringExtension.getContributor().getName();

			Bundle bundle = Platform.getBundle(name);
			descriptor.getPersistedState().put(ORIGINAL_COMPATIBILITY_VIEW_CLASS, clsSpec.split(":")[0]); //$NON-NLS-1$
			descriptor.getPersistedState().put(ORIGINAL_COMPATIBILITY_VIEW_BUNDLE, bundle.getSymbolicName());
