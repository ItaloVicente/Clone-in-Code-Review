		}
	}

	protected final List computeClassOrder(Class extensibleClass) {
		ArrayList result = new ArrayList(4);
		Class clazz = extensibleClass;
		while (clazz != null) {
			result.add(clazz);
			clazz = clazz.getSuperclass();
		}
		return result;
	}

	protected final List computeInterfaceOrder(List classList) {
		ArrayList result = new ArrayList(4);
		Map seen = new HashMap(4);
		for (Iterator list = classList.iterator(); list.hasNext();) {
			Class[] interfaces = ((Class) list.next()).getInterfaces();
			internalComputeInterfaceOrder(interfaces, result, seen);
		}
		return result;
	}

	public void flushLookup() {
		objectLookup = null;
		resourceAdapterLookup = null;
		adaptableLookup = null;
	}

	private void cacheResourceAdapterLookup(Class adapterClass, List results) {
		if (resourceAdapterLookup == null) {
