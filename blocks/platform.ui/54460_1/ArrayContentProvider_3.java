
	public static <E> ArrayContentProvider<E> getInstance(Class<E> componentType) {

		synchronized (instanceMap) {
			@SuppressWarnings("unchecked")
			ArrayContentProvider<E> arrayContentProvider = (ArrayContentProvider<E>) instanceMap
					.get(componentType);
			if (arrayContentProvider == null) {
				arrayContentProvider = new ArrayContentProvider<E>(
						componentType);
				instanceMap.put(componentType, arrayContentProvider);
			}
			return arrayContentProvider;
		}
	}
