		catalogs = new ArrayList<>();
		catalogs.add(foods);
		catalog.setCatalogs(catalogs);

		@SuppressWarnings("unchecked")
		IListProperty<AbstractModelObject, AbstractModelObject> childrenProperty = new MultiListProperty<>(
				(IListProperty<AbstractModelObject, AbstractModelObject>[]) new IListProperty<?, ?>[] {
					BeanProperties.list("catalogs"),
					BeanProperties.list("items") });

		ObservableListTreeContentProvider<AbstractModelObject> contentProvider =
				new ObservableListTreeContentProvider<>(childrenProperty.listFactory(), null);
