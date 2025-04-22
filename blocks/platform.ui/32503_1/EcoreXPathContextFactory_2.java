	public static XPathContextFactory<EObject> newInstance(List<Class<?>> functions, List<String> namespaces) {
		JXPathContextFactoryImpl<EObject> ret = new JXPathContextFactoryImpl<EObject>();
		ret.setFunctions(functions, namespaces);
		return ret;
	}

