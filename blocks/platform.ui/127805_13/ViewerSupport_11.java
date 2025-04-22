	public static <E> void bind(AbstractTreeViewer viewer, E input,
			ISetProperty<? super E, ? extends E> childrenProperty,
			IValueProperty<? super E, ?> labelProperty) {
		@SuppressWarnings("unchecked")
		IValueProperty<? super E, ?>[] labelPropertyArray = new IValueProperty[] { labelProperty };
		bind(viewer, input, childrenProperty, labelPropertyArray);
