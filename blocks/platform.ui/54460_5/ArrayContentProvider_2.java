	private Class<E> componentType;



	@Deprecated
	public ArrayContentProvider(){

	}

	public ArrayContentProvider(Class<E> componentType) {
		this.componentType = componentType;
	}
