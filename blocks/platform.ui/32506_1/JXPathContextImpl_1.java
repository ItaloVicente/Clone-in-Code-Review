	public void addFunctions(List<Class<?>> functions, List<String> namespaces) {
		if (functions != null && namespaces != null) {
			for (int i = 0; i < functions.size(); i++) {
				if (namespaces.size() < i - 1) {
					break;
				}
				functionLibrary.addFunctions(new ClassFunctions(functions.get(i), namespaces.get(i)));
			}
		}
	}
