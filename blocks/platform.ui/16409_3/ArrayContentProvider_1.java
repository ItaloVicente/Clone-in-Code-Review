
	public static <E> ArrayContentProvider<E> getInstance(Class<E> componentType) {

		@SuppressWarnings("unchecked")
		ArrayContentProvider<E> arrayContentProvider = (ArrayContentProvider<E>) instanceMap.get(componentType);
		if (arrayContentProvider == null) {
			synchronized (ArrayContentProvider.class) {
				arrayContentProvider = new ArrayContentProvider<E>(
						componentType);
				instanceMap.put(componentType, arrayContentProvider);
			}
		}

		return arrayContentProvider;
	}
