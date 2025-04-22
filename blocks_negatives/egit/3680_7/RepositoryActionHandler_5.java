	private Object getAdapter(Object adaptable, Class c) {
		if (c.isInstance(adaptable))
			return adaptable;
		if (adaptable instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) adaptable;
			Object adapter = a.getAdapter(c);
			if (c.isInstance(adapter))
				return adapter;
		}
		return null;
	}

