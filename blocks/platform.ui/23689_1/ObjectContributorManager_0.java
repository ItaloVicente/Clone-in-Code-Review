	protected final List<Class<?>> computeInterfaceOrder(List<Class<?>> classList) {
		ArrayList<Class<?>> result = new ArrayList<Class<?>>(4);

		for (Iterator<Class<?>> list = classList.iterator(); list.hasNext();) {
			Class<?>[] interfaces = list.next().getInterfaces();
			internalComputeInterfaceOrder(interfaces, result, new HashMap(4));
