    @SuppressWarnings("unchecked")
	public E[] getElements(Object inputElement) {
		if (componentType != null) {
			if (inputElement instanceof Object[]) {
				return (E[]) inputElement;
			}
			if (inputElement instanceof Collection) {
				E[] elementArray = (E[]) Array.newInstance(componentType,
						((Collection<E>) inputElement).size());
				return ((Collection<E>) inputElement).toArray(elementArray);
			}
			return (E[]) Array.newInstance(componentType, 0);
		}
		if (inputElement instanceof Object[]) {
			return (E[]) inputElement;
