    private static Method getContributorResourceAdapter2GetAdaptedResourceMappingMethod() {
        if (getAdaptedResourceMappingMethod != null) {
            return getAdaptedResourceMappingMethod;
        }

        Class c = getIContributorResourceAdapter2Class();
        if (c != null) {
            try {
				getAdaptedResourceMappingMethod = c.getDeclaredMethod("getAdaptedResourceMapping", new Class[]{IAdaptable.class}); //$NON-NLS-1$
