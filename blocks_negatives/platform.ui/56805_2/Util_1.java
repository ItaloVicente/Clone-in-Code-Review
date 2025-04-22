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
     * @param adapterType
     *            type to adapt to
     * @return a representation of sourceObject that is assignable to the
     *         adapter type, or null if no such representation exists
     */
	public static <T> T getAdapter(Object sourceObject, Class<T> adapterType) {
    	Assert.isNotNull(adapterType);
        if (sourceObject == null) {
            return null;
        }
        if (adapterType.isInstance(sourceObject)) {
			return adapterType.cast(sourceObject);
        }

        if (sourceObject instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) sourceObject;

			T result = adaptable.getAdapter(adapterType);
            if (result != null) {
                Assert.isTrue(adapterType.isInstance(result));
                return result;
            }
        }

        if (!(sourceObject instanceof PlatformObject)) {
			T result = Platform.getAdapterManager().getAdapter(sourceObject, adapterType);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

