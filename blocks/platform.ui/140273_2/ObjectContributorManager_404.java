			}
		}
		adapters.clear();
	}

	private void removeCommonInterfaces(List superClasses, List types, List results) {
		List dropInterfaces = null;
		if (!superClasses.isEmpty()) {
			dropInterfaces = computeInterfaceOrder(superClasses);
		}
		for (int j = 0; j < types.size(); j++) {
			if (types.get(j) != null) {
				if (dropInterfaces != null && !dropInterfaces.contains(types.get(j))) {
					results.add(types.get(j));
				}
			}
		}
	}

	private List computeAdapterOrder(List classList) {
		Set result = new HashSet(4);
		IAdapterManager adapterMgr = Platform.getAdapterManager();
		for (Iterator list = classList.iterator(); list.hasNext();) {
			Class clazz = ((Class) list.next());
