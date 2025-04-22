		JXPathContextImpl ret = new JXPathContextImpl(contextBean);
		ret.addFunctions(functions, namespaces);
		return ret;
	}

	public void setFunctions(List<Class<?>> functions, List<String> namespaces) {
		this.functions = functions;
		this.namespaces = namespaces;
