        }
    }

    /**
     * Returns the class search order starting with <code>extensibleClass</code>.
     * The search order is defined in this class' comment.
     */
    protected final List computeClassOrder(Class extensibleClass) {
        ArrayList result = new ArrayList(4);
        Class clazz = extensibleClass;
        while (clazz != null) {
            result.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    /**
     * Returns the interface search order for the class hierarchy described
     * by <code>classList</code>.
     * The search order is defined in this class' comment.
     */
    protected final List computeInterfaceOrder(List classList) {
        ArrayList result = new ArrayList(4);
        Map seen = new HashMap(4);
        for (Iterator list = classList.iterator(); list.hasNext();) {
            Class[] interfaces = ((Class) list.next()).getInterfaces();
            internalComputeInterfaceOrder(interfaces, result, seen);
        }
        return result;
    }

    /**
     * Flushes the cache of contributor search paths.  This is generally required
     * whenever a contributor is added or removed.
     * <p>
     * It is likely easier to just toss the whole cache rather than trying to be
     * smart and remove only those entries affected.
     */
    public void flushLookup() {
        objectLookup = null;
        resourceAdapterLookup = null;
        adaptableLookup = null;
    }

    /**
     * Cache the real adapter class contributor search path.
     */
    private void cacheResourceAdapterLookup(Class adapterClass, List results) {
        if (resourceAdapterLookup == null) {
