
    /**
     * If it is possible to adapt the given object to the given type, this
     * returns the adapter. Performs the following checks:
     *
     * <ol>
     * <li>Returns <code>sourceObject</code> if it is an instance of the
     * adapter type.</li>
     * <li>If sourceObject implements IAdaptable, it is queried for adapters.</li>
     * <li>If sourceObject is not an instance of PlatformObject (which would have
     * already done so), the adapter manager is queried for adapters</li>
     * </ol>
     *
     * Otherwise returns null.
     *
     * @param sourceObject
     *            object to adapt, or null
     * @param adapter
     *            type to adapt to
     * @param activatePlugins
     *            true if IAdapterManager.loadAdapter should be used (may trigger plugin activation)
     * @return a representation of sourceObject that is assignable to the
     *         adapter type, or null if no such representation exists
     */
	public static <T> T getAdapter(Object sourceObject, Class<T> adapter, boolean activatePlugins) {
    	Assert.isNotNull(adapter);
        if (sourceObject == null) {
            return null;
        }
        if (adapter.isInstance(sourceObject)) {
			return adapter.cast(sourceObject);
        }

        if (sourceObject instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) sourceObject;

			T result = adaptable.getAdapter(adapter);
            if (result != null) {
                Assert.isTrue(adapter.isInstance(result));
                return result;
            }
        }

        if (!(sourceObject instanceof PlatformObject)) {
			T result;
        	if (activatePlugins) {
				result = adapter.cast(Platform.getAdapterManager().loadAdapter(sourceObject, adapter.getName()));
        	} else {
        		result = Platform.getAdapterManager().getAdapter(sourceObject, adapter);
        	}
            if (result != null) {
                return result;
            }
        }

        return null;
    }
