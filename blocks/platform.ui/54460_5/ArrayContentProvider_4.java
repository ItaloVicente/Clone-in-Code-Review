    @SuppressWarnings("unchecked")
	public E[] getElements(Object inputElement) {
		if (componentType != null) {
			if (inputElement instanceof Object[]) {
				Assert.isTrue(inputElement.getClass().getComponentType()
						.isAssignableFrom(componentType),"The type of the input ("+inputElement.getClass().getComponentType()+") is not compatible to the ArrayContentProvider Type ("+componentType+")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
