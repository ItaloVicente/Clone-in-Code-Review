	@Override
	public <E2> IBeanListProperty<S, E2> list(String propertyName) {
		return list(propertyName, null);
	}

	@Override
	public <E2> IBeanListProperty<S, E2> list(String propertyName, Class<E2> elementType) {
		@SuppressWarnings("unchecked")
		Class<E> beanClass = (Class<E>) delegate.getElementType();
		return list(BeanProperties.list(beanClass, propertyName, elementType));
	}

	@Override
	public <E2> IBeanListProperty<S, E2> list(IBeanListProperty<? super E, E2> detailList) {
		return new BeanListPropertyDecorator<>(super.list(detailList), detailList.getPropertyDescriptor());
	}

