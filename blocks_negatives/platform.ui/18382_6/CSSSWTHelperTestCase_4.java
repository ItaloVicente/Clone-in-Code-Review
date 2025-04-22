	protected CSS2FontProperties fontProperties(final String family, final Object size, final Object style) throws Exception {
		return (CSS2FontProperties) Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[] {CSS2FontProperties.class}, 
				new InvocationHandler() {			
			public Object invoke(Object arg0, Method method, Object[] arg2)
					throws Throwable {
				if (method.getName().equals("getFamily")) {
					return valueImpl(family);
				} 
				if (method.getName().equals("getSize")) {
					return valueImpl(size);
				} 
				if (method.getName().equals("getStyle")) {
					return valueImpl(style);
