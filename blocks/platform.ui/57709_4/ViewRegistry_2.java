		} else {
			IExtension declaringExtension = element.getDeclaringExtension();
			String name = declaringExtension.getContributor().getName();

			Bundle bundle = Platform.getBundle(name);
			int colonIndex = clsSpec.indexOf(':');
			String viewClass = colonIndex == -1 ? clsSpec : clsSpec.substring(0, colonIndex);
			descriptor.getPersistedState().put(ORIGINAL_COMPATIBILITY_VIEW_CLASS, viewClass);
			descriptor.getPersistedState().put(ORIGINAL_COMPATIBILITY_VIEW_BUNDLE, bundle.getSymbolicName());
